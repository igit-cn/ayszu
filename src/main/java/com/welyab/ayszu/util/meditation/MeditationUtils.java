/*
 * Copyright 2015 Welyab da Silva Paula
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.welyab.ayszu.util.meditation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import com.welyab.ayszu.util.Pair;
import com.welyab.ayszu.util.Single;
import com.welyab.ayszu.util.SynchronizedSingle;
import com.welyab.ayszu.util.collection.AyszuCollections;

/**
 * Utility methods to used in Meditation API.
 *
 * @author Welyab Paula
 */
public class MeditationUtils {

	/**
	 * This method was designed to help the <code>Meditation</code> class to check if is possible
	 * to assign a value to a given type of variable; if distance between value class type and the
	 * variable class type is a positive number, them the assignment is possible.
	 *
	 * <p>
	 * The most simple assignment statement is shown below:
	 *
	 * <pre>
	 * variable = expression;
	 * // for example
	 * int x = 7;
	 * </pre>
	 *
	 * If the distance of <i>right expression result class type</i> to the <i>variable class
	 * type</i> is a positive value, then the assignment is possible without throws a
	 * <code>ClassCastException</code>. For example, is not possible assign a <code>String</code> to
	 * a <code>Integer</code> variable; in this cases, the distance is defined here as
	 * <code>-1</code>.
	 *
	 * <p>
	 * This method was designed to help the <code>Meditation</code> class to check if is possible
	 * assign a value to a given type of variable. Because, when using java reflection, the compiler
	 * time type check is lost. So, this method is strongly helpful to simulate compiler behavior
	 * during method calls with java reflection.
	 *
	 * <p>
	 * At next code snippet, what method is called?
	 *
	 * <pre>
	 * public void fooBar(Object x){System.out.println("object");}
	 * public void fooBar(Number x){System.out.println("number");}
	 *
	 * Integer x = 10;
	 * fooBar(x);
	 * </pre>
	 *
	 * Java compiler always calls the method with the most specific argument type [
	 * <code>fooBar(Integer x)</code>]. In <code>Meditation</code>, this behavior is equivalent to
	 * call the method with minimum distance between the class of <code>x</code> variable and method
	 * parameter classes.
	 *
	 * <pre>
	 * assert distance(Object.class, Integer.class) == 4;
	 * assert distance(Number.class, Integer.class) == 2; // win
	 * </pre>
	 *
	 * So, the method with <code>Number</code> parameter is called because it calculated distance,
	 * 2, is lowest than the distance calculated to the other method, 4. <i>NOTE: when method with
	 * multiple parameters are evaluated, the sum all distances to each parameter class is used in a
	 * sum</i>.
	 *
	 * <p>
	 * <b>The distance between two classes are defined as follow:</b>
	 *
	 * <p>
	 * &#149; NULL ARGUMENT CLASS: the distance from <code>null</code> to the
	 * <code>parameterClass</code> is always 0 (zero), because <code>null</code> can always be
	 * assigned to any variable type. The exception s a primitive class; in this case,
	 * <code>-1</code> will be returned, indicating that assignment is not possible.
	 *
	 * <p>
	 * &#149; NON PRIMITIVE PARAMETER CLASS AND PRIMITIVE ARGUMENT CLASS: the primitive class
	 * specified in <code>argumentClass</code> is converted to it respective wrapper class and
	 * calculation of the distance continues as the calculation of two non primitive types.
	 *
	 * <p>
	 * &#149; PRIMITIVE PARAMETER CLASS AND A NON PRIMITIVE ARGUMENT CLASS: the non primitive
	 * argument class specified in <code>argumentClass</code> is converted to it primitive class,
	 * and the calculation of the distance continues as calculation of two primitive types.
	 *
	 * <p>
	 * &#149; PRIMITIVE PARAMETER CLASS AND A PRIMITIVE ARGUMENT CLASS: the calculation of distance
	 * between two primitive types use the follow tables:
	 *
	 * <pre>
	 * When involving char type
	 * +------+------+-----+------+-------+--------+
	 * | byte | char | int | long | float | double |
	 * +------+------+-----+------+-------+--------+
	 * |    0 |    1 |   2 |    3 |     4 |      5 |
	 * +------+------+-----+------+-------+--------+
	 *
	 * When involving short type
	 * +------+-------+-----+------+-------+--------+
	 * | byte | short | int | long | float | double |
	 * +------+-------+-----+------+-------+--------+
	 * |    0 |     1 |   2 |    3 |     4 |      5 |
	 * +------+-------+-----+------+-------+--------+
	 * </pre>
	 *
	 * The distance of <code>argumentClass</code> (rightmost in the table) and an
	 * <code>parameterClass</code> (leftmost in the table) are the simple difference of its indexes.
	 * <br>
	 * Considering above tables, if the distance of a rightmost class to a leftmost class is not
	 * possible, negative value, then and the distance is defined as <code>-1</code>. <br>
	 * Not that <code>char</code> and <code>short</code> types are not mutually compatible, and the
	 * distance is <code>-1</code>.
	 *
	 * <p>
	 * Exemple:
	 *
	 * <pre>
	 * assert distance(double.class, byte.class) == 5;
	 * assert distance(int.class, byte.class) == 2;
	 * assert distance(long.class, int.class) == 1;
	 * assert distance(float.class, long.class) == 1;
	 *
	 * assert distance(char.class, short.class) == -1;
	 * assert distance(short.class, char.class) == -1;
	 * </pre>
	 *
	 *
	 * <p>
	 * &#149; NON PRIMITIVE PARAMETER CLASS AND NON PRIMITIVE ARGUMENT CLASS: the distance is define
	 * as the minimum path in the inheritance tree of <code>argumentClass</code> need to reach
	 * <code>parameterClass</code>; if this is not possible, them the returned distance is
	 * <code>-1</code>.
	 *
	 * <p>
	 * The salt from a class to it direct super class is defined to a value <code>2</code>, i.e,
	 * given a class <code>A</code> and it direct super class, <code>B</code>, them the distance
	 * between <code>A</code> to <code>B</code> is <code>2</code>. If consider a class
	 * <code>C</code> as direct super type of <code>B</code>, them distance of <code>A</code> to
	 * <code>C</code> is <code>4</code> (the simple sum of distances of <code>A/B</code> and
	 * <code>B/C</code>). This behavior is the same to calculate the distance from class
	 * <code>A</code> and it implementing interfaces.
	 *
	 * <p>
	 * <i>Implementation note:</i> The {@link Null} class is a representation the <code>null</code>
	 * value. If pass <code>Null.class</code> to this method, it will be handled as
	 * <code>null</code>.
	 *
	 * @param parameterClass A class in the inheritance tree of <code>argumentClass</code>
	 *        parameter; cannot be <code>null</code>.
	 *
	 * @param argumentClass The start pointing to calculate the distance; may be <code>null</code>.
	 *
	 * @return The distance, as defined.
	 *
	 * @see Null
	 */
	public static int distance(Class<?> parameterClass, Class<?> argumentClass) {
		Preconditions.checkNotNull(argumentClass, "User %s instead of null", Null.class);
		Preconditions.checkNotNull(parameterClass, "parameterClass");
		Preconditions.checkArgument(
				parameterClass != Null.class,
				"'parameterClass' cannot be %s",
				Null.class);

		if (argumentClass == Null.class) {
			if (parameterClass.isPrimitive()) {
				return -1;
			} else {
				return 0;
			}
		}

		if (!parameterClass.isAssignableFrom(argumentClass)
				&& !parameterClass.isPrimitive()
				&& !argumentClass.isPrimitive()) {
			return -1;
		}

		int distance = distance0(parameterClass, argumentClass);
		return distance;
	}

	private static int distance0(Class<?> parameterClass, Class<?> argumentClass) {
		if (parameterClass.isPrimitive() && argumentClass.isPrimitive()) {
			parameterClass = getPrimitiveRepresentation(parameterClass);
			argumentClass = getPrimitiveRepresentation(argumentClass);
			int distance = distance1(parameterClass, argumentClass);
			return distance;
		}

		if (parameterClass.isPrimitive() && !argumentClass.isPrimitive()) {
			if (!isWrapperType(argumentClass)) {
				return -1;
			}

			parameterClass = getPrimitiveRepresentation(parameterClass);
			argumentClass = getPrimitiveRepresentation(getPrimitiveClass(argumentClass));

			int distance = distance1(parameterClass, argumentClass);
			return distance == -1
					? -1
					: distance + 10000;
		}

		int sum = 0;

		if (!parameterClass.isPrimitive() && argumentClass.isPrimitive()) {
			argumentClass = getWrapperType(argumentClass);
			sum = 1000;
		}

		int distance = distance1(parameterClass, argumentClass);
		return distance == -1
				? -1
				: distance + sum;
	}

	/**
	 * Work as described on {@link #distance(Class, Class)}, but deal only with non primitive types.
	 *
	 * @param parameterClass The type where <code>argumentClass</code> wants to reach.
	 * @param argumentClass The type where start.
	 *
	 * @return The distance from <code>argumentClass</code> to <code>parameterClass</code>.
	 */
	private static int distance1(Class<?> parameterClass, Class<?> argumentClass) {
		if (parameterClass == argumentClass) {
			return 0;
		}

		Class<?> superClass = argumentClass.isInterface()
				? Object.class
				: argumentClass.getSuperclass();
		Class<?>[] interfaces = argumentClass.getInterfaces();

		if (superClass == null && interfaces.length == 0) {
			return -1;
		}

		int distance = superClass != null
				? distance1(parameterClass, superClass)
				: -1;
		for (int i = 0; i < interfaces.length; i++) {
			Class<?> inter = interfaces[i];
			int distanceTemp = distance1(parameterClass, inter);
			if (distance == -1 || distanceTemp != -1 && distance > distanceTemp) {
				distance = distanceTemp;
			}
		}

		return distance == -1
				? distance
				: distance + 1;
	}

	public static Constructor<?> getMostSpecificConstructor(Class<?> cls, List<Class<?>> argumentTypes) {
		ConstructorIterator<?> constructorIterator = ConstructorIterator.create(cls);
		Stream<ConstructorInfoImpl> constructorStream = AyszuCollections.stream(constructorIterator)
				.map(c -> new ConstructorInfoImpl(c));
		ConstructorInfoImpl mostSpecific = getMostSpecificExecutable(constructorStream, argumentTypes);
		return mostSpecific.getConstructor();
	}

	public static Method getMostSpecificMethod(Class<?> cls, List<Class<?>> argumentTypes) {
		Method mostSpecificMethod = getMostSpecificMethod(cls, false, argumentTypes);
		return mostSpecificMethod;
	}

	public static Method getMostSpecificMethod(Class<?> cls, boolean staticOnly, List<Class<?>> argumentTypes) {
		Method mostSpecificMethod = getMostSpecificMethod(cls, null, staticOnly, argumentTypes);
		return mostSpecificMethod;
	}

	public static Method getMostSpecificMethod(
			Class<?> cls,
			String executableName,
			boolean staticOnly,
			List<Class<?>> argumentTypes) {

		MethodIterator methodIterator = MethodIterator.create(cls, staticOnly);
		Stream<MethodInfoImpl> methodStream = AyszuCollections.stream(methodIterator)
				.map(m -> new MethodInfoImpl(m))
				.filter(info -> executableName == null || info.getName().equals(executableName));
		MethodInfoImpl mostSpecific = getMostSpecificExecutable(methodStream, argumentTypes);
		return mostSpecific.getMethod();
	}

	public static <E extends ExecutableInfo> E getMostSpecificExecutable(
			Stream<E> stream,
			List<Class<?>> argumentTypes) {

		Single<Integer> smallerMethodWeight = new SynchronizedSingle<>();

		List<E> methods = stream.map(m -> {
			int methodWeight = MeditationUtils.methodWeight(m, argumentTypes);

			if (methodWeight >= 0
					&& (smallerMethodWeight.getItem() == null
							|| smallerMethodWeight.getItem() > methodWeight)) {
				smallerMethodWeight.setItem(methodWeight);
			}

			Pair<E, Integer> pair = methodWeight >= 0
					? new Pair<>(m, methodWeight)
					: null;

			return pair;
		}).filter(p -> p != null)
				.collect(Collectors.toList()).stream()
				.filter(p -> p.getSecond().equals(smallerMethodWeight.getItem()))
				.map(p -> p.getFirst())
				.collect(Collectors.toList());

		if (methods.isEmpty()) {
			throwNoSuchMethodException();
		}

		if (methods.size() == 1) {
			E executable = methods.get(0);
			return executable;
		}

		E mostSpecific = null;
		Map<Integer, List<Class<?>>> types = new HashMap<>();
		for (Iterator<E> iterator = methods.iterator(); iterator.hasNext();) {
			E method = iterator.next();
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (int i = 0; i < argumentTypes.size(); i++) {
				Class<?> argumentType = argumentTypes.get(i);

				// if (argumentType != Null.class) {
				// continue;
				// }

				Class<?> parameterType = parameterTypes[Math.min(i, parameterTypes.length - 1)];

				if (!types.containsKey(i)) {
					types.put(i, new ArrayList<>(5));
				}

				List<Class<?>> list = types.get(i);
				boolean found = false;
				for (ListIterator<Class<?>> iterator2 = list.listIterator(); iterator2.hasNext();) {
					Class<?> class1 = iterator2.next();

					if (mostSpecific == null || MeditationUtils.distance(class1, parameterType) >= 0) {
						mostSpecific = method;
					}

					if (parameterType == class1) {
						break;
					}

					if (MeditationUtils.distance(parameterType, class1) >= 0) {
						found = true;
						break;
					}
					if (MeditationUtils.distance(class1, parameterType) >= 0) {
						iterator2.set(parameterType);
						found = true;
						break;
					}
				}

				if (!found) {
					list.add(parameterType);
					if (mostSpecific == null) {
						mostSpecific = method;
					}
				}
			}
		}

		if (types.values().stream().mapToInt(l -> l.size()).filter(i -> i > 1).count() > 0) {
			throwAmbiguityException(methods, argumentTypes);
		}

		return mostSpecific;
	}

	private static String argumentTypesNameList(List<Class<?>> argumentTypes) {
		String argumentNames = argumentTypes.stream()
				.map(a -> a == Null.class ? "null" : a.getName())
				.reduce((n1, n2) -> n1 + ", " + n2)
				.orElse("<no arguments>");
		return argumentNames;
	}

	private static void throwAmbiguityException(
			List<? extends ExecutableInfo> ambiguousMethods,
			List<Class<?>> argumentTypes) {
		String argumentNames = argumentTypesNameList(argumentTypes);
		String ambiguousMethodStringList = ambiguousMethods.stream()
				.map(m -> m.toString())
				.reduce("\t  Methods in ambiguous state to given argument types:",
						(m1, m2) -> m1 + System.lineSeparator() + "\t> " + m2);

		String message = String.format(
				"Multiples match methods were foud capable to receive the list of argument with types [%s]"
						+ System.lineSeparator()
						+ "%s" + System.lineSeparator(),
				argumentNames,
				ambiguousMethodStringList);

		throw new AmbiguityException(message);
	}

	private static void throwNoSuchMethodException() {
		throw new MethodNotFoundException();
	}

	public static int methodWeight(ExecutableInfo methodInfo, List<Class<?>> argumentTypes) {
		Class<?>[] parameterTypes = methodInfo.getParameterTypes();
		List<Class<?>> list = AyszuCollections.list(parameterTypes);

		int methodWeight = methodWeight(list, argumentTypes, methodInfo.isVarArgs());
		return methodWeight;
	}

	public static int methodWeight(Executable executable, List<Class<?>> argumentTypes) {
		Class<?>[] parameterTypes = executable.getParameterTypes();
		List<Class<?>> list = AyszuCollections.list(parameterTypes);

		int methodWeight = methodWeight(list, argumentTypes, executable.isVarArgs());
		return methodWeight;
	}

	private static int methodWeight(List<Class<?>> parameterTypes, List<Class<?>> argumentTypes, boolean isVarArgs) {
		if (!isVarArgs && parameterTypes.size() != argumentTypes.size()) {
			return -1;
		}

		int fixedParametersLength = isVarArgs
				? parameterTypes.size() - 1
				: parameterTypes.size();

		if (fixedParametersLength > argumentTypes.size()) {
			return -1;
		}

		int sum = 0;

		for (int i = 0; i < fixedParametersLength; i++) {
			Class<?> parameterType = parameterTypes.get(i);
			Class<?> argumentType = argumentTypes.get(i);

			int distance = MeditationUtils.distance(parameterType, argumentType);
			if (distance == -1) {
				return -1;
			}
			sum += distance;
		}

		if (isVarArgs) {
			Class<?> varArgType = parameterTypes.get(parameterTypes.size() - 1);

			if (argumentTypes.size() - fixedParametersLength == 1) {
				int index = argumentTypes.size() - 1;
				Class<?> lastArgumentType = argumentTypes.get(index);
				if (lastArgumentType.isArray()) {
					if (varArgType == lastArgumentType) {
						return sum;
					} else {
						return -1;
					}
				}
			}

			sum++;

			Class<?> arrayComponentType = varArgType.getComponentType();

			for (int i = fixedParametersLength; i < argumentTypes.size(); i++) {
				Class<?> argumentType = argumentTypes.get(i);
				int distance = MeditationUtils.distance(arrayComponentType, argumentType);
				if (distance == -1) {
					return -1;
				}

				sum += distance;
			}
		}

		return sum;
	}

	/**
	 * Returns the wrapper <code>Class</code> to given a primitive type specified at parameter
	 * <code>primitiveClass</code>.
	 *
	 * @param primitiveClass The primitive class.
	 *
	 * @return the wrapper class to given <code>primitiveClass</code>, or the same
	 *         value passed as argument, if it is not a primitive class.
	 */
	public static Class<?> getWrapperType(Class<?> primitiveClass) {

		if (boolean.class == primitiveClass) {
			return Boolean.class;
		}
		if (byte.class == primitiveClass) {
			return Byte.class;
		}
		if (short.class == primitiveClass) {
			return Short.class;
		}
		if (char.class == primitiveClass) {
			return Character.class;
		}
		if (int.class == primitiveClass) {
			return Integer.class;
		}
		if (long.class == primitiveClass) {
			return Long.class;
		}
		if (float.class == primitiveClass) {
			return Float.class;
		}
		if (double.class == primitiveClass) {
			return Double.class;
		}

		return primitiveClass;
	}

	/**
	 * Returns the primitive <code>Class</code> to given <code>wrapperClass</code>.
	 *
	 * @param wrapperClass The wrapper class.
	 *
	 * @return The primitive <code>Class</code> to given <code>wrapperClass</code>, or the same
	 *         value passed as argument, if it is not a wrapper class.
	 */
	public static Class<?> getPrimitiveClass(Class<?> wrapperClass) {

		if (Boolean.class == wrapperClass) {
			return boolean.class;
		}
		if (Byte.class == wrapperClass) {
			return byte.class;
		}
		if (Short.class == wrapperClass) {
			return short.class;
		}
		if (Character.class == wrapperClass) {
			return char.class;
		}
		if (Integer.class == wrapperClass) {
			return int.class;
		}
		if (Long.class == wrapperClass) {
			return long.class;
		}
		if (Float.class == wrapperClass) {
			return float.class;
		}
		if (Double.class == wrapperClass) {
			return double.class;
		}

		return wrapperClass;
	}

	/**
	 * Check if given <code>wrapperClass</code> is a wrapper type. Returns <code>true</code> if
	 * given <code>wrapperClass</code> argument is passed with some of follow classes:
	 *
	 * <ul>
	 * <li>Boolean.class</li>
	 * <li>Byte.class</li>
	 * <li>Short.class</li>
	 * <li>Character.class</li>
	 * <li>Integer.class</li>
	 * <li>Long.class</li>
	 * <li>Float.class</li>
	 * <li>Double.class</li>
	 * </ul>
	 *
	 * @param wrapperClass The type to check.
	 *
	 * @return <code>true</code> if the given parameter is a wrapper type, of <code>false</code>
	 *         otherwise.
	 */
	public static boolean isWrapperType(Class<?> wrapperClass) {
		return Boolean.class == wrapperClass
				|| Byte.class == wrapperClass
				|| Short.class == wrapperClass
				|| Character.class == wrapperClass
				|| Integer.class == wrapperClass
				|| Long.class == wrapperClass
				|| Float.class == wrapperClass
				|| Double.class == wrapperClass;
	}

	/**
	 * Returns a array with the <code>Class</code> type of each object in the <code>arguments</code>
	 * array. If some value of array is <code>null</code>, the <code>Null.class</code> is returned
	 * in
	 * the respective position; {@link Null} is a internal class to represent the <code>null</code>
	 * keyword.
	 *
	 * @param arguments The list of objects.
	 *
	 * @return A array with classes to each value in given argument array.
	 *
	 * @see Null
	 *
	 * @throws NullPointerException If <code>arguments</code> parameters is informed with
	 *         <code>null</code> value.
	 */
	public static Class<?>[] types(Object[] arguments) {
		Class<?>[] types = new Class<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			Object arg = arguments[i];

			types[i] = arg == null
					? Null.class
					: arg.getClass();
		}
		return types;
	}

	/**
	 * Returns a <code>Class</code> of an internal representation of primitive type.
	 *
	 * @param primitiveType The primitive type.
	 *
	 * @return The internal representation of primitive type.
	 *
	 * @see BooleanPrimitive
	 * @see BytePrimitive
	 * @see ShortPrimitive
	 * @see CharacterPrimitive
	 * @see IntegerPrimitive
	 * @see LongPrimitive
	 * @see FloatPrimitive
	 * @see DoublePrimitive
	 */
	private static Class<?> getPrimitiveRepresentation(Class<?> primitiveType) {
		if (boolean.class == primitiveType) {
			return BooleanPrimitive.class;
		}
		if (byte.class == primitiveType) {
			return BytePrimitive.class;
		}
		if (short.class == primitiveType) {
			return ShortPrimitive.class;
		}
		if (char.class == primitiveType) {
			return CharacterPrimitive.class;
		}
		if (int.class == primitiveType) {
			return IntegerPrimitive.class;
		}
		if (long.class == primitiveType) {
			return LongPrimitive.class;
		}
		if (float.class == primitiveType) {
			return FloatPrimitive.class;
		}
		if (double.class == primitiveType) {
			return DoublePrimitive.class;
		}

		return primitiveType;
	}

	/**
	 * Internal representation of <code>boolean</code> primitive type. This class is used to build
	 * a inheritance three of primitive types, in order to keep available possibles assignments
	 * between
	 * primitive types.
	 *
	 * <pre>
	 *   double               boolean
	 *      ^
	 *      |
	 *    float
	 *      ^
	 *      |
	 *    long
	 *      ^
	 *      |
	 *     int
	 *     ^ ^
	 *    /   \
	 * short char
	 *   ^     ^
	 *    \   /
	 *     byte
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static interface BooleanPrimitive {
	}

	/**
	 * Internal representation of <code>double</code> primitive type. This class is used to build
	 * a inheritance three of primitive types, in order to keep available possibles assignments
	 * between
	 * primitive types.
	 *
	 * <pre>
	 *   double               boolean
	 *      ^
	 *      |
	 *    float
	 *      ^
	 *      |
	 *    long
	 *      ^
	 *      |
	 *     int
	 *     ^ ^
	 *    /   \
	 * short char
	 *   ^     ^
	 *    \   /
	 *     byte
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static interface DoublePrimitive {
	}

	/**
	 * Internal representation of <code>float</code> primitive type. This class is used to build
	 * a inheritance three of primitive types, in order to keep available possibles assignments
	 * between
	 * primitive types.
	 *
	 * <pre>
	 *   double               boolean
	 *      ^
	 *      |
	 *    float
	 *      ^
	 *      |
	 *    long
	 *      ^
	 *      |
	 *     int
	 *     ^ ^
	 *    /   \
	 * short char
	 *   ^     ^
	 *    \   /
	 *     byte
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static interface FloatPrimitive extends DoublePrimitive {
	}

	/**
	 * Internal representation of <code>long</code> primitive type. This class is used to build
	 * a inheritance three of primitive types, in order to keep available possibles assignments
	 * between
	 * primitive types.
	 *
	 * <pre>
	 *   double               boolean
	 *      ^
	 *      |
	 *    float
	 *      ^
	 *      |
	 *    long
	 *      ^
	 *      |
	 *     int
	 *     ^ ^
	 *    /   \
	 * short char
	 *   ^     ^
	 *    \   /
	 *     byte
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static interface LongPrimitive extends FloatPrimitive {
	}

	/**
	 * Internal representation of <code>int</code> primitive type. This class is used to build
	 * a inheritance three of primitive types, in order to keep available possibles assignments
	 * between
	 * primitive types.
	 *
	 * <pre>
	 *   double               boolean
	 *      ^
	 *      |
	 *    float
	 *      ^
	 *      |
	 *    long
	 *      ^
	 *      |
	 *     int
	 *     ^ ^
	 *    /   \
	 * short char
	 *   ^     ^
	 *    \   /
	 *     byte
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static interface IntegerPrimitive extends LongPrimitive {
	}

	/**
	 * Internal representation of <code>char</code> primitive type. This class is used to build
	 * a inheritance three of primitive types, in order to keep available possibles assignments
	 * between
	 * primitive types.
	 *
	 * <pre>
	 *   double               boolean
	 *      ^
	 *      |
	 *    float
	 *      ^
	 *      |
	 *    long
	 *      ^
	 *      |
	 *     int
	 *     ^ ^
	 *    /   \
	 * short char
	 *   ^     ^
	 *    \   /
	 *     byte
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static interface CharacterPrimitive extends IntegerPrimitive {
	}

	/**
	 * Internal representation of <code>short</code> primitive type. This class is used to build
	 * a inheritance three of primitive types, in order to keep available possibles assignments
	 * between
	 * primitive types.
	 *
	 * <pre>
	 *   double               boolean
	 *      ^
	 *      |
	 *    float
	 *      ^
	 *      |
	 *    long
	 *      ^
	 *      |
	 *     int
	 *     ^ ^
	 *    /   \
	 * short char
	 *   ^     ^
	 *    \   /
	 *     byte
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static interface ShortPrimitive extends IntegerPrimitive {
	}

	/**
	 * Internal representation of <code>byte</code> primitive type. This class is used to build
	 * a inheritance three of primitive types, in order to keep available possibles assignments
	 * between
	 * primitive types.
	 *
	 * <pre>
	 *   double               boolean
	 *      ^
	 *      |
	 *    float
	 *      ^
	 *      |
	 *    long
	 *      ^
	 *      |
	 *     int
	 *     ^ ^
	 *    /   \
	 * short char
	 *   ^     ^
	 *    \   /
	 *     byte
	 * </pre>
	 *
	 * @author Welyab Paula
	 */
	private static interface BytePrimitive extends ShortPrimitive, CharacterPrimitive {
	}

	static interface B {

		int b = 0;
	}

	static interface C {

		int b = 0;
	}

	static class A implements B, C {
	}

	public static boolean isAmbiguousField(Class<?> cls, String fieldName) {
		// if (isFieldPresent(cls, fieldName)) {
		// return false;
		// }
		//
		// List<Class<?>> list = a(cls, fieldName);
		//
		// if(list.isEmpty())

		return false;
	}

	private static List<Class<?>> a(Class<?> cls, String fieldName) {
		SuperTypesIterator superTypesIterator = SuperTypesIterator.create(cls);
		return AyszuCollections.stream(superTypesIterator)
				.filter(c -> isFieldPresent(c, fieldName))
				.distinct()
				.collect(Collectors.toList());
	}

	/**
	 * Check existence of a <code>Field</code> with name specified on <code>fieldName</code>
	 * parameter in the <code>cls</code> <code>Class</code>. This method does not search the field
	 * in super class or interfaces of <code>cls</code> <code>Class</code>.
	 *
	 * @param cls The <code>Class</code>.
	 * @param fieldName The <code>Field</code> name.
	 *
	 * @return Return <code>true</code> if <code>cls</code> <code>Class</code> has a field with
	 *         specified name, of <code>false</code>, otherwise.
	 */
	private static boolean isFieldPresent(Class<?> cls, String fieldName) {
		return getDeclaredField(cls, fieldName) != null;
	}

	/**
	 * Get declared <code>Field</code> in given <code>cls</code> <code>Class</code> parameter. This
	 * method does not search the field in super class or interfaces of <code>cls</code>
	 * <code>Class</code>.
	 *
	 * @param cls The <code>Class</code>.
	 * @param fieldName The <code>Field</code> name.
	 *
	 * @return The declared <code>Field</code>, or <code>null</code> if no field is found.
	 */
	private static Field getDeclaredField(Class<?> cls, String fieldName) {
		try {
			return cls.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			return null;
		} catch (SecurityException e) {
			throw new MeditationException(e);
		}
	}

	/**
	 * Represents the <code>null</code> in some methods of this class.
	 *
	 * @author Welyab Paula
	 *
	 * @see MeditationUtils#distance(Class, Class)
	 * @see MeditationUtils#getMostSpecificConstructor(Class, List)
	 * @see MeditationUtils#getMostSpecificExecutable(Stream, List)
	 * @see MeditationUtils#getMostSpecificMethod(Class, List)
	 * @see MeditationUtils#getMostSpecificMethod(Class, boolean, List)
	 * @see MeditationUtils#getMostSpecificMethod(Class, String, boolean, List)
	 */
	public static final class Null {

		private Null() {
			throw new UnsupportedOperationException("No intance for you");
		}
	}
}

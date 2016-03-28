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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

/**
 * A <code>MethodIterator</code> iterates over methods of a type (like <code>List</code> class,
 * <code>String</code> class...). It searches methods from the base type and it super types and has
 * a lazy behavior, because goes to super types as required.
 *
 * <p>
 * When a method entry is found on a class, a method with same signature (method name with it
 * parameters) in an super class or interface will be not supplied again. The check of methods with
 * same signatures is made with its names and parameters type names, i.e., the type of parameters
 * are not used, but the <code>String</code> representation of the name.
 *
 * @author Welyab Paula
 */
public class MethodIterator implements Iterator<Method> {

	/**
	 * The unique instance of <code>MethodComparator</code>.
	 */
	private final MethodComparator METHOD_COMPARATOR = new MethodComparator();

	private final Deque<Class<?>> deque;

	private final Set<Method> backup;

	/**
	 * Indicates if only static methods must be considerate.
	 */
	private final boolean staticOnly;

	/**
	 * The next <code>Method</code> that will be returned by {@link #next} method. This field must
	 * be set to <code>null</code> after each call to {@link #next()}; the method
	 * {@link #prepareNext()} must set this field with next available <code>Method</code>, when
	 * there is one.
	 *
	 * @see #prepareNext()
	 * @see #hasNext()
	 * @see #next()
	 */
	private Method next;

	/**
	 * The next available <code>Method</code> in {@link #methods} arrays.
	 *
	 * @see #prepareNext()
	 */
	private int index;

	/**
	 * The list of declared method of some class.
	 *
	 * @see #prepareNext()
	 */
	private Method[] methods;

	private int currentIndex;

	public MethodIterator(Class<?> cls) {
		this(cls, false);
	}

	/**
	 * Creates a new <code>MethodIterator</code> using given argument values. If the
	 * <code>staticOnly</code> argument is <code>true</code>, then only static method declared on
	 * <code>cls</code> will be considerate (declared methods on super classes will don't).
	 *
	 * @param cls The class.
	 *
	 * @param staticOnly If only static method must be considerate.
	 */
	public MethodIterator(Class<?> cls, boolean staticOnly) {
		this.staticOnly = staticOnly;

		currentIndex = 0;

		deque = new LinkedList<>();
		deque.addFirst(cls);

		backup = new TreeSet<>(METHOD_COMPARATOR);
	}

	@Override
	public boolean hasNext() {
		prepareNext();
		return next != null;
	}

	@Override
	public Method next() {
		prepareNext();
		checkNext();

		Method method = next;
		next = null;

		currentIndex++;

		return method;
	}

	/**
	 * Checks for a next <code>Method</code> to consumes.
	 *
	 * @throws NoSuchElementException Thrown in case of no <code>Method</code> found.
	 */
	private void checkNext() {
		if (next == null) {
			throw new NoSuchElementException();
		}
	}

	private void prepareNext() {
		if (next != null) {
			return;
		}

		while (methods != null && index < methods.length) {
			Method method = methods[index];
			index++;

			boolean isStaticMethod = Modifier.isStatic(method.getModifiers());
			if (staticOnly && !isStaticMethod) {
				continue;
			}

			if (backup.contains(method)) {
				continue;
			}

			backup.add(method);
			next = method;
			return;
		}

		while (next == null && !deque.isEmpty()) {
			Class<?> cls = deque.removeLast();

			if (!staticOnly) {
				Class<?> clsSuperClass = cls.getSuperclass();
				if (clsSuperClass != null) {
					deque.addFirst(clsSuperClass);
				}
			}

			methods = cls.getDeclaredMethods();
			index = 0;

			prepareNext();
		}
	}

	/**
	 * Creates a new <code>MethodIterator</code> using given parameters.
	 *
	 * @param cls
	 * @param staticOnly
	 *
	 * @return The created <code>MethodIterator</code>.
	 */
	public static MethodIterator create(Class<?> cls, boolean staticOnly) {
		MethodIterator methodIterator = new MethodIterator(cls, staticOnly);
		return methodIterator;
	}

	/**
	 * Creates a new <code>MethodIterator</code> using given <code>cls</code> <code>Class</code>
	 * parameter.
	 *
	 * <p>
	 * Call this method works as calling
	 *
	 * <pre>
	 * MethodIterator.create(cls, false);
	 * </pre>
	 *
	 * @param cls
	 * @return
	 */
	public static MethodIterator create(Class<?> cls) {
		MethodIterator methodIterator = new MethodIterator(cls);
		return methodIterator;
	}

	/**
	 * Compares methods as defined in {@link MethodComparator#compare(Method, Method)}.
	 *
	 * @author Welyab Paula
	 */
	private static final class MethodComparator implements Comparator<Method> {

		/**
		 * Compares two methods by using its names and parameters type names. In other words, all
		 * comparison is made using <code>Strings</code>.
		 *
		 * <p>
		 * The comparison is made in follow order:
		 *
		 * <ul>
		 * <li>
		 * The method name.
		 * </li>
		 * <li>
		 * The method parameter length.
		 * </li>
		 * <li>
		 * Parameters type name at ordering in the method.
		 * </li>
		 * </ul>
		 *
		 * @param method1 The first method.
		 * @param method2 The second method.
		 *
		 * @return The comparison.
		 */
		@Override
		public int compare(Method method1, Method method2) {
			int parameterCount1 = method1.getParameterCount();
			int parameterCount2 = method2.getParameterCount();

			if (parameterCount1 != parameterCount2) {
				return parameterCount1 - parameterCount2;
			}

			String methodName1 = method1.getName();
			String methodName2 = method2.getName();

			int nameComparison = methodName1.compareTo(methodName2);
			if (nameComparison != 0) {
				return nameComparison;
			}

			Class<?>[] parameterTypes1 = method1.getParameterTypes();
			Class<?>[] parameterTypes2 = method2.getParameterTypes();

			for (int i = 0; i < parameterTypes1.length; i++) {
				Class<?> parameterType1 = parameterTypes1[i];
				Class<?> parameterType2 = parameterTypes2[i];

				String parameterTypeName1 = parameterType1.getName();
				String parameterTypeName2 = parameterType2.getName();

				int parameterTypeNameComparison = parameterTypeName1.compareTo(parameterTypeName2);
				if (parameterTypeNameComparison != 0) {
					return parameterTypeNameComparison;
				}
			}

			return 0;
		}
	}
}

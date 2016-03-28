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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

import com.google.common.base.Preconditions;

public class Meditation {

	/**
	 * Used to replace a <i>vararg</i> argument when it is passed as <code>null</code>.
	 */
	private static final Object[] SINGLE_NULL_VALUE_ARRAY = {
			null
	};

	/**
	 * The wrapped object.
	 *
	 * <p>
	 * If this object is a instance of <code>Class</code>, them is will be used to access only
	 * static <code>Fields</code>, <code>Methods</code> or <code>Constructors</code>. Otherwise, if
	 * this field has any other kind of <code>Object</code> instance (or <code>null</code>) it will
	 * be used to access instance <code>Fields</code> or call instance <code>Methods</code> as well;
	 * call <code>Constructors</code> is possible to create new instances of the same type class of
	 * this field.
	 */
	private final Object object;

	/**
	 * Indicates if java language access check must be suppressed in calls to
	 * <code>Constructors</code>, <code>Methods</code> or <code>Fields</code>.
	 */
	private final boolean accessible;

	/**
	 * Creates a new <code>Meditation</code> to works over given <code>object</code>, and with given
	 * <code>accessible</code> flag.
	 *
	 * @param object The object.
	 *
	 * @param accessible The flag.
	 */
	private Meditation(Object object, boolean accessible) {
		this.object = object;
		this.accessible = accessible;
	}

	public Meditation create(Object... arguments) {
		ConstructorSelector constructorSelector = Selectors.constructors()
				.mostSpecific()
				.selector();

		Meditation meditation = create(constructorSelector, arguments);
		return meditation;
	}

	public Meditation create(ConstructorSelector selector, Object... arguments) {
		arguments = adjustArguments(arguments);

		Class<?> type = type();
		Class<?>[] types = MeditationUtils.types(arguments);

		Constructor<?> constructor = selector.select(type).usingArguments(types);

		ConstructorCaller constructorCaller = new ConstructorCallerImpl(constructor, accessible);
		Object instance = constructorCaller.call(arguments);

		Meditation meditation = new Meditation(instance, accessible);
		return meditation;
	}

	/**
	 * Call a no arguments method with name specified in <code>methodName</code> parameter.
	 *
	 * <p>
	 * If the wrapped object is a <code>Class</code>, than only static methods will be considered.
	 * IF the wrapped object is a instance of any other type, than all method will be considered.
	 *
	 * @param methodName The method name.
	 *
	 * @return Returns the result, <code>null</code> inclusive, of call the selected method wrapped
	 *         into a <code>Meditation</code> object to further usage. If the selected method has no
	 *         return type, i.e., <code>void</code>, them <code>this</code> <code>Meditation</code>
	 *         is itself returned. The returned <code>Meditation</code> will have the same
	 *         <code>accessible</code> flag of <code>this</code> <code>Meditation</code>.
	 */
	public Meditation call(String methodName) {
		Meditation meditation = call(methodName, new Object[] {});
		return meditation;
	}

	/**
	 * Call a method with name specified in <code>methodName</code> parameter. The list of
	 * <code>arguments</code> are passed to the call of selected method.
	 *
	 * <p>
	 * If the wrapped object is a <code>Class</code>, than only static methods will be considered.
	 * IF the wrapped object is a instance of any other type, than all method will be considered.
	 *
	 * <p>
	 * Call this method with <code>null</code> value in <code>arguments</code> parameter will be
	 * interpreted as array of single position, with <code>null</code> value in that position. The
	 * follow code fragments has the same result:
	 *
	 * <pre>
	 * 1) call(methodName, null);
	 * 2) call(methodName, new Object[]{null});
	 * </pre>
	 *
	 * @param methodName The name of method to call.
	 * @param arguments
	 *
	 * @return Returns the result, <code>null</code> inclusive, of call the selected method wrapped
	 *         into a <code>Meditation</code> object to further usage. If the selected method has no
	 *         return type, i.e., <code>void</code>, them <code>this</code> <code>Meditation</code>
	 *         is itself returned. The returned <code>Meditation</code> will have the same
	 *         <code>accessible</code> flag of <code>this</code> <code>Meditation</code>.
	 */
	public Meditation call(String methodName, Object... arguments) {
		MethodSelector selector = Selectors.methods()
				.filter(info -> info.getName().equals(methodName))
				.mostSpecific()
				.selector();

		Meditation meditation = call(selector, arguments);
		return meditation;
	}

	/**
	 * Use the given <code>MethodSelector</code> parameter in order to select a method to call. To
	 * call the selected method, the list of arguments are passed to it.
	 *
	 * <p>
	 * If the wrapped object is a <code>Class</code>, than only static methods will be considered.
	 * IF the wrapped object is a instance of any other type, than all method will be considered.
	 *
	 * <p>
	 * Call this method with <code>null</code> value in <code>arguments</code> parameter will be
	 * interpreted as array of single position, with <code>null</code> value in that position. The
	 * follow code fragments has the same result:
	 *
	 * <pre>
	 * 1) call(selector, null);
	 * 2) call(selector, new Object[]{null});
	 * </pre>
	 *
	 * @param selector The <code>MethodSelector</code>.
	 *
	 * @param arguments The list of arguments to be passed to the selected method.
	 *
	 * @return Returns the result, <code>null</code> inclusive, of call the selected method wrapped
	 *         into a <code>Meditation</code> object to further usage. If the selected method has no
	 *         return type, i.e., <code>void</code>, them <code>this</code> <code>Meditation</code>
	 *         is itself returned. The returned <code>Meditation</code> will have the same
	 *         <code>accessible</code> flag of <code>this</code> <code>Meditation</code>.
	 *
	 * @see Selectors
	 */
	public Meditation call(MethodSelector selector, Object... arguments) {
		arguments = adjustArguments(arguments);

		Class<?> type = type();
		Class<?>[] types = MeditationUtils.types(arguments);

		Method method = selector.select(type).usingArguments(types);

		Object instance = isClass()
				? null
				: object;

		MethodCaller methodCaller = new MethodCallerImpl(instance, method, accessible);
		Object result = methodCaller.call(arguments);

		if (!methodCaller.isReturnable()) {
			return this;
		}

		Meditation meditation = new Meditation(result, accessible);
		return meditation;
	}

	/**
	 * Retrieve the value of field with name specified in <code>fieldName</code> parameter.
	 *
	 * @param fieldName The name of the field.
	 *
	 * @return Returns the value of field, <code>null</code> inclusive, wrapped into a a
	 *         <code>Meditation</code> object to further usage. The returned <code>Meditation</code>
	 *         will have the same <code>accessible</code> flag of <code>this</code>
	 *         <code>Meditation</code>.
	 */
	public Meditation field(String fieldName) {
		FieldSelector fieldSelector = Selectors.fields()
				.filter(info -> info.getName().equals(fieldName))
				.selector();

		Meditation meditation = field(fieldSelector);
		return meditation;
	}

	/**
	 * Retrieve the value of field specified by given <code>fieldSelector</code> parameter.
	 *
	 * @param fieldSelector The <code>FieldSelector</code>.
	 *
	 * @return Returns the value of field, <code>null</code> inclusive, wrapped into a a
	 *         <code>Meditation</code> object to further usage. The returned <code>Meditation</code>
	 *         will have the same <code>accessible</code> flag of <code>this</code>
	 *         <code>Meditation</code>.
	 *
	 * @see Selectors
	 */
	public Meditation field(FieldSelector fieldSelector) {
		Field field = fieldSelector.select(type()).get();

		Object instance = isClass()
				? null
				: object;

		FieldCaller fieldCaller = new FieldCallerImpl(instance, field, accessible);
		Object value = fieldCaller.call();

		Meditation meditation = new Meditation(value, accessible);
		return meditation;
	}

	/**
	 * Check whether wrapped object is a <code>Class</code> instance, or any other type of object (
	 * <code>null</code> is allowed here).
	 *
	 * @return A value <code>true</code> if this <code>Meditation</code> wraps a <code>Class</code>
	 *         instance, of <code>false</code> otherwise.
	 */
	public boolean isClass() {
		return object != null && object instanceof Class;
	}

	/**
	 * Returns the type of wrapped object.
	 *
	 * @return The type of wrapped object.
	 *
	 * @throws NullPointerException Rises if wrapped object is <code>null</code>.
	 */
	public Class<?> type() {
		Preconditions.checkNotNull(object, "The wrapped object is null");

		return object instanceof Class
				? (Class<?>) object
				: object.getClass();
	}

	public <E> E as(Class<E> cls) {
		Class<?>[] interfaces = {
				cls
		};
		ClassLoader classLoader = cls.getClassLoader();

		@SuppressWarnings("unchecked")
		E proxy = (E) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				MethodCaller methodCaller = new MethodCallerImpl(object, method, accessible);
				Object result = methodCaller.call(args);
				return result;
			}
		});

		return proxy;
	}

	/**
	 * Returns the wrapped object; may be a <code>Class</code> instance.
	 *
	 * @return The wrapped object.
	 */
	public <E> E get() {
		@SuppressWarnings("unchecked")
		E result = (E) object;
		return result;
	}

	public <E> E get(String fieldName) {
		return field(fieldName).get();
	}

	public <E> E get(FieldSelector selector) {
		return field(selector).get();
	}

	public Meditation set(String fieldName, Object value) {
		FieldSelector selector = Selectors.fields()
				.filter(info -> info.getName().equals(fieldName))
				.selector();

		Meditation meditation = set(selector, value);
		return meditation;
	}

	public Meditation set(FieldSelector selector, Object value) {
		Field field = selector.select(type()).get();
		Object instance = isClass()
				? null
				: object;
		FieldSetter fieldSetter = new FieldSetterImpl(object, field, accessible);
		fieldSetter.set(value);
		return this;
	}

	/**
	 * Apply the value returned by {@link #get()} in given <code>consumer</code> parameter. This
	 * method returns this <code>Meditation</code> with wrapped object and possibles changes
	 * performed by specific <code>consumer</code> function after calling.
	 *
	 * @param consumer The <code>Consumer</code> to wrapped object.
	 *
	 * @return This <code>Meditation</code>, further use.
	 */
	public Meditation consumer(Consumer<?> consumer) {
		consumer.accept(get());
		return this;
	}

	/**
	 * Indicates to this <code>Meditation</code> to suppress the Java language access check. As
	 * <code>Meditation</code> works on top of Java Reflection API, this method just set to
	 * <code>true</code> the accessible flag ({@link AccessibleObject#setAccessible(boolean)}; an
	 * <i>accessible object</i> is a class constructor, a field, or a method declaration).
	 *
	 * <p>
	 * <code>Meditations</code> derived from <code>this</code> instance will have the same
	 * <code>accessible</code> flag value.
	 *
	 * @return A new <code>Meditation</code>, with <code>accessible</code> flag set to
	 *         <code>true</code>.
	 *
	 * @see AccessibleObject#setAccessible(boolean)
	 * @see #accessible(boolean)
	 */
	public Meditation accessible() {
		Meditation meditation = accessible(true);
		return meditation;
	}

	/**
	 * Sets the <code>accessible</code> flag to this <code>Meditation</code>, indicating whether
	 * Java language access check must be suppressed. As <code>Meditation</code> works on top of
	 * Java Reflection API, this method just set to the accessible flag with given parameter value (
	 * {@link AccessibleObject#setAccessible(boolean)}; an <i>accessible object</i> is a class
	 * constructor, a field, or a method declaration).
	 *
	 * <p>
	 * <code>Meditations</code> derived from <code>this</code> instance will have the same
	 * <code>accessible</code> flag value.
	 *
	 * @param accessible The new value flag.
	 *
	 * @return A new <code>Meditation</code>, with <code>accessible</code> flag set to given
	 *         parameter value.
	 */
	public Meditation accessible(boolean accessible) {
		if (accessible == this.accessible) {
			return this;
		}

		Meditation meditation = new Meditation(object, accessible);
		return meditation;
	}

	/**
	 * Check if <code>arguments</code> is <code>null</code>, returning a array with length
	 * <code>1</code> with a <code>null</code> value at index <code>0</code> in this case. If the
	 * <code>arguments</code> parameter is not <code>null</code>, them this function return
	 * parameter passed.
	 *
	 * @param arguments The array to check.
	 *
	 * @return The array adjusted.
	 */
	private Object[] adjustArguments(Object[] arguments) {
		return arguments == null
				? SINGLE_NULL_VALUE_ARRAY
				: arguments;
	}

	/**
	 * Creates a <code>Meditation</code> over the <code>object</code> instance.
	 * <code>Meditation</code> over <code>Class</code> objects are generally used to accessing
	 * static fields and methods.
	 *
	 * <p>
	 * The created <code>Meditation</code> has the <code>accessible</code> flag set to
	 * <code>false</code>.
	 *
	 * <pre>
	 * Object o = "FooBar";
	 *
	 * Object s = Meditation.on(o)
	 * 	.call("substring", 3);
	 *
	 * assert s.equals("Bar") == true;
	 * </pre>
	 *
	 * @param object The instance of some object.
	 *
	 * @return The <code>Meditation</code> over the <code>object</code> parameter.
	 *
	 * @see Meditation
	 * @see #accessible()
	 */
	public static Meditation on(Object object) {
		Meditation meditation = new Meditation(object, false);
		return meditation;
	}

	/**
	 * Creates a <code>Meditation</code> over the <code>Class cls</code> parameter.
	 *
	 * <p>
	 * The created <code>Meditation</code> has the <code>accessible</code> flag set to
	 * <code>false</code>.
	 *
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * Object s = Meditation
	 * 	.on(String.class)
	 * 	.create("FooBar")
	 * 	.call("substring", 3);
	 *
	 * assert s.equals("Bar") == true;
	 * </pre>
	 *
	 * @param cls The <code>Class</code> object.
	 *
	 * @return The <code>Meditation</code> over <code>Class</code> object of <code>cls</code>
	 *         parameter.
	 *
	 * @see Meditation
	 * @see #accessible()
	 */
	public static Meditation on(Class<?> cls) {
		Object objClass = cls;
		Meditation meditation = new Meditation(objClass, false);
		return meditation;
	}

	/**
	 * Creates a <code>Meditation</code> over the <code>Class</code> identified by given
	 * <code>className</code> parameter.
	 *
	 * <p>
	 * The class is loaded as follow:
	 *
	 * <pre>
	 * Class&lt;?&gt; cls = Class.forName(className);
	 * </pre>
	 *
	 * <p>
	 * The created <code>Meditation</code> has the <code>accessible</code> flag set to
	 * <code>false</code>.
	 *
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * Object s = Meditation
	 * 	.on("java.lang.String")
	 * 	.create("FooBar")
	 * 	.call("substring", 3)
	 * 	.get();
	 *
	 * assert s.equals("Bar") == true;
	 * </pre>
	 *
	 * @param className The class name.
	 *
	 * @return The <code>Meditation</code> over <code>Class</code> object based on
	 *         <code>className</code> parameter.
	 *
	 * @see Meditation
	 * @see #accessible()
	 */
	public static Meditation on(String className) {
		Meditation meditation = on(className, null);
		return meditation;
	}

	/**
	 * Creates a <code>Meditation</code> over the <code>Class</code> identified by given
	 * <code>className</code> parameter.
	 *
	 * <p>
	 * The <code>Class</code> is loaded as follow:
	 *
	 * <pre>
	 * Class&lt;?&gt; cls = classLoader == null
	 * 	? Class.forName(className)
	 * 	: Class.forName(className, true, classLoader);
	 * </pre>
	 *
	 * <p>
	 * The created <code>Meditation</code> has the <code>accessible</code> flag set to
	 * <code>false</code>.
	 *
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * Object s = Meditation
	 * 	.on("java.lang.String", ClassLoader.getSystemClassLoader())
	 * 	.create("FooBar")
	 * 	.call("substring", 3)
	 * 	.get();
	 *
	 * assert s.equals("Bar") == true;
	 * </pre>
	 *
	 * @param className The class name.
	 *
	 * @param classLoader The <code>ClassLoader</code> to load the class specified by
	 *        <code>className</code>; may be <code>null</code> (
	 *        <code>ClassLoader.getSystemClassLoader()</code> will be used instead).
	 *
	 * @return The <code>Meditation</code> over <code>Class</code> object based on
	 *         <code>className</code> parameter.
	 *
	 * @see Meditation
	 * @see #accessible()
	 */
	public static Meditation on(String className, ClassLoader classLoader) {
		Class<?> cls = null;

		try {
			cls = classLoader == null
					? Class.forName(className)
					: Class.forName(className, true, classLoader);

		} catch (ClassNotFoundException e) {
			throw new MeditationException(e);
		}

		Meditation meditation = on(cls);
		return meditation;
	}
}

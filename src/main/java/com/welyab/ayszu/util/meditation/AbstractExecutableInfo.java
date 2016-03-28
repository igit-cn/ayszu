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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * This is a shortcut class that implements <code>ExecutableInfor</code>, delegating all method
 * calls to an underlying executable.
 *
 * @author Welyab Paula
 *
 * @param <T> The implementing type of <code>Executable</code>: <code>Method</code> or
 *        <code>Constructor</code>.
 */
public class AbstractExecutableInfo<T extends Executable> implements ExecutableInfo<T> {

	/**
	 * The underlying executable. All calls to methods implemented from <code>ExecutableInfo</code>
	 * on <i>this</i> instance are delegated to this executable instance.
	 */
	protected final T executable;

	/**
	 * Creates a new <code>AbstractExecutableInfo</code> using given <code>executable</code>.
	 *
	 * @param executable The underlying executable.
	 */
	public AbstractExecutableInfo(T executable) {
		this.executable = executable;
	}

	@Override
	public int getModifiers() {
		return executable.getModifiers();
	}

	@Override
	public String getName() {
		return executable.getName();
	}

	@Override
	public <E extends Annotation> E getAnnotation(Class<E> annotationClass) {
		return executable.getAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return executable.getDeclaredAnnotations();
	}

	@Override
	public Class<?> getDeclaringClass() {
		return executable.getDeclaringClass();
	}

	@Override
	public Class<?>[] getParameterTypes() {
		return executable.getParameterTypes();
	}

	@Override
	public TypeVariable<T>[] getTypeParameters() {
		@SuppressWarnings("unchecked")
		TypeVariable<T>[] typeParameters = (TypeVariable<T>[]) executable.getTypeParameters();
		return typeParameters;
	}

	@Override
	public boolean isSynthetic() {
		return executable.isSynthetic();
	}

	@Override
	public Type[] getGenericParameterTypes() {
		return executable.getGenericParameterTypes();
	}

	@Override
	public Annotation[][] getParameterAnnotations() {
		return executable.getParameterAnnotations();
	}

	@Override
	public int getParameterCount() {
		return executable.getParameterCount();
	}

	@Override
	public boolean isVarArgs() {
		return executable.isVarArgs();
	}

	@Override
	public AnnotatedType getAnnotatedReturnType() {
		return executable.getAnnotatedReturnType();
	}

	@Override
	public Class<?>[] getExceptionTypes() {
		return executable.getExceptionTypes();
	}

	@Override
	public Type[] getGenericExceptionTypes() {
		return executable.getGenericExceptionTypes();
	}

	@Override
	public AnnotatedType getAnnotatedReceiverType() {
		return executable.getAnnotatedReceiverType();
	}

	@Override
	public <E extends Annotation> E[] getAnnotationsByType(Class<E> annotationClass) {
		return executable.getAnnotationsByType(annotationClass);
	}

	@Override
	public AnnotatedType[] getAnnotatedParameterTypes() {
		return executable.getAnnotatedParameterTypes();
	}

	@Override
	public Parameter[] getParameters() {
		return executable.getParameters();
	}

	@Override
	public Annotation[] getAnnotations() {
		return executable.getAnnotations();
	}

	@Override
	public <E extends Annotation> E getDeclaredAnnotation(Class<E> annotationClass) {
		return executable.getAnnotation(annotationClass);
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return executable.isAnnotationPresent(annotationClass);
	}

	@Override
	public boolean isAccessible() {
		return executable.isAccessible();
	}

	@Override
	public <E extends Annotation> E[] getDeclaredAnnotationsByType(Class<E> annotationClass) {
		return executable.getDeclaredAnnotationsByType(annotationClass);
	}
}

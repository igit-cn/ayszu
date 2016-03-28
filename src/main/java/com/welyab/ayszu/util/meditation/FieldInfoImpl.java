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
import java.lang.reflect.Field;

public class FieldInfoImpl implements FieldInfo {

	protected final Field field;

	public FieldInfoImpl(Field field) {
		this.field = field;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return field.getAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getAnnotations() {
		return field.getAnnotations();
	}

	@Override
	public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
		return field.getDeclaredAnnotationsByType(annotationClass);
	}

	@Override
	public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
		return field.getDeclaredAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return field.getDeclaredAnnotations();
	}

	@Override
	public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
		return field.getDeclaredAnnotationsByType(annotationClass);
	}

	@Override
	public Class<?> getDeclaringClass() {
		return field.getDeclaringClass();
	}

	@Override
	public int getModifiers() {
		return field.getModifiers();
	}

	@Override
	public String getName() {
		return field.getName();
	}

	@Override
	public boolean isAccessible() {
		return field.isAccessible();
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return field.isAnnotationPresent(annotationClass);
	}

	@Override
	public boolean isSynthetic() {
		return field.isSynthetic();
	}

	Field getField() {
		return field;
	}

	@Override
	public String toString() {
		return "FieldInfoImpl [field=" + field + "]";
	}

}

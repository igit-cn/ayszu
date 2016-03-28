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

public interface MemberInfo {

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass);

	public Annotation[] getAnnotations();

	public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass);

	public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass);

	public Annotation[] getDeclaredAnnotations();

	public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass);

	public Class<?> getDeclaringClass();

	public int getModifiers();

	public java.lang.String getName();

	public boolean isAccessible();

	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);

	public boolean isSynthetic();
}

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

public interface ExecutableInfo<T extends Executable> extends MemberInfo {

	public AnnotatedType[] getAnnotatedParameterTypes();

	public AnnotatedType getAnnotatedReceiverType();

	public AnnotatedType getAnnotatedReturnType();

	public Class<?>[] getExceptionTypes();

	public Type[] getGenericExceptionTypes();

	public Type[] getGenericParameterTypes();

	public Annotation[][] getParameterAnnotations();

	public int getParameterCount();

	public Class<?>[] getParameterTypes();

	public Parameter[] getParameters();

	public TypeVariable<T>[] getTypeParameters();

	public boolean isVarArgs();
}

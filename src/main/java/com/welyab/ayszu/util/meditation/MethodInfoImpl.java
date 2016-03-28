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
import java.lang.reflect.Type;

/**
 * Default implementation to {@link MethodInfo}.
 *
 * @author Welyab Paula
 *
 * @see MethodInfo
 */
public class MethodInfoImpl extends AbstractExecutableInfo<Method>implements MethodInfo {

	/**
	 * Creates a a new <code>MethodInfoImpl</code> using given <code>method</code>.
	 *
	 * @param method The <code>Method</code>.
	 */
	public MethodInfoImpl(Method method) {
		super(method);
	}

	Method getMethod() {
		return executable;
	}

	@Override
	public Object getDefaultValue() {
		return executable.getDefaultValue();
	}

	@Override
	public Type getGenericReturnType() {
		return executable.getGenericReturnType();
	}

	@Override
	public Class<?> getReturnType() {
		return executable.getReturnType();
	}

	@Override
	public boolean isBridge() {
		return executable.isBridge();
	}

	@Override
	public boolean isDefault() {
		return executable.isDefault();
	}

	@Override
	public String toString() {
		return "[MethodInfo = " + executable.toString() + "]";
	}
}

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodCallerImpl extends AbstractExecutableCaller<Method>implements MethodCaller {

	public MethodCallerImpl(Object object, Method method, boolean accessible) {
		super(object, method, accessible);
	}

	@Override
	public boolean isReturnable() {
		return accessibleObject.getReturnType() != void.class;
	}

	@Override
	protected Object callHelper(Object... arguments) {
		try {
			Object result = accessibleObject.invoke(object, arguments);
			return result;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new MeditationException(e);
		}
	}

	@Override
	protected Object callHelper() {
		return callHelper(new Object[] {});
	}
}

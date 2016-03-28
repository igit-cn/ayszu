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

public abstract class AbstractExecutableCaller<T extends AccessibleObject>
		extends AbstractMemberCaller<T>
		implements ExecutableCaller {

	public AbstractExecutableCaller(Object object, T accessibleObject, boolean accessible) {
		super(object, accessibleObject, accessible);
	}

	@Override
	public Object call(Object... arguments) {
		boolean tempAccessible = accessibleObject.isAccessible();
		setAccessible(accessible);
		try {
			Object result = callHelper(arguments);
			return result;
		} finally {
			setAccessible(tempAccessible);
		}
	}

	@Override
	protected Object callHelper() {
		Object result = callHelper(new Object[] {});
		return result;
	}

	protected abstract Object callHelper(Object... arguments);
}

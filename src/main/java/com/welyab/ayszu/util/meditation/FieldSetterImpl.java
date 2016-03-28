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

import java.lang.reflect.Field;

public class FieldSetterImpl implements FieldSetter {

	private final Object object;

	private final Field field;

	private final boolean accessible;

	public FieldSetterImpl(Object object, Field field, boolean accessible) {
		this.object = object;
		this.field = field;
		this.accessible = accessible;
	}

	@Override
	public void set(Object value) {
		boolean tempAccessible = field.isAccessible();
		setAccessible(accessible);
		try {
			field.set(object, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new MeditationException(e);
		} finally {
			setAccessible(accessible);
		}
	}

	private void setAccessible(boolean accessible) {
		try {
			field.setAccessible(accessible);
		} catch (SecurityException e) {
			throw new MeditationException("Cannot change accessible flag", e);
		}
	}
}

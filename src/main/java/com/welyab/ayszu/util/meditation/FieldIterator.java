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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

public class FieldIterator implements Iterator<Field> {

	private final boolean staticOnly;

	private final Set<String> backup;

	private Field next;

	private Field fields[];

	private int index;

	private Deque<Class<?>> classes;

	public FieldIterator(Class<?> cls, boolean staticOnly) {
		this.staticOnly = staticOnly;
		backup = new HashSet<>();
		index = 0;

		classes = new LinkedList<>();
		classes.add(cls);
	}

	@Override
	public boolean hasNext() {
		prepareNext();
		return next != null;
	}

	@Override
	public Field next() {
		prepareNext();
		if (next == null) {
			throw new NoSuchElementException();
		}
		Field field = next;
		next = null;
		return field;
	}

	private void prepareNext() {
		if (next != null) {
			return;
		}

		while (fields != null && index < fields.length) {
			Field field = fields[index];
			index++;

			boolean isStaticMethod = Modifier.isStatic(field.getModifiers());
			if (staticOnly && !isStaticMethod) {
				continue;
			}

			if (backup.contains(field.getName())) {
				continue;
			}

			backup.add(field.getName());
			next = field;
			return;
		}

		while (next == null && !classes.isEmpty()) {
			Class<?> current = classes.removeFirst();

			if (current.getSuperclass() != null) {
				classes.addLast(current.getSuperclass());
			}
			Arrays.stream(current.getInterfaces()).forEach(classes::addLast);

			index = 0;
			fields = current.getDeclaredFields();

			prepareNext();
		}
	}

	public static FieldIterator create(Class<?> cls, boolean staticOnly) {
		FieldIterator fieldIterator = new FieldIterator(cls, staticOnly);
		return fieldIterator;
	}

	public static FieldIterator create(Class<?> cls) {
		FieldIterator fieldIterator = create(cls, false);
		return fieldIterator;
	}
}

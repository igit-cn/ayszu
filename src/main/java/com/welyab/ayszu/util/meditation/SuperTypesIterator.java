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

import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

public class SuperTypesIterator implements Iterator<Class<?>> {

	private Class<?> next;

	private Deque<Class<?>> classes;

	private Set<Class<?>> backup;

	public SuperTypesIterator(Class<?> cls) {
		classes = new LinkedList<>();

		if (cls.getSuperclass() != null) {
			classes.addLast(cls.getSuperclass());
		}
		Arrays.stream(cls.getInterfaces()).forEach(classes::addLast);

		backup = new HashSet<>();
	}

	@Override
	public boolean hasNext() {
		prepareNext();
		return next != null;
	}

	@Override
	public Class<?> next() {
		prepareNext();

		if (next == null) {
			throw new NoSuchElementException();
		}

		Class<?> clazz = next;
		next = null;

		return clazz;
	}

	private void prepareNext() {
		if (next != null) {
			return;
		}

		if (!classes.isEmpty()) {
			Class<?> current = classes.removeFirst();
			if (current.getSuperclass() != null) {
				classes.addLast(current.getSuperclass());
			}
			Arrays.stream(current.getInterfaces())
					.filter(c -> !backup.contains(c))
					.forEach(classes::addLast);

			backup.add(current);
			next = current;
		}
	}

	public static SuperTypesIterator create(Class<?> cls) {
		SuperTypesIterator iterator = new SuperTypesIterator(cls);
		return iterator;
	}
}

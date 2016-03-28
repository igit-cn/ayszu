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

import java.lang.reflect.Constructor;
import java.util.Iterator;

import com.welyab.ayszu.util.collection.AyszuCollections;

public class ConstructorIterator<T> implements Iterator<Constructor<T>> {

	private final Iterator<Constructor<?>> iterator;

	public ConstructorIterator(Class<T> cls) {
		Constructor<?>[] constructors = cls.getDeclaredConstructors();
		iterator = AyszuCollections.iterator(constructors);
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Constructor<T> next() {
		@SuppressWarnings("unchecked")
		Constructor<T> constructor = (Constructor<T>) iterator.next();
		return constructor;
	}

	public static <E> ConstructorIterator<E> create(Class<E> cls) {
		ConstructorIterator<E> constructorIterator = new ConstructorIterator<>(cls);
		return constructorIterator;
	}
}

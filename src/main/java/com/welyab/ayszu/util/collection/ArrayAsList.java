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

package com.welyab.ayszu.util.collection;

import java.util.AbstractList;

/**
 * A <code>List</code> implementation that uses array as source. This class has a constructor that
 * receives a array and keep a reference to it. Note that modifications on underlying array will be
 * reflect on the state of the list.
 *
 * <p>
 * This implementation of list provides three methods:
 *
 * <pre>
 * get(int index);
 * set(int index, E value);
 * int size();
 * </pre>
 *
 * The method <code>'set(int index, E value)'</code> will change the underlying array source.
 *
 * <p>
 * All other methods are not available.
 *
 * <p>
 * This class is not thread safe.
 *
 * @param <E> The type of elements in the list/array.
 *
 * @author Welyab Paula
 */
public class ArrayAsList<E> extends AbstractList<E> {

	/**
	 * The array refenrence.
	 */
	private final E[] array;

	/**
	 * Creates a new <code>ArrayAsList</code> with given <code>array</code> parameter.
	 *
	 * @param array The underlying array source.
	 */
	public ArrayAsList(E[] array) {
		this.array = array;
	}

	@Override
	public E get(int index) {
		return this.array[index];
	}

	@Override
	public E set(int index, E element) {
		E previous = array[index];
		array[index] = element;
		return previous;
	}

	@Override
	public int size() {
		return this.array.length;
	}
}

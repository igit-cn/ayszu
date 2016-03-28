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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * An extension to <code>Iterator</code>, providing stream capabilities and index information.
 *
 * @author Welyab Paula
 *
 * @param <T> The type of elements returned by this iterator.
 */
public interface AyszuIterator<T> extends Iterator<T> {

	/**
	 * Streams over each remaining element in the <code>Iterator</code>.
	 *
	 * <p>
	 * After call to a final operation on the <code>Stream</code>, a call to method
	 * <code>hasNext</code> of this <code>Iterator</code> returns <code>false</code>.
	 *
	 * @return A <code>Stream</code>.
	 */
	public Stream<T> streamRemaining();

	/**
	 * Streams over each remaining element in the <code>Iterator</code>, encapsulating it as an
	 * <code>IndexedItem</code>.
	 *
	 * <p>
	 * After call to a final operation on the <code>Stream</code>, a call to method
	 * <code>hasNext</code> of this <code>Iterator</code> returns <code>false</code>.
	 *
	 * @return A <code>Stream</code>.
	 */
	public Stream<IndexedItem<T>> indexedStreamRemaining();

	/**
	 * The current element index of this <code>AyszuIterator</code>, starting from zero.
	 *
	 * <p>
	 * After a new <code>AyszuIterator</code> is created, a call to this method returns zero if the
	 * method {@link #hasNext()} is capable to return <code>true</code>. If <code>hasNext</code>
	 * returns <code>false</code>, them a {@link NoSuchElementException} is thrown.
	 *
	 * @return The index into <code>AyszuIterator</code> of current element.
	 *
	 * @throws NoSuchElementException if the iteration has no more elements.
	 */
	public int currentIndex();
}

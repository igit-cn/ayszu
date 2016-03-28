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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class AyszuIteratorImpl<T> implements AyszuIterator<T> {

	private final Iterator<T> iterator;

	private int currentIndex;

	public AyszuIteratorImpl(Iterator<T> iterator) {
		this.iterator = iterator;
		currentIndex = 0;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public T next() {
		return iterator.next();
	}

	@Override
	public int currentIndex() {
		return currentIndex;
	}

	@Override
	public Stream<T> streamRemaining() {
		Stream<T> stream = AyszuCollections.stream(this);
		return stream;
	}

	@Override
	public Stream<IndexedItem<T>> indexedStreamRemaining() {
		int currentIndex = currentIndex();
		AtomicInteger indexSeed = new AtomicInteger(currentIndex);
		Stream<IndexedItem<T>> stream = streamRemaining()
				.map(method -> {
					int index = indexSeed.getAndIncrement();
					IndexedItem<T> indexedItem = new IndexedItem<>(index, method);
					return indexedItem;
				});
		return stream;
	}
}

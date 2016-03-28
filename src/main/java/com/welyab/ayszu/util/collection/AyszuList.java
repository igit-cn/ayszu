package com.welyab.ayszu.util.collection;

import java.util.AbstractList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * An extension to <code>List</code>, adding other capabilities.
 *
 * @author Welyab Paula
 *
 * @param <T> The type of elements in this list
 */
public abstract class AyszuList<T> extends AbstractList<T> {

	public Stream<IndexedItem<T>> indexedStream() {
		return IntStream.range(0, size())
				.mapToObj(index -> new IndexedItem<>(index, get(index)));
	}
}

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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.base.Preconditions;

/**
 * This class contains some helper methods to work with collections.
 *
 * @author Welyab Paula
 */
public class AyszuCollections {

	@SuppressWarnings("javadoc")
	private AyszuCollections() {
		throw new UnsupportedOperationException("No instances of AyszuCollections for you");
	}

	/**
	 * Creates a sequential <code>Stream</code> using the given <code>iterator</code> parameter. The
	 * <code>Iterator</code> elements are consumed just when operations on returned
	 * <code>Stream</code> are performed; and only the remaining elements of the
	 * <code>Iterator</code> will be consumed.
	 *
	 * @param iterator <code>Iterator</code> to servers as source to created <code>Stream</code>.
	 * @param <E> The type of <code>Iterator</code> and <code>Stream</code> elements type.
	 *
	 * @return A <code>Stream</code> that uses remaining items of the <code>iterator</code> as
	 *         source.
	 */
	public static <E> Stream<E> stream(Iterator<E> iterator) {
		Spliterator<E> spliterator = Spliterators
				.spliteratorUnknownSize(
						iterator,
						Spliterator.ORDERED | Spliterator.SORTED);
		Stream<E> stream = StreamSupport.stream(spliterator, false);
		return stream;
	}

	/**
	 * Creates a <code>Stream</code> of <code>IndexedItems</code> using given <code>list</code>
	 * parameter. A <code>IndexedItem</code> is a pair of a element from the list and it respective
	 * index position.
	 *
	 * @param list The list to create the <code>Stream</code>.
	 *
	 * @return THe created <code>Stream</code> of <code>IndexedItems</code>.
	 *
	 * @throws NullPointerException Thrown when <code>list</code> parameter is null.
	 */
	public static <E> Stream<IndexedItem<E>> indexedStream(List<E> list) {
		Preconditions.checkNotNull(list, "list");
		return IntStream.range(0, list.size())
				.mapToObj(index -> new IndexedItem<>(index, list.get(index)));
	}

	/**
	 * Creates a <code>Stream</code> using the given <code>enumeration</code> parameter. The
	 * <code>Enumeration</code> elements are consumed just when operations on returned
	 * <code>Stream</code> are performed; and only the remaining elements of the
	 * <code>Enumeration</code> will be consumed.
	 *
	 * @param enumeration <code>Enumeration</code> to servers as source to created
	 *        <code>Stream</code>.
	 * @param <E> The type of <code>Enumeration</code> and <code>Stream</code> elements.
	 *
	 * @return A <code>Stream</code> that uses remaining items of the <code>enumeration</code> as
	 *         source.
	 */
	public static <E> Stream<E> stream(Enumeration<E> enumeration) {
		Iterator<E> iterator = iterator(enumeration);
		Stream<E> stream = stream(iterator);
		return stream;
	}

	/**
	 * Creates a immutable list using the given <code>array</code> as source. Changes on array
	 * elements will be reflected on future uses of created list.
	 *
	 * <p>
	 * The follow code snippet:
	 *
	 * <pre>
	 * String array[] = {"Foo", "Bar"};
	 * List<String> list = list(array);
	 * System.out.println(list.get(0));
	 * array[0] = "Lol";
	 * System.out.println(list.get(0));
	 * </pre>
	 *
	 * ... will print:
	 *
	 * <pre>
	 * Foo
	 * Lol
	 * </pre>
	 *
	 * @param array The source array.
	 * @param <E> The type of array and <code>List</code> content type.
	 *
	 * @return The list.
	 */
	public static <E> List<E> list(E[] array) {
		ArrayAsList<E> list = new ArrayAsList<E>(array);
		return list;
	}

	/**
	 * Creates a <code>List</code> instance using given <code>iterator</code> as source. The
	 * iterator is fully consumed, by retrieving its all remaining items.
	 *
	 * @param iterator The iterator item source to returned list.
	 * @param <E> The type of <code>Iterator</code> and <code>List</code> content type.
	 *
	 * @return A <code>List</code> instance.
	 */
	public static <E> List<E> list(Iterator<E> iterator) {
		Stream<E> stream = stream(iterator);
		List<E> list = stream.collect(Collectors.toList());
		return list;
	}

	/**
	 * Creates a <code>Iterator</code> using given <code>enumeration</code>. The returned
	 * <code>Iterator</code> has a lazy behavior because consumes the <code>Enumeration</code> only
	 * when requested. Note that the <code>Iterator</code> has the same state of the
	 * <code>Enumeration</code>, i.e, previously consumed elements of the <code>Enumeration</code>
	 * will not be available on the <code>Iterator</code>.
	 *
	 * @param enumeration The <code>Enumeration</code> to server the created <code>Iterator</code>.
	 * @param <E> The type of <code>Enumeration</code> and <code>Iterator</code> content type.
	 *
	 * @return An <code>Iterator</code>.
	 */
	public static <E> Iterator<E> iterator(Enumeration<E> enumeration) {
		Iterator<E> iterator = new Iterator<E>() {

			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public E next() {
				return enumeration.nextElement();
			}
		};
		return iterator;
	}

	public static <E> Iterator<E> iterator(E[] array) {
		List<E> list = list(array);
		Iterator<E> iterator = list.iterator();
		return iterator;
	}

	public static <E> AyszuIterator<E> ayszuIterator(Iterator<E> iterator) {
		AyszuIterator<E> ayszuIterator = new AyszuIteratorImpl<>(iterator);
		return ayszuIterator;
	}

	public static <E> Enumeration<E> enumeration(Iterator<E> iterator) {
		return new Enumeration<E>() {

			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public E nextElement() {
				return iterator.next();
			}
		};
	}

	/**
	 * Creates a <code>List</code> instance using given <code>enumeration</code> as source. The
	 * <code>Enumeration</code> is fully consumed, by retrieving its all remaining elements.
	 *
	 * @param enumeration The enumeration item source to returned list.
	 * @param <E> The type of <code>Enumeration</code> and <code>List</code> content type.
	 *
	 * @return A <code>List</code> instance.
	 */
	public static <E> List<E> list(Enumeration<E> enumeration) {
		Iterator<E> iterator = iterator(enumeration);
		List<E> list = list(iterator);
		return list;
	}
}

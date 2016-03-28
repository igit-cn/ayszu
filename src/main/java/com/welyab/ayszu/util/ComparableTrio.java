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

package com.welyab.ayszu.util;

import com.google.common.collect.ComparisonChain;

/**
 * A comparable version of class {@link Trio}.
 *
 * @author Welyab Paula
 *
 * @param <F> The type of first element of trio.
 * @param <S> The type of second element of trio.
 * @param <T> The type of trhird element of trio.
 *
 * @see Pair
 * @see Trio
 */
public class ComparableTrio<F extends Comparable<F>, S extends Comparable<S>, T extends Comparable<T>>
		extends Trio<F, S, T>
		implements Comparable<ComparableTrio<F, S, T>> {

	/**
	 * Creates a new <code>ComparableTrio</code> with all elements set to <code>null</code>.
	 */
	public ComparableTrio() {
	}

	/**
	 * Creates a new <code>ComparableTrio</code> using respective arguments.
	 *
	 * @param first The value of the first element
	 * @param second The value of the second element
	 * @param third The value of the third element
	 */
	public ComparableTrio(F first, S second, T third) {
		super(first, second, third);
	}

	/**
	 * Sequentially compares the items from this <code>ComparableTrio</code>, beginning on the first
	 * element, after on the second, and finally in the third.
	 */
	@Override
	public int compareTo(ComparableTrio<F, S, T> o) {
		return ComparisonChain.start()
				.compare(getFirst(), o.getFirst())
				.compare(getSecond(), o.getSecond())
				.compare(getThird(), o.getThird())
				.result();
	}
}

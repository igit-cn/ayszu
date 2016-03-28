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
 * A comparable version of {@link Pair} container. A comparison is performed
 * sequentially in first, and after on second element, as follow:
 *
 * <pre>
 * // consider comparison of two null values as 0 (zero)
 * // and consider null less than a non null value
 * return first.compareTo(first) == 0
 * 		? second.compareTo(second)
 * 		: first.compareTo(first);
 * </pre>
 *
 * @author Welyab Paula
 *
 * @param <F> The type of first element of this pair.
 * @param <S> The type of second element of this pair.
 *
 * @see Pair
 * @see Trio
 */
public class ComparablePair<F extends Comparable<F>, S extends Comparable<S>>
		extends Pair<F, S>
		implements Comparable<ComparablePair<F, S>> {

	/**
	 * Creates a <code>ComparablePair</code> with <code>first</code> and <code>second</code> fields
	 * set to <code>null</code>.
	 */
	public ComparablePair() {
	}

	/**
	 * Creates a <code>ComparablePair</code> using given <code>first</code> and <code>second</code>
	 * values.
	 *
	 * @param first The first value of the <code>ComparablePair</code>.
	 * @param second The second value of the <code>ComparablePair</code>.
	 */
	public ComparablePair(F first, S second) {
		super(first, second);
	}

	@Override
	public int compareTo(ComparablePair<F, S> o) {
		return ComparisonChain.start()
				.compare(getFirst(), o.getFirst())
				.compare(getSecond(), o.getSecond())
				.result();
	}
}

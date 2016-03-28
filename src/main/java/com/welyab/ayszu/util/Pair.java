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

/**
 * A <code>Pair</code> is a generic container to two elements. It is useful in some operation when
 * working with Java 8 Lambdas.
 *
 * <p>
 * A <code>Pair</code> is not thread safe, use <code>SynchronizedSingle</code> if synchronization is
 * need.
 *
 * @author Welyab Paula
 *
 * @param <F> The type of first element.
 * @param <S> The type of second element.
 *
 * @see Single
 * @see ComparableSingle
 * @see ComparablePair
 * @see Trio
 * @see ComparableTrio
 */
public class Pair<F, S> {

	/**
	 * The first value of this <code>Pair</code>.
	 */
	private F first;

	/**
	 * The second value of this <code>Pair</code>.
	 */
	private S second;

	/**
	 * Creates a <code>Pair</code> with <code>first</code> and <code>second</code> fields set to
	 * <code>null</code>.
	 */
	public Pair() {
	}

	/**
	 * Creates a <code>Pair</code> using given <code>first</code> and <code>second</code> values.
	 *
	 * @param first The first value of the <code>Pair</code>.
	 * @param second The second value of the <code>Pair</code>.
	 */
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Returns the first value of this <code>Pair</code>.
	 *
	 * @return The first value of this <code>Pair</code>.
	 */
	public F getFirst() {
		return first;
	}

	/**
	 * Sets the first value of this <code>Pair</code>.
	 *
	 * @param first The new value.
	 */
	public void setFirst(F first) {
		this.first = first;
	}

	/**
	 * Returns the second value of this <code>Pair</code>.
	 *
	 * @return The second value of this <code>Pair</code>.
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * Sets the second value of this <code>Pair</code>.
	 *
	 * @param second The new value.
	 */
	public void setSecond(S second) {
		this.second = second;
	}

	/**
	 * Returns the hash based on <code>first</code> and <code>second</code> fields.
	 *
	 * @return The hash.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (first == null ? 0 : first.hashCode());
		result = prime * result + (second == null ? 0 : second.hashCode());
		return result;
	}

	/**
	 * Check whether this <code>Pair</code> is equals to other <code>Pair</code>.
	 *
	 * <p>
	 * Two <code>Pairs</code> are equals if, and only if:
	 *
	 * <pre>
	 * this.first.equals(other.first) && this.second(other.second)
	 * </pre>
	 *
	 * @return <code>true</code> if this object is the same as the <code>obj</code> argument;
	 *         <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		if (first == null) {
			if (other.first != null) {
				return false;
			}
		} else if (!first.equals(other.first)) {
			return false;
		}
		if (second == null) {
			if (other.second != null) {
				return false;
			}
		} else if (!second.equals(other.second)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a <code>String</code> at format:
	 *
	 * <pre>
	 * "Pair(first=value1, second=value2)"
	 * </pre>
	 *
	 * @return The <code>String</code>.
	 */
	@Override
	public String toString() {
		return String.format("Pair(first=%s, second=%s)", first, second);
	}
}

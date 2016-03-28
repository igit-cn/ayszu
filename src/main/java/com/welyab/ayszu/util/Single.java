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
 * A <code>Single</code> is a container to single element. Useful in some situations where is need
 * to update value outside from anonymous class declarations.
 *
 * <p>
 * A <code>Single</code> is not thread safe, use {@link SynchronizedSingle} if synchronization is
 * need.
 *
 * @author Welyab Paula
 *
 * @param <T> The type of element.
 *
 * @see Pair
 * @see Trio
 * @see SynchronizedSingle
 */
public class Single<T> {

	/**
	 * The item.
	 */
	private T item;

	/**
	 * Creates a <code>Single</code> with <code>null</code> element.
	 */
	public Single() {
	}

	/**
	 * Creates a <code>Single</code> with given <code>item</code>.
	 *
	 * @param item The item.
	 */
	public Single(T item) {
		this.item = item;
	}

	/**
	 * Returns the contained item.
	 *
	 * @return The item.
	 */
	public T getItem() {
		return item;
	}

	/**
	 * Set the item of this container with the value of <code>item</code>.
	 *
	 * @param item The item.
	 */
	public void setItem(T item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (item == null ? 0 : item.hashCode());
		return result;
	}

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
		Single<?> other = (Single<?>) obj;
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		} else if (!item.equals(other.item)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Single [item=" + item + "]";
	}
}

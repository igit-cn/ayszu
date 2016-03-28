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

public class IndexedItem<T> {

	private final int index;

	private final T item;

	public IndexedItem(int index, T item) {
		this.index = index;
		this.item = item;
	}

	public int getIndex() {
		return index;
	}

	public T getItem() {
		return item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		@SuppressWarnings("rawtypes")
		IndexedItem other = (IndexedItem) obj;
		if (index != other.index) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"%s[index=%i, item=%s]",
				IndexedItem.class.getSimpleName(),
				index,
				item);
	}
}

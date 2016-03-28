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
 * A synchronized version of <code>Single</code> class. Each <code>set</code> and <code>get</code>
 * method is synchronized on on <code>this</code> instance.
 *
 * @author Welyba paula
 *
 * @param <T>
 */
public class SynchronizedSingle<T> extends Single<T> {

	/**
	 * Creates a new <code>SynchronizedSingle</code>.
	 */
	public SynchronizedSingle() {
	}

	/**
	 * Creates a new <code>SynchronizedSingle</code>.
	 *
	 * @param item The initial item value to this single.
	 */
	public SynchronizedSingle(T item) {
		super(item);
	}

	@Override
	public synchronized T getItem() {
		return super.getItem();
	}

	@Override
	public synchronized void setItem(T item) {
		super.setItem(item);
	}
}

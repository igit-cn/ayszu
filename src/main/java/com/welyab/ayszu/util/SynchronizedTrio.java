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

public class SynchronizedTrio<F, S, T> extends Trio<F, S, T> {

	public SynchronizedTrio() {
	}

	public SynchronizedTrio(F frist, S second, T third) {
		super(frist, second, third);
	}

	@Override
	public F getFirst() {
		return super.getFirst();
	}

	@Override
	public void setFirst(F frist) {
		super.setFirst(frist);
	}

	@Override
	public S getSecond() {
		return super.getSecond();
	}

	@Override
	public void setSecond(S second) {
		super.setSecond(second);
	}

	@Override
	public T getThird() {
		return super.getThird();
	}

	@Override
	public void setThird(T third) {
		super.setThird(third);
	}
}

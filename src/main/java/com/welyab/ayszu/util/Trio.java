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

public class Trio<F, S, T> {

	private F first;
	private S second;
	private T third;

	public Trio() {
	}

	public Trio(F frist, S second, T third) {
		super();
		this.first = frist;
		this.second = second;
		this.third = third;
	}

	public F getFirst() {
		return first;
	}

	public void setFirst(F frist) {
		this.first = frist;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	public T getThird() {
		return third;
	}

	public void setThird(T third) {
		this.third = third;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (first == null ? 0 : first.hashCode());
		result = prime * result + (second == null ? 0 : second.hashCode());
		result = prime * result + (third == null ? 0 : third.hashCode());
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
		Trio other = (Trio) obj;
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
		if (third == null) {
			if (other.third != null) {
				return false;
			}
		} else if (!third.equals(other.third)) {
			return false;
		}
		return true;
	}
}

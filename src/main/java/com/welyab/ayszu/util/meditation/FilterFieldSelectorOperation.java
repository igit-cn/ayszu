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

package com.welyab.ayszu.util.meditation;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FilterFieldSelectorOperation implements FieldSelectorOperation {

	private final Predicate<FieldInfo> predicate;

	public FilterFieldSelectorOperation(Predicate<FieldInfo> predicate) {
		this.predicate = predicate;
	}

	@Override
	public <E extends FieldInfo> Stream<E> apply(Stream<E> stream) {
		return stream.filter(info -> predicate.test(info));
	}
}

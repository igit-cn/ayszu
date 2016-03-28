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

import java.util.List;
import java.util.stream.Stream;

public class UseAnyMethodSelectorOperation implements MethodSelectorOperation {

	@Override
	public <E extends MethodInfo> Stream<E> apply(Stream<E> stream, List<Class<?>> argumentTypes) {
		return stream.findAny()
				.map(e -> Stream.of(e))
				.orElse(Stream.empty());
	}
}
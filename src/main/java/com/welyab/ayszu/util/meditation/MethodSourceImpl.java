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

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.welyab.ayszu.util.collection.AyszuCollections;

public class MethodSourceImpl implements MethodSource {

	private final Class<?> type;

	private final List<MethodSelectorOperation> operations;

	public MethodSourceImpl(Class<?> type, List<MethodSelectorOperation> operations) {
		this.type = type;
		this.operations = operations;
	}

	@Override
	public Method usingArguments(Class<?>[] argumentTypes) {
		List<Class<?>> types = AyszuCollections.list(argumentTypes);
		boolean staticOnly = Class.class.getName().equals(type.getName());

		MethodIterator methodIterator = MethodIterator.create(type, staticOnly);

		Stream<MethodInfoImpl> stream = AyszuCollections.stream(methodIterator)
				.map(method -> new MethodInfoImpl(method));

		for (int i = 0; i < operations.size(); i++) {
			MethodSelectorOperation operation = operations.get(i);
			stream = operation.apply(stream, types);
		}

		List<MethodInfoImpl> list = stream.collect(Collectors.toList());
		checkResult(list);

		MethodInfoImpl methodInfoImpl = list.get(0);
		return methodInfoImpl.getMethod();
	}

	private static void checkResult(List<MethodInfoImpl> list) {
		if (list.isEmpty()) {
			throw new MeditationException("no method match");
		}

		if (list.size() > 1) {
			throw new MeditationException("many methods match");
		}
	}
}

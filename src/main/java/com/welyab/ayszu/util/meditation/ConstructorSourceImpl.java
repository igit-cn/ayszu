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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ComparisonChain;
import com.welyab.ayszu.util.collection.AyszuCollections;

public class ConstructorSourceImpl implements ConstructorSource {

	private final Class<?> type;

	private final List<ConstructorSelectorOperation> operations;

	public ConstructorSourceImpl(Class<?> type, List<ConstructorSelectorOperation> operations) {
		this.type = type;
		this.operations = operations;
	}

	@Override
	public Constructor<?> usingArguments(Class<?>[] argumentTypes) {
		List<Class<?>> list = AyszuCollections.list(argumentTypes);

		ConstructorIterator<?> constructorIterator = new ConstructorIterator<>(type);
		Stream<ConstructorInfoImpl> stream = AyszuCollections.stream(constructorIterator)
				.map(c -> new ConstructorInfoImpl(c));

		for (int i = 0; i < operations.size(); i++) {
			ConstructorSelectorOperation operation = operations.get(i);
			stream = operation.apply(stream, list);
		}

		List<ConstructorInfoImpl> selecteds = stream.collect(Collectors.toList());

		if (selecteds.isEmpty()) {
			throw new MeditationException("no constructor found");
		}

		if (selecteds.size() > 1) {
			throw new MeditationException("may constructors found");
		}

		return selecteds.get(0).getConstructor();
	}

	private static class MethodComparator implements Comparator<Method> {

		@Override
		public int compare(Method m1, Method m2) {
			ComparisonChain chain = ComparisonChain.start();

			chain = chain.compare(m1.getName(), m2.getName())
					.compare(m1.getParameterCount(), m2.getParameterCount());

			for (int i = 0; i < Math.min(m1.getParameterCount(), m2.getParameterCount()); i++) {
				chain = chain.compare(
						m1.getParameterTypes()[i].getName(),
						m2.getParameterTypes()[i].getName());
			}

			return chain.result();
		}
	}
}

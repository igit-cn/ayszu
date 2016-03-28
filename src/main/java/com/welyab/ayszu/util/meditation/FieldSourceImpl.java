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

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.welyab.ayszu.util.collection.AyszuCollections;

public class FieldSourceImpl implements FieldSource {

	private final Class<?> cls;

	private final List<FieldSelectorOperation> operations;

	public FieldSourceImpl(Class<?> cls, List<FieldSelectorOperation> operations) {
		this.cls = cls;
		this.operations = operations;
	}

	@Override
	public Field get() {
		FieldIterator fieldIterator = FieldIterator.create(cls);
		Stream<FieldInfoImpl> stream = AyszuCollections.stream(fieldIterator).map(f -> new FieldInfoImpl(f));
		for (int i = 0; i < operations.size(); i++) {
			FieldSelectorOperation operation = operations.get(i);
			stream = operation.apply(stream);
		}

		List<FieldInfoImpl> list = stream.collect(Collectors.toList());

		System.out.println(list);

		if (list.isEmpty()) {
			throw new MeditationException("no field found");
		}
		if (list.size() > 1) {
			throw new MeditationException("many fields found");
		}

		return list.get(0).getField();
	}
}

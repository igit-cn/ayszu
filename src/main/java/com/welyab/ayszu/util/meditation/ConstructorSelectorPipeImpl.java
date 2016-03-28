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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ConstructorSelectorPipeImpl implements ConstructorSelectorPipe {

	private final ArrayList<ConstructorSelectorOperation> operations;

	public ConstructorSelectorPipeImpl() {
		operations = new ArrayList<>();
	}

	@Override
	public ExecutableSelectorPipe filter(BiPredicate<ConstructorInfo, List<Class<?>>> predicate) {
		ConstructorSelectorOperation operation = new FilterConstructorSelectorOperationBiPredicateImpl(predicate);
		operations.add(operation);
		return this;
	}

	@Override
	public ConstructorSelectorPipe filter(Predicate<ConstructorInfo> predicate) {
		ConstructorSelectorOperation operation = new FilterConstructorSelectorOperationImpl(predicate);
		operations.add(operation);
		return this;
	}

	@Override
	public ConstructorSelectorPipe compatible() {
		ConstructorSelectorOperation operation = new CompatibleConstructorSelectorOperationImpl();
		operations.add(operation);
		return this;
	}

	@Override
	public ConstructorSelectorPipe mostSpecific() {
		ConstructorSelectorOperation operation = new MostSpecificConstructorSelectorOperationImpl();
		operations.add(operation);
		return this;
	}

	@Override
	public ConstructorSelectorPipe useAny() {
		ConstructorSelectorOperation operation = new UseAnyConstructorSelectorOperationImpl();
		operations.add(operation);
		return this;
	}

	@Override
	public ConstructorSelector selector() {
		List<ConstructorSelectorOperation> unmodifiableList = Collections.unmodifiableList(operations);
		ConstructorSelector constructorSelector = new ConstructorSelectorImpl(unmodifiableList);
		return constructorSelector;
	}
}

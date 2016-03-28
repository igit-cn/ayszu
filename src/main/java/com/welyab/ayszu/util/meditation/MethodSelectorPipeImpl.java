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

public class MethodSelectorPipeImpl implements MethodSelectorPipe {

	private final List<MethodSelectorOperation> operations;

	public MethodSelectorPipeImpl() {
		operations = new ArrayList<>();
	}

	@Override
	public ExecutableSelectorPipe filter(BiPredicate<MethodInfo, List<Class<?>>> predicate) {
		MethodSelectorOperation filterOperation = new FilterMethodSelectorOperationBiPredicate(predicate);
		operations.add(filterOperation);
		return this;
	}

	@Override
	public MethodSelectorPipe filter(Predicate<MethodInfo> predicate) {
		MethodSelectorOperation filterOperation = new FilterMethodSelectorOperation(predicate);
		operations.add(filterOperation);
		return this;
	}

	@Override
	public MethodSelectorPipe mostSpecific() {
		MethodSelectorOperation operation = new MostSpecificMethodSelectorOperation();
		operations.add(operation);
		return this;
	}

	@Override
	public MethodSelectorPipe useAny() {
		MethodSelectorOperation operation = new UseAnyMethodSelectorOperation();
		operations.add(operation);
		return this;
	}

	@Override
	public MethodSelectorPipe compatible() {
		MethodSelectorOperation operation = new CompatibleMethodSelectorOperation();
		operations.add(operation);
		return this;
	}

	@Override
	public MethodSelector selector() {
		List<MethodSelectorOperation> unmodifiableList = Collections.unmodifiableList(operations);
		MethodSelector methodSelector = new MethodSelectorImpl(unmodifiableList);
		return methodSelector;
	}
}

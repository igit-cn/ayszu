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
import java.util.List;
import java.util.function.Predicate;

public class FieldSelectorPipeImpl implements FieldSelectorPipe {

	private final List<FieldSelectorOperation> operations;

	public FieldSelectorPipeImpl() {
		operations = new ArrayList<>();
	}

	@Override
	public FieldSelectorPipe filter(Predicate<FieldInfo> predicate) {
		FieldSelectorOperation operation = new FilterFieldSelectorOperation(predicate);
		operations.add(operation);
		return this;
	}

	@Override
	public FieldSelector selector() {
		return new FieldSelectorImpl(operations);
	}
}
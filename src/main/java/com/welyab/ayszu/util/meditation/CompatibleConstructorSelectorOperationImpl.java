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

public class CompatibleConstructorSelectorOperationImpl implements ConstructorSelectorOperation {

	@Override
	public <E extends ConstructorInfo> Stream<E> apply(Stream<E> stream, List<Class<?>> argumentTypes) {
		return stream.filter(info -> {
			int methodWeight = MeditationUtils.methodWeight(info, argumentTypes);
			return methodWeight >= 0;
		});
	}
}

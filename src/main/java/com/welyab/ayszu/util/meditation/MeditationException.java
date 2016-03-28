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

import com.welyab.ayszu.AyszuException;

/**
 * <code>MeditationException</code> is the root exception of all exceptions thrown by operations
 * into <code>Meditation</code>. Any exception thrown by underlying operations are wrapped by
 * <code>MeditationException</code>.
 *
 * @author Welyab Paula
 */
public class MeditationException extends AyszuException {

	@SuppressWarnings("javadoc")
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new <code>MeditationException</code> without information.
	 */
	public MeditationException() {
	}

	/**
	 * Creates a new <code>MeditationException</code> using given <code>message</code> and
	 * <code>cause</code> parameters.
	 *
	 * @param message Detail message about the <code>MeditationException</code>.
	 *
	 * @param cause The <code>cause</code> of this <code>MeditationException</code>.
	 */
	public MeditationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new <code>MeditationException</code> using given <code>message</code> parameter.
	 *
	 * @param message Detail message about the <code>MeditationException</code>.
	 */
	public MeditationException(String message) {
		super(message);
	}

	/**
	 * Creates a new <code>MeditationException</code> using the give
	 * <code>MeditationException</code>.
	 *
	 * @param cause The <code>cause</code> of this <code>MeditationException</code>.
	 */
	public MeditationException(Throwable cause) {
		super(cause);
	}
}

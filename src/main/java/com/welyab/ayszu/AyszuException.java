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

package com.welyab.ayszu;

/**
 * The <code>AyszuException</code> indicates any problem in <strong>Ayszu</strong> system.
 *
 * @author Welyab Paula
 */
public class AyszuException extends RuntimeException {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new <code>Ayszu</code> exception with {@code null} as its detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}
	 */
	public AyszuException() {
	}

	/**
	 * Constructs a new <code>Ayszu</code> exception with the specified detail message. The cause is
	 * not initialized, and may subsequently be initialized by a call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for later retrieval by the
	 *        {@link #getMessage()} method.
	 */
	public AyszuException(String message) {
		super(message);
	}

	/**
	 * Constructs a new <code>Ayszu</code> exception with the specified cause and a detail message
	 * of <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and
	 * detail message of <tt>cause</tt>). This constructor is useful for runtime exceptions that are
	 * little more than wrappers for other throwables.
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *        {@link #getCause()} method). (A <tt>null</tt> value is
	 *        permitted, and indicates that the cause is nonexistent or
	 *        unknown.)
	 * @since 1.4
	 */
	public AyszuException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message and cause.
	 *
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i> automatically
	 * incorporated in this <code>Ayszu</code> exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the
	 *        {@link #getMessage()} method).
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()}
	 *        method). (A <tt>null</tt> value is permitted, and indicates that the cause is
	 *        nonexistent or unknown.)
	 */
	public AyszuException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new <code>Ayszu</code> exception with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or disabled.
	 *
	 * @param message the detail message.
	 * @param cause the cause. (A {@code null} value is permitted, and indicates that the cause is
	 *        nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public AyszuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

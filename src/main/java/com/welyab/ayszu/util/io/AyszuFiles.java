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

package com.welyab.ayszu.util.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.stream.Stream;

/**
 * Some methods from {@link Files} class, with checked <code>IOException</code> wrapped with
 * <code>UncheckedIOException</code>.
 *
 * @author Welyab Paula
 */
public class AyszuFiles {

	/**
	 * Calls {@link Files#list(Path)} wrap any thrown <code>IOException</code> into a
	 * <code>UncheckEIOException</code>.
	 *
	 * @param directory See the target method to to know more.
	 * @return See the target method to to know more.
	 */
	public static Stream<Path> list(Path directory) {
		try {
			return Files.list(directory);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Calls {@link Files#lines(Path)} wrap any thrown <code>IOException</code> into a
	 * <code>UncheckedIOException</code>.
	 *
	 * @param file See the target method to to know more.
	 * @return See the target method to to know more.
	 */
	public static Stream<String> lines(Path file) {
		try {
			return Files.lines(file);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Calls {@link Files#lines(Path, Charset)} wrap any thrown <code>IOException</code> into a
	 * <code>UncheckedIOException</code>.
	 *
	 * @param file See the target method to to know more.
	 * @param charset See the target method to to know more.
	 * @return See the target method to to know more.
	 */
	public static Stream<String> lines(Path file, Charset charset) {
		try {
			return Files.lines(file, charset);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Calls {@link Files#walk(Path, java.nio.file.FileVisitOption...)} wrap any thrown
	 * <code>IOException</code> into a <code>UncheckedIOException</code>.
	 *
	 * @param path See the target method to know more.
	 * @param options See the target method to know more.
	 * @return See the target method to to know more.
	 */
	public static Stream<Path> walk(Path path, FileVisitOption... options) {
		try {
			return Files.walk(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Calls {@link Files#createDirectories(Path, FileAttribute...)} wrap any thrown
	 * <code>IoException</code> into a <code>UncheckedIOException</code>.
	 *
	 * @param dir See the target method to know more.
	 * @param attrs See the target method to know more.
	 */
	public static void createDirectories(Path dir, FileAttribute<?>... attrs) {
		try {
			Files.createDirectories(dir, attrs);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static BufferedWriter newBufferedWriter(Path path, OpenOption... openOptions) {
		try {
			BufferedWriter bufferedWriter = Files.newBufferedWriter(path, openOptions);
			return bufferedWriter;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static BufferedWriter newBufferedWriter(Path path, Charset cs, OpenOption... openOptions) {
		try {
			BufferedWriter bufferedWriter = Files.newBufferedWriter(path, cs, openOptions);
			return bufferedWriter;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}

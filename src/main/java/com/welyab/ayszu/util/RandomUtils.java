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

package com.welyab.ayszu.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Preconditions;

/**
 * This class contains a set of utility methos to generate 'random' numbers.
 *
 * @author Welyab Paula
 *
 * @see Random
 * @see ThreadLocalRandom
 */
public class RandomUtils {

	/**
	 * Private just to no instance.
	 */
	private RandomUtils() {
	}

	/**
	 * Returns the thread local pseudorandom numbers generators.
	 *
	 * @return the thread local pseudorandom numbers generators.
	 */
	private static Random randomGen() {
		return ThreadLocalRandom.current();
	}

	/**
	 * See {@link Random#nextBytes(byte[])}.
	 *
	 * @param bytes See {@link Random#nextBytes(byte[])}.
	 */
	public static void nextBytes(byte[] bytes) {
		randomGen().nextBytes(bytes);
	}

	/**
	 * See {@link Random#nextInt()}.
	 *
	 * @return See {@link Random#nextInt()}.
	 */
	public static int nextInt() {
		return randomGen().nextInt();
	}

	/**
	 * Pseudo randomically generates a number, between given minimum value
	 * (inclusive) and maximum value (exclusive).
	 *
	 * @param minValueInclusive The minimum value (inclusive) to generates the
	 *        number.
	 * @param maxValueExclusive The maximum value (exclusive) to generates the
	 *        number.
	 *
	 * @return A pseudo randomically generated number, between given min value
	 *         (inclusive) and max value (exclusive).
	 */
	public static int nextInt(int minValueInclusive, int maxValueExclusive) {
		Preconditions.checkArgument(
				minValueInclusive >= 0,
				"minValueInclusive can't be negative");
		Preconditions.checkArgument(
				maxValueExclusive > 0,
				"maxValueExclusive must be greater than zero");
		Preconditions.checkArgument(
				maxValueExclusive - minValueInclusive > 0,
				"maxValueExclusive mat be greater than minValueInclusive");

		return minValueInclusive + nextInt(maxValueExclusive - minValueInclusive);
	}

	/**
	 * See {@link Random#nextInt(int)}.
	 */
	@SuppressWarnings("javadoc")
	public static int nextInt(int maxValueExclusive) {
		return randomGen().nextInt(maxValueExclusive);
	}

	/**
	 * See {@link Random#nextLong()}
	 */
	@SuppressWarnings("javadoc")
	public static long nextLong() {
		return randomGen().nextLong();
	}

	/**
	 * See {@link Random#nextBoolean()}
	 */
	@SuppressWarnings("javadoc")
	public static boolean nextBoolean() {
		return randomGen().nextBoolean();
	}

	/**
	 * See {@link Random#nextFloat()}
	 */
	@SuppressWarnings("javadoc")
	public static float nextFloat() {
		return randomGen().nextFloat();
	}

	/**
	 * See {@link Random#nextDouble()}
	 */
	@SuppressWarnings("javadoc")
	public static double nextDouble() {
		return randomGen().nextDouble();
	}

	/**
	 * Returns a pseudorandom, uniformly distributed <code>char</code> value. For each combination
	 * of parameters, the frequency of each <code>char</code> is the same.
	 *
	 * @param letterCase The case of letters to be considered.
	 * @param includeDigits Whether digits must be included.
	 *
	 * @return The <code>char</code>.
	 */
	public static char nextCharacter(LetterCase letterCase,
			boolean includeDigits) {
		int digitWeight = includeDigits ? 10 : 0;
		int lowerWeight = letterCase == LetterCase.LOWER || letterCase == LetterCase.MIXED ? 26 : 0;
		int upperWeight = letterCase == LetterCase.UPPER || letterCase == LetterCase.MIXED ? 26 : 0;
		int rnd = randomGen().nextInt(digitWeight + lowerWeight + upperWeight);
		int c = '\0';
		if (rnd < digitWeight && digitWeight != 0) {
			c = '0' + randomGen().nextInt(10);
		} else if (rnd < digitWeight + lowerWeight && lowerWeight != 0) {
			c = 'a' + randomGen().nextInt(26);
		} // else if (rnd < digitWeight + lowerWeight + upperWeight && upperWeight != 0) {
		else {
			c = 'A' + randomGen().nextInt(26);
		}
		return (char) c;
	}

	/**
	 * Create a random <code>String</code> with given <code>length</code>. The string is
	 * composed only by upper case letters between <code>'A'</code> and <code>'Z'</code>.
	 *
	 * @param length The length of generated string.
	 *
	 * @return A random string, with only upper case letters..
	 */
	public static String nextRandomUpperString(int length) {
		return nextRandomUpperString(length, false);
	}

	/**
	 * Generates a random string with upper case letters between <code>'A'</code> and
	 * <code>'Z'</code> and, optionally, may include digits
	 * between <code>'0'</code> and <code>'9'</code>.<br>
	 *
	 * If the {@code includeDigit} param is <code>true</code>, then the
	 * occurrence rate of each character is 1/36 (26 letters plus 10
	 * digits).<br>
	 * If the {@code includeDigit} param is <code>false</code>, then the
	 * occurrence rate of each letter is 1/26 (26 letters).
	 *
	 * @param length The <code>length</code> of generated strings.
	 *
	 * @param includeDigits If <code>true</code>, the string can include digits
	 *        (note, this is a random process), otherwise, it will not include digits.
	 *
	 * @return A random string, with given <code>length</code>.
	 */
	public static String nextRandomUpperString(int length,
			boolean includeDigits) {
		return nextRandomString(length, LetterCase.UPPER, includeDigits);
	}

	/**
	 * @param length The generated string length.
	 * @param characterCase Indicates if letter case is upper case, lower case,
	 *        or both.
	 * @param includeDigits Indicates if digit from 0 up to 9 may be included.
	 *
	 * @return A random string generated as specified.
	 */
	public static String nextRandomString(int length, LetterCase characterCase,
			boolean includeDigits) {
		char str[] = new char[length];
		for (int i = 0; i < length; i++) {
			char c = nextCharacter(characterCase, includeDigits);
			str[i] = c;
		}
		return new String(str);
	}

	/**
	 * Creates a string with given <code>length</code> composed only by lower
	 * case characters between <code>'a'</code> and <code>'z'</code>.
	 *
	 * @param length The length of generated string.
	 *
	 * @return A string with given <code>length</code>, whose characters was
	 *         rrandomly generated.
	 */
	public static String nextRandomString(int length) {
		return nextRandomString(length, RandomUtils.LetterCase.LOWER, false);
	}

	/**
	 * Generates a random string using the given characters set in <code>cs</code> param. <br>
	 * The probability of occurrence of each character are the same.
	 *
	 * @param length The length of generated string.
	 *
	 * @param cs The set of characters to be used to generate string.
	 *
	 * @return The generated random string; it may be empty string if <code>cs</code> param is
	 *         empty.
	 */
	public static String nextRandomString(int length, char... cs) {
		if (ArrayUtils.isEmpty(cs)) {
			return "";
		}
		char str[] = new char[length];
		for (int i = 0; i < length; i++) {
			str[i] = cs[nextInt(cs.length)];
		}
		return new String(str);
	}

	/**
	 * Lists possible combinations between letter case (uppper case, lower case, and mixed cases.).
	 *
	 * @author Welyab Paula
	 */
	public static enum LetterCase {
		/**
		 * Indicates letters in upper case.
		 */
		UPPER,
		/**
		 * Indicates letters in lower case.
		 */
		LOWER,
		/**
		 * Indicates letters in upper case or lower case.
		 */
		MIXED;
	}
}

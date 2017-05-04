/*
 * Copyright 2011-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.camaral.sheeco.exceptions;

import java.io.Serializable;

public class RowRange implements Serializable {
	/**
	 * no specific row
	 */
	public static RowRange NO_ROW = new RowRange(-1);
	
	private static final long serialVersionUID = 1;
	private final int startIndex;
	private final int endIndex;

	RowRange(final int rowIndex) {
		this(rowIndex, rowIndex);
	}

	RowRange(final int startIndex, final int endIndex) {
		if (startIndex > endIndex) {
			throw new IllegalArgumentException("invalid row range: "
					+ startIndex + " ~ " + endIndex);
		}
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	@Override
	public String toString() {
		return startIndex == endIndex ? String.valueOf(startIndex) : startIndex
				+ " ~ " + endIndex;
	}
}

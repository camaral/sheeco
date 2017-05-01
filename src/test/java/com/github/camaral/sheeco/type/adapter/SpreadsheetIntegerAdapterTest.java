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
package com.github.camaral.sheeco.type.adapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import com.github.camaral.sheeco.type.adapter.InvalidCellFormatException;
import com.github.camaral.sheeco.type.adapter.SpreadsheetIntegerAdapter;

/**
 * @author caio.amaral
 *
 */
public class SpreadsheetIntegerAdapterTest {
	private final SpreadsheetIntegerAdapter adapter = new SpreadsheetIntegerAdapter();

	@Test
	public void testBlank() {

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BLANK);

		Integer value = adapter.fromSpreadsheet(cell);
		Assert.assertNull(value);
	}

	@Test(expected = InvalidCellFormatException.class)
	public void testInvalidError() {

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_ERROR);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("Vida"));

		adapter.fromSpreadsheet(cell);
	}

	@Test(expected = InvalidCellFormatException.class)
	public void testInvalidFormula() {

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_FORMULA);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("Vida"));

		adapter.fromSpreadsheet(cell);
	}

}

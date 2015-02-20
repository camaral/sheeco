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
package midas.sheeco.type.adapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import junit.framework.Assert;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.Test;

/**
 * @author caio.amaral
 *
 */
public class SpreadsheetDateAdapterTest {
	private final SpreadsheetDateAdapter adapter = new SpreadsheetDateAdapter();

	@Test
	public void testNumericTypeDate() {

		Date expected = new Date(111111);

		CellStyle style = mock(CellStyle.class);
		when(style.getDataFormat()).thenReturn((short) 0x0e);
		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(cell.getDateCellValue()).thenReturn(expected);
		when(cell.getCellStyle()).thenReturn(style);

		Date value = adapter.fromSpreadsheet(cell);
		Assert.assertEquals(expected, value);
	}

	@Test
	public void testBlank() {

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BLANK);

		Date value = adapter.fromSpreadsheet(cell);
		Assert.assertNull(value);
	}

	@Test
	public void testInvalidError() {

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_ERROR);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("Vida"));

		try {
			adapter.fromSpreadsheet(cell);
			Assert.fail("Should have thrown InvalidCellFormatException");
		} catch (InvalidCellFormatException e) {
			// success
		}
	}

	@Test
	public void testInvalidFormula() {

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_FORMULA);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("Vida"));

		try {
			adapter.fromSpreadsheet(cell);
			Assert.fail("Should have thrown InvalidCellFormatException");
		} catch (InvalidCellFormatException e) {
			// success
		}
	}
}

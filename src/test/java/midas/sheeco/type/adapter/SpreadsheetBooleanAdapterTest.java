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
public class SpreadsheetBooleanAdapterTest {
	@Test
	public void testBooleanTypeTrue() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BOOLEAN);
		when(cell.getBooleanCellValue()).thenReturn(true);

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testBooleanTypeFalse() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BOOLEAN);
		when(cell.getBooleanCellValue()).thenReturn(false);

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test
	public void testBlank() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BLANK);

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertNull(value);
	}

	@Test
	public void testNumericTrue() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(cell.getNumericCellValue()).thenReturn(2d);

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testNumericFalse() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(cell.getNumericCellValue()).thenReturn(0d);

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test
	public void testStringTrue() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("TRUe"));

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testStringYes() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("YeS"));

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testString1() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("1"));

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testStringFalse() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("FaLsE"));

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test
	public void testStringNo() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("nO"));

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test
	public void testString0() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("0"));

		Boolean value = adapter.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test
	public void testInvalidString() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("Vida"));

		try {
			adapter.fromSpreadsheet(cell);
			Assert.fail("Should have thrown InvalidCellValueException");
		} catch (InvalidCellValueException e) {
			// success
		}
	}

	@Test
	public void testInvalidNumeric() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

		CellStyle style = mock(CellStyle.class);
		when(style.getDataFormat()).thenReturn((short) 0x0e);
		Cell cell = mock(Cell.class);
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(cell.getDateCellValue()).thenReturn(new Date(111111));
		when(cell.getCellStyle()).thenReturn(style);

		try {
			adapter.fromSpreadsheet(cell);
			Assert.fail("Should have thrown InvalidCellValueException");
		} catch (InvalidCellValueException e) {
			// success
		}
	}

	@Test
	public void testInvalidError() {
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

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
		SpreadsheetBooleanAdapter adapter = new SpreadsheetBooleanAdapter();

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

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

import java.util.Date;

import org.testng.Assert;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.testng.annotations.BeforeMethod;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import com.github.camaral.sheeco.type.adapter.InvalidCellValueException;
import com.github.camaral.sheeco.type.adapter.SpreadsheetBooleanAdapter;

/**
 * @author caio.amaral
 * 
 */
public class SpreadsheetBooleanAdapterTest {

	private SpreadsheetBooleanAdapter sut;

	@Mock
	private Cell cell;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		sut = new SpreadsheetBooleanAdapter();
	}

	@Test
	public void testBooleanTypeTrue() {
		// given
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BOOLEAN);
		when(cell.getBooleanCellValue()).thenReturn(true);

		// when
		Boolean value = sut.fromSpreadsheet(cell);

		// then
		Assert.assertTrue(value);
	}

	@Test
	public void testBooleanTypeFalse() {
		// given
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BOOLEAN);
		when(cell.getBooleanCellValue()).thenReturn(false);

		// when
		Boolean value = sut.fromSpreadsheet(cell);

		// then
		Assert.assertFalse(value);
	}

	@Test
	public void testBlank() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BLANK);

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertNull(value);
	}

	@Test
	public void testNumericTrue() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(cell.getNumericCellValue()).thenReturn(2d);

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testNumericFalse() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(cell.getNumericCellValue()).thenReturn(0d);

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test
	public void testStringTrue() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("TRUe"));

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testStringYes() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("YeS"));

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testString1() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("1"));

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertTrue(value);
	}

	@Test
	public void testStringFalse() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("FaLsE"));

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test
	public void testStringNo() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("nO"));

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test
	public void testString0() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("0"));

		Boolean value = sut.fromSpreadsheet(cell);
		Assert.assertFalse(value);
	}

	@Test(expectedExceptions = InvalidCellValueException.class)
	public void testInvalidString() {
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("Vida"));

		sut.fromSpreadsheet(cell);
	}

	@Test(expectedExceptions = InvalidCellValueException.class)
	public void testInvalidNumeric() {
		CellStyle style = mock(CellStyle.class);
		when(style.getDataFormat()).thenReturn((short) 0x0e);

		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_NUMERIC);
		when(cell.getDateCellValue()).thenReturn(new Date(111111));
		when(cell.getCellStyle()).thenReturn(style);

		sut.fromSpreadsheet(cell);
	}

}

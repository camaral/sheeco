package com.github.camaral.sheeco.type.adapter;

import static org.mockito.Mockito.when;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Repeated scenarios on various adapters
 * 
 */
public class AllAdaptersTest {

	@Mock
	private Cell cell;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expectedExceptions = InvalidCellFormatException.class, dataProvider = "allAdapters")
	public void testInvalidError(SpreadsheetTypeAdapter<? extends Object> sut) {

		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_ERROR);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("Vida"));

		sut.fromSpreadsheet(cell);
	}

	@Test(expectedExceptions = InvalidCellFormatException.class, dataProvider = "allAdapters")
	public void testInvalidFormula(SpreadsheetTypeAdapter<? extends Object> sut) {

		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_FORMULA);
		when(cell.getRichStringCellValue()).thenReturn(
				new HSSFRichTextString("Vida"));

		sut.fromSpreadsheet(cell);
	}

	@DataProvider
	public Object[][] allAdapters() {
		return new Object[][] { { new SpreadsheetStringAdapter() },
				{ new SpreadsheetBooleanAdapter() },
				{ new SpreadsheetDateAdapter() },
				{ new SpreadsheetDoubleAdapter() },
				{ new SpreadsheetIntegerAdapter() },
				{ new SpreadsheetLongAdapter() } };
	}
}

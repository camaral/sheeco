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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Arrays;

public class SpreadsheetViolation {
	private String msgKey;

	private int colNumber;

	private RowRange rowRange;

	private OriginalCellValue originalValue;

	private String localizedMsg = null;

	private String[] msgParams;

	public SpreadsheetViolation(final String msgKey, final Cell cell) {
		this.msgKey = msgKey;
		this.colNumber = cell.getColumnIndex();
		this.rowRange = new RowRange(cell.getRowIndex());
		this.originalValue = new OriginalCellValue(getCellType(cell),
				getCellValue(cell));
	}

	public SpreadsheetViolation(final String msgKey) {
		this.msgKey = msgKey;
		this.colNumber = -1;
		this.rowRange = RowRange.NO_ROW;
		this.originalValue = new OriginalCellValue(OriginalCellType.NO_VALUE,
				null);
	}

	public SpreadsheetViolation(final String msgKey, final String[] msgParams) {
		this.msgKey = msgKey;
		this.msgParams = msgParams;
		this.colNumber = -1;
		this.rowRange = RowRange.NO_ROW;
		this.originalValue = new OriginalCellValue(OriginalCellType.NO_VALUE,
				null);
	}

	public SpreadsheetViolation(final String msgKey, final RowRange rowRange,
			final int colNumber, final OriginalCellValue originalCellValue,
			final String[] msgParams) {
		this.msgKey = msgKey;
		this.rowRange = rowRange;
		this.colNumber = colNumber;
		this.originalValue = originalCellValue;
		this.msgParams = msgParams;
	}

	@SuppressWarnings("unused")
	private SpreadsheetViolation() {
	}

	public String getMsgKey() {
		return msgKey;
	}

	public int getColNumber() {
		return colNumber;
	}

	public RowRange getRowRange() {
		return rowRange;
	}

	public OriginalCellValue getOriginalValue() {
		return originalValue;
	}

	public String getLocalizedMsg() {
		return localizedMsg != null ? localizedMsg : getMsgKey();
	}

	public String[] getMsgParams() {
		return this.msgParams == null ? new String[] {} : this.msgParams;
	}

	@Override
	public String toString() {
		return String.format("row %s column %d violation : %s params: %s ",
				getRowRange().toString(), getColNumber(), getMsgKey(),
				Arrays.toString(getMsgParams()));
	}

	private static Object getCellValue(final Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			}
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		default:
			throw new UnsupportedOperationException("CellType "
					+ cell.getCellType() + " is invalid");
		}
	}

	private static OriginalCellType getCellType(final Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return OriginalCellType.DATE;
			}
			return OriginalCellType.NUMERIC;
		case Cell.CELL_TYPE_STRING:
			return OriginalCellType.STRING;
		case Cell.CELL_TYPE_BLANK:
			return OriginalCellType.BLANK;
		case Cell.CELL_TYPE_BOOLEAN:
			return OriginalCellType.BOOLEAN;
		case Cell.CELL_TYPE_ERROR:
			return OriginalCellType.ERROR;
		case Cell.CELL_TYPE_FORMULA:
			return OriginalCellType.FORMULA;
		default:
			throw new UnsupportedOperationException("CellType "
					+ cell.getCellType() + " is invalid");
		}
	}

}

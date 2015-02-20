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

import org.apache.poi.ss.usermodel.Cell;

/**
 * Adapter from Spreadsheet cell to Java types
 * 
 * @author caio.amaral
 * @param <T>
 *            The adapted Java type
 */
public interface SpreadsheetTypeAdapter<T> {
	/**
	 * Convert the spreadsheet cell data to a Java type. Blank cell are
	 * converted to null. Cells of type {@link Cell#CELL_TYPE_FORMULA} must be
	 * converted before calling this method to avoid coupling with the whole
	 * spreadsheet.
	 * 
	 * @param cell
	 *            The spreadsheet cell. It must contain the right data type.
	 * @return An instance of the Java type converted from the cell
	 * @throws InvalidCellValueException
	 *             If the cell contains an incompatible type or if the cell
	 *             contains an error(type {@link Cell#CELL_TYPE_ERROR})
	 * @throws InvalidCellValueException
	 *             If the cell is formatted with the right data type but it is
	 *             filled in with the wrong data
	 */
	T fromSpreadsheet(final Cell cell) throws InvalidCellValueException,
			InvalidCellValueException;
}

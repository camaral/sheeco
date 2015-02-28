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
package midas.sheeco;

import java.util.List;

import midas.sheeco.exceptions.SpreadsheetViolation;
import midas.sheeco.processor.Attribute;
import midas.sheeco.processor.PayloadContext;
import midas.sheeco.type.adapter.InvalidCellFormatException;
import midas.sheeco.type.adapter.InvalidCellValueException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author caio.amaral
 *
 */
public class PayloadFiller {

	/**
	 * Reads the spreadsheet row and populate the current Object's native fields
	 */
	public static <T> void fillAttributes(final T payload, final Row row,
			final PayloadContext<T> ctx) {
		final List<Attribute> attributes = ctx.getPayload().getAttributes();

		for (final Attribute attr : attributes) {
			final Cell cell = row.getCell(attr.getColumnIndex(),
					Row.CREATE_NULL_AS_BLANK);
			ctx.getEvaluator().evaluateInCell(cell);

			if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
				ctx.addViolation(new SpreadsheetViolation(
						"serializer.spreadsheet.cell.error", cell));
				continue;
			}

			try {
				attr.setValue(payload, cell);
			} catch (final InvalidCellValueException e) {
				ctx.addViolation(new SpreadsheetViolation(
						"serializer.spreadsheet.cell.invalid", cell));
				continue;
			} catch (final InvalidCellFormatException e) {
				ctx.addViolation(new SpreadsheetViolation(
						"serializer.spreadsheet.cell.type.invalid", cell));
				continue;
			}

		}
	}
}

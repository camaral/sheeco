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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import midas.sheeco.annotation.SpreadsheetPayload;
import midas.sheeco.exceptions.SpreadsheetUnmarshallingException;
import midas.sheeco.processor.Payload;
import midas.sheeco.processor.PayloadContext;

import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author caio.amaral
 */
public class Sheeco {
	/**
	 * Max number of blank consecutive rows
	 */
	private static final int MAX_BLANK_ROWS = 50;

	public <T> List<T> fromSpreadsheet(final File file,
			final Class<T> payloadClass)
			throws SpreadsheetUnmarshallingException {
		try {
			return fromSpreadsheet(new BufferedInputStream(new FileInputStream(
					file)), payloadClass);
		} catch (final FileNotFoundException e) {
			throw new RuntimeException(
					String.format("sheeco.serializer.file.cannot.open"));
		}
	}

	public <T> List<T> fromSpreadsheet(final InputStream stream,
			final Class<T> payloadClass)
			throws SpreadsheetUnmarshallingException {
		NPOIFSFileSystem fsFileSystem = null;

		try {
			fsFileSystem = new NPOIFSFileSystem(stream);

			final Workbook wb = WorkbookFactory.create(fsFileSystem);
			final Payload<T> payload = new Payload<>(payloadClass);

			final Sheet sheet = getSheet(payload.getName(), wb);

			final FormulaEvaluator evaluator = wb.getCreationHelper()
					.createFormulaEvaluator();

			return readPayloads(new PayloadContext<>(sheet, evaluator, payload));

		} catch (final FileNotFoundException e) {
			throw new RuntimeException(
					String.format("sheeco.serializer.file.cannot.open"));
		} catch (final IOException e) {
			throw new RuntimeException(
					String.format("sheeco.serializer.file.wrong.format"));
		} finally {
			close(fsFileSystem);
		}
	}

	private <T> Sheet getSheet(final String sheetName, final Workbook wb)
			throws AssertionError {
		final Sheet sheet = wb.getSheet(sheetName);

		if (sheet == null) {
			throw new AssertionError(
					"Spreadsheet does not contain a sheet named with \""
							+ sheetName + "\"");
		}

		return sheet;
	}

	public void toSpreadsheet(final Set<Class<?>> payloadClass,
			final OutputStream stream) {

	}

	public void toSpreadsheet(final List<? extends Object> payloads,
			final OutputStream out) {

	}

	private <T> List<T> readPayloads(final PayloadContext<T> ctx) {
		final List<T> payloads = new ArrayList<>();

		final int firstRowNum = ctx.getSheet().getFirstRowNum();
		int blankRowCount = 0;

		for (final Row row : ctx.getSheet()) {
			if (row.getRowNum() == firstRowNum) {
				// First row reserved for headers
				continue;
			}
			if (!isBlankRow(row)) {
				blankRowCount = 0;

				readPayload(row, ctx);

			} else {
				// if the blank rows limit is reached, throws an exception
				++blankRowCount;
				if (blankRowCount >= MAX_BLANK_ROWS) {
					throw new RuntimeException(
							"serializer.spreadsheet.row.too.many.blank");
				}
			}
		}

		return payloads;
	}

	private <T> T readPayload(final Row row, final PayloadContext<T> ctx) {
		final T payload = ctx.getPayload().newInstance();

		PayloadFiller.fillAttributes(payload, row, ctx);
		readElements();

		return payload;
	}

	/**
	 * Reads the spreadsheet row and populate the current Object's
	 * {@link SpreadsheetPayload} fields
	 */
	private void readElements() {
		// TODO:
	}

	private static void close(final NPOIFSFileSystem fsFileSystem) {
		if (fsFileSystem != null) {
			try {
				fsFileSystem.close();
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private boolean isBlankRow(final Row row) {
		for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
			final Cell cell = row.getCell(i);
			if (cell != null
					&& row.getCell(i).getCellType() != Cell.CELL_TYPE_BLANK) {
				return false;
			}
		}
		return true;
	}
}

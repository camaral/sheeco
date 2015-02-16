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
import java.util.List;
import java.util.Set;

import midas.sheeco.annotation.SpreadsheetPayload;
import midas.sheeco.exceptions.SpreadsheetUnmarshallingException;

import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author caio.amaral
 */
public class Sheeco {

	public <T> List<T> fromSpreadsheet(final File file,
			final Class<T> payloadType)
			throws SpreadsheetUnmarshallingException {
		try {
			return fromSpreadsheet(new BufferedInputStream(new FileInputStream(
					file)), payloadType);
		} catch (final FileNotFoundException e) {
			throw new RuntimeException(
					String.format("sheeco.serializer.file.cannot.open"));
		}
	}

	public <T> List<T> fromSpreadsheet(final InputStream stream,
			final Class<T> payloadType)
			throws SpreadsheetUnmarshallingException {
		NPOIFSFileSystem fsFileSystem = null;
		final Workbook wb;

		try {
			fsFileSystem = new NPOIFSFileSystem(stream);
			wb = WorkbookFactory.create(fsFileSystem);
		} catch (final FileNotFoundException e) {
			throw new RuntimeException(
					String.format("sheeco.serializer.file.cannot.open"));
		} catch (final IOException e) {
			throw new RuntimeException(
					String.format("sheeco.serializer.file.wrong.format"));
		} finally {
			close(fsFileSystem);
		}

		try {
			final SpreadsheetPayload spreadsheetPayload = payloadType
					.getAnnotation(SpreadsheetPayload.class);
			if (spreadsheetPayload == null) {
				throw new AssertionError("class "
						+ payloadType.getCanonicalName()
						+ " is not annotated with @SpreadsheetPayload");
			}
			final String sheetName = spreadsheetPayload.name();
			final Sheet sheet = wb.getSheet(sheetName);

			if (sheet == null) {
				throw new AssertionError(
						"Spreadsheet does not contain a sheet named with \""
								+ sheetName + "\"");
			}
		} finally {
			close(fsFileSystem);
		}

		throw new RuntimeException("TODO!");
	}

	public void toSpreadsheet(final Set<Class<?>> payloadClass,
			final OutputStream stream) {

	}

	public void toSpreadsheet(final List<? extends Object> payloads,
			final OutputStream out) {

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
}

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

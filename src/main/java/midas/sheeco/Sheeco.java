package midas.sheeco;

import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.List;
import java.util.Set;

/**
 * @author caio.amaral
 */
public class Sheeco {

    private static void close(
            final NPOIFSFileSystem fsFileSystem) {
        try {
            fsFileSystem.close();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> fromSpreadsheet(final File file, final Class<T> payloadType) {
        try {
            return fromSpreadsheet(new BufferedInputStream(new FileInputStream(file)), payloadType);
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(String.format("sheeco.serializer.file.cannot.open"));
        }
    }

    public <T> List<T> fromSpreadsheet(final InputStream stream, final Class<T> payloadType) {
        NPOIFSFileSystem fsFileSystem = null;
        final Workbook wb;

        try {
            fsFileSystem = new NPOIFSFileSystem(stream);
            wb = WorkbookFactory.create(fsFileSystem);
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(String.format("sheeco.serializer.file.cannot.open"));
        } catch (final IOException e) {
            throw new RuntimeException(String.format("sheeco.serializer.file.wrong.format"));
        } finally {
            if (fsFileSystem != null) {
                close(fsFileSystem);
            }
        }

        throw new RuntimeException("TODO");
    }

    public void toSpreadsheet(
            final Set<Class<?>> payloadClass,
            final OutputStream stream) {

    }

    public void toSpreadsheet(
            final List<? extends Object> payloads,
            final OutputStream out) {

    }
}

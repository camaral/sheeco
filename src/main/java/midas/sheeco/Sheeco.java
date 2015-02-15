package midas.sheeco;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

/**
 * @author caio.amaral
 */
public class Sheeco {

    public void fromSpreadsheet(final File file) {
        try {
            fromSpreadsheet(new BufferedInputStream(new FileInputStream(file)));
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void fromSpreadsheet(final InputStream stream) {

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

package midas.sheeco.exceptions;

import java.util.List;

/**
 * @author caio.amaral
 */
public class SpreadsheetUnmarshallingException extends Exception {
    private static final long serialVersionUID = 1L;

    // TODO: improve payload type declaration here
    private List<Object> payloads;
    private List<SpreadsheetViolation> violations;

    public SpreadsheetUnmarshallingException(final List<Object> payloads, final List<SpreadsheetViolation> violations) {
        this.payloads = payloads;
        this.violations = violations;
    }
}


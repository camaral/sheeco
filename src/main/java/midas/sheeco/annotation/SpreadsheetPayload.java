package midas.sheeco.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A Java POJO to be serialized as a spreadsheet or deserialized from a spreadsheet
 *
 * @author caio.amaral
 */
@Retention(RUNTIME)
@Target({
        TYPE
})
public @interface SpreadsheetPayload {
    /**
     * The name of the entity represented by this payload. It will be used
     * as the name of the spreadsheet tab.
     *
     * @return O nome da entidade.
     */
    String name() default "";
}

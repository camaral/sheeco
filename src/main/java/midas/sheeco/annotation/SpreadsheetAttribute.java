package midas.sheeco.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * POJO's attribute for serialization/deserialization for spreadsheet
 *
 * @author caio.amaral
 */
@Retention(RUNTIME)
@Target({ FIELD })
public @interface SpreadsheetAttribute {
	/**
	 * Zero based index of the column in which the attribute will be placed
	 * inside the spreadsheet
	 */
	int index();

	/**
	 * The scope of the the column index
	 */
	ScopeType scope() default ScopeType.RELATIVE;

	/**
	 * Name of the column that appears on the first line, as a header. By
	 * default, the name is derived from the JavaBean property name.
	 */
	String name() default "";

	public static enum ScopeType {
		/**
		 * The column index is a fixed position in the spreadsheet
		 */
		ABSOLUTE,
		/**
		 * The column index is the value plus the scope of previous objects in
		 * the relationship
		 */
		RELATIVE;
	}
}

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

package com.github.camaral.sheeco.processor;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;

import com.github.camaral.sheeco.annotation.SpreadsheetAttribute;
import com.github.camaral.sheeco.type.adapter.InvalidCellValueException;
import com.github.camaral.sheeco.type.adapter.SpreadsheetTypeAdapter;
import com.github.camaral.sheeco.type.adapter.SpreadsheetTypeAdapterManager;

public class Attribute {
	private final Field field;
	private final SpreadsheetTypeAdapter<?> typeAdapter;
	private final int columnIndex;
	private final String columnName;

	Attribute(final Field field, final int scopeIndex) {
		this.field = field;
		// this may not work if we run under a security manager
		this.field.setAccessible(true);

		final SpreadsheetAttribute ssAttrib = field
				.getAnnotation(SpreadsheetAttribute.class);

		if (ssAttrib.scope() == SpreadsheetAttribute.ScopeType.RELATIVE) {
			columnIndex = ssAttrib.index() + scopeIndex;
		} else {
			columnIndex = ssAttrib.index();
		}

		this.columnName = getName(ssAttrib, field);
		this.typeAdapter = SpreadsheetTypeAdapterManager
				.getTypeAdapter(ReflectionUtils.getType(field));
	}

	public void setValue(final Object payload, final Cell cell)
			throws InvalidCellValueException {
		final Object value = typeAdapter.fromSpreadsheet(cell);
		if (value != null) {
			try {
				field.set(payload, value);
			} catch (final IllegalArgumentException e) {
				throw new IllegalStateException(
						"Field does not belongs to payload. It may be an programming error.",
						e);
			} catch (final IllegalAccessException e) {
				throw new IllegalStateException(
						"Field is unaccessable. It may be an programming error.", e);
			}
		}
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public String getColumnName() {
		return columnName;
	}

	public SpreadsheetTypeAdapter<?> getTypeAdapter() {
		return typeAdapter;
	}

	public boolean isEquals(final Object payload, final Object previousPayload) {
		try {
			if (payload == null || previousPayload == null) {
				return false;
			}

			final Object obj1 = field.get(payload);
			final Object obj2 = field.get(previousPayload);

			if (obj1 == obj2) {
				return true;
			}
			if (obj1 != null) {
				return obj1.equals(obj2);
			} else if (obj2 == null) {
				// both null
				return true;
			}
			return false;
		} catch (final IllegalArgumentException e) {
			throw new IllegalStateException(
					"Field does not belongs to payload. It may be an programming error.",
					e);
		} catch (final IllegalAccessException e) {
			throw new IllegalStateException(
					"Field is unaccessable. It may be an programming error.", e);
		}
	}

	@Override
	public String toString() {
		return "Attribute [field=" + field + ", typeAdapter=" + typeAdapter
				+ ", index=" + columnIndex + ", name=" + columnName + "]";
	}

	/**
	 * Get the name of the attribute
	 * 
	 * @param attributeAnnotation
	 * @param field
	 * @return {@link SpreadsheetAttribute#name()} if it is not empty; the field
	 *         name otherwise
	 */
	private static String getName(
			final SpreadsheetAttribute attributeAnnotation, final Field field) {
		final String attrName;
		if (attributeAnnotation == null || attributeAnnotation.name().isEmpty()) {
			attrName = field.getName();
		} else {
			attrName = attributeAnnotation.name();
		}
		return attrName;
	}
}

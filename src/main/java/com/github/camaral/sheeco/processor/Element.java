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
import java.util.ArrayList;
import java.util.List;

import com.github.camaral.sheeco.annotation.SpreadsheetElement;
import com.github.camaral.sheeco.type.adapter.InvalidCellValueException;

public class Element {
	private final Field field;
	private final boolean isList;
	private final int firstColumnIndex;
	private final Payload<?> payload;

	Element(final Field field) {
		this.payload = new Payload<>(ReflectionUtils.getType(field), field
				.getAnnotation(SpreadsheetElement.class).index());

		this.field = field;
		// this may not work if we run under a security manager
		this.field.setAccessible(true);

		final Class<?> type = this.field.getType();

		this.isList = List.class.isAssignableFrom(type);

		this.firstColumnIndex = field.getAnnotation(SpreadsheetElement.class)
				.index();
	}

	public void setValue(final Object payload, final Object theElement)
			throws InvalidCellValueException {
		try {
			if (isList) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) field.get(payload);

				if (list == null) {
					list = new ArrayList<Object>();
					field.set(payload, list);
				}
				list.add(theElement);
			} else {
				field.set(payload, theElement);
			}
		} catch (final IllegalArgumentException e) {
			throw new RuntimeException(
					"Setting an invalid value or field does not belongs to payload. May be an programming error",
					e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(
					"Field is unaccessable. May be an programming error", e);
		}
	}

	public Object getValue(final Object payload) {
		try {
			return this.field.get(payload);
		} catch (final IllegalArgumentException e) {
			throw new RuntimeException(
					"Field does not belongs to payload. May be an programming error",
					e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(
					"Field is unaccessable. May be an programming error", e);
		}
	}

	public boolean isList() {
		return isList;
	}

	public Field getField() {
		return field;
	}

	public int getFirstColumnIndex() {
		return firstColumnIndex;
	}

	public Payload<?> getPayload() {
		return payload;
	}

}
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

package com.github.camaral.sheeco.exceptions;

import java.io.Serializable;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

public class OriginalCellValue implements Serializable {
	private static final long serialVersionUID = 4057608708534345778L;
	private final OriginalCellType type;
	private final Object value;

	public OriginalCellValue(final OriginalCellType type, final Object value) {
		this.type = type;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public OriginalCellType getType() {
		return type;
	}

	public void setCellValue(final Cell cell) {
		switch (type) {
		case DATE:
			cell.setCellValue((Date) value);
			break;
		case NUMERIC:
			cell.setCellValue(((Number) value).doubleValue());
			break;
		case BOOLEAN:
			cell.setCellValue((Boolean) value);
			break;
		case STRING:
			cell.setCellValue(String.valueOf(value));
			break;
		case ERROR:
			cell.setCellErrorValue((Byte) value);
			break;
		case FORMULA:
			cell.setCellFormula((String) value);
			break;
		default:
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (type == null ? 0 : type.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OriginalCellValue other = (OriginalCellValue) obj;
		if (type != other.type) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}

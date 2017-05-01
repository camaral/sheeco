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

import java.util.List;
import java.util.Set;

/**
 * This exception is thrown when the spreadsheet was read, but some violations
 * where found. The pieces of payload that could be read are under
 * {@link #getPayloads()}. The exception can be used to recreate the
 * spreadsheet, highlighting the violations.
 * 
 * @author caio.amaral
 */
public class SpreadsheetUnmarshallingException extends Exception {
	private static final long serialVersionUID = 1L;

	// TODO: improve payload type declaration here
	private List<?> payloads;
	private Set<SpreadsheetViolation> violations;

	public SpreadsheetUnmarshallingException(final List<?> payloads,
			final Set<SpreadsheetViolation> violations) {
		this.payloads = payloads;
		this.violations = violations;
	}

	public List<?> getPayloads() {
		return payloads;
	}

	public Set<SpreadsheetViolation> getViolations() {
		return violations;
	}
}

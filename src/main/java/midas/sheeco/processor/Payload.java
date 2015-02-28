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
package midas.sheeco.processor;

import java.util.List;

import midas.sheeco.annotation.SpreadsheetPayload;

/**
 * @author caio.amaral
 *
 */
public class Payload {
	private final String name;
	private final List<Attribute> attributes;
	private final List<Element> elements;

	public Payload(final Class<?> payloadClass) {
		this.name = getName(payloadClass);
		this.attributes = AttributeScanner.scan(payloadClass);
		this.elements = ElementScanner.scan(payloadClass);
	}

	public String getName() {
		return name;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public List<Element> getElements() {
		return elements;
	}

	private static String getName(final Class<?> payloadClass) {
		final SpreadsheetPayload spreadsheetPayload = payloadClass
				.getAnnotation(SpreadsheetPayload.class);
		if (spreadsheetPayload == null) {
			throw new AssertionError("class " + payloadClass.getCanonicalName()
					+ " is not annotated with @SpreadsheetPayload");
		}
		String payloadName = spreadsheetPayload.name();

		if (payloadName == null || payloadName.isEmpty()) {
			payloadName = payloadClass.getSimpleName();
		}
		return payloadName;
	}
}

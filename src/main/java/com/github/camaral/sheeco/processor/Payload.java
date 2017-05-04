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

import java.util.List;

import com.github.camaral.sheeco.annotation.SpreadsheetPayload;
import com.github.camaral.sheeco.exceptions.SpreasheetUnmarshallingUnrecoverableException;

/**
 * @author caio.amaral
 *
 */
public class Payload<T> {
	private final Class<T> clazz;
	private final String name;
	private final List<Attribute> attributes;
	private final List<Element> elements;
	
	public static <X> Payload<X> newPayload(final Class<X> payloadClass) {
		return new Payload<>(payloadClass);
	}
	
	public static <X> Payload<X> newPayload(final Class<X> payloadClass, final int scopeIndex) {
		return new Payload<>(payloadClass, scopeIndex);
	}

	public Payload(final Class<T> payloadClass) {
		this(payloadClass, 0);
	}

	public Payload(final Class<T> payloadClass, final int scopeIndex) {
		this.clazz = payloadClass;
		this.name = getName(payloadClass);
		this.attributes = AttributeScanner.scan(payloadClass, scopeIndex);
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

	public Class<T> getPayloadClass() {
		return clazz;
	}

	public T newInstance() {
		try {
			return clazz.newInstance();
		} catch (final Exception e) {
			throw new SpreasheetUnmarshallingUnrecoverableException(
					"No-args constructor must be present for deserialization");
		}
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

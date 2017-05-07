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

import org.testng.Assert;

import org.testng.annotations.Test;

import com.github.camaral.sheeco.processor.Attribute;
import com.github.camaral.sheeco.processor.AttributeScanner;
import com.github.camaral.sheeco.samples.domain.Cat;
import com.github.camaral.sheeco.type.adapter.SpreadsheetBooleanAdapter;
import com.github.camaral.sheeco.type.adapter.SpreadsheetDateAdapter;
import com.github.camaral.sheeco.type.adapter.SpreadsheetStringAdapter;

/**
 * @author caio.amaral
 * 
 */
public class AttributeScannerTest {

	@Test
	public void testReadAll() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals(attributes.size(), 3);
	}

	@Test
	public void testReadIndexes() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals(attributes.get(0).getColumnIndex(), 0);
		Assert.assertEquals(attributes.get(1).getColumnIndex(), 1);
		Assert.assertEquals(attributes.get(2).getColumnIndex(), 2);
	}

	@Test
	public void testReadFieldNames() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals(attributes.get(0).getColumnName(), "name");
	}

	@Test
	public void testReadAnnotationNames() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals(attributes.get(1).getColumnName(), "Male?");
		Assert.assertEquals(attributes.get(2).getColumnName(), "Birth date");
	}

	@Test
	public void testReadTypes() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals(attributes.get(0).getTypeAdapter().getClass(),
				SpreadsheetStringAdapter.class);
		Assert.assertEquals(attributes.get(1).getTypeAdapter().getClass(),
				SpreadsheetBooleanAdapter.class);
		Assert.assertEquals(attributes.get(2).getTypeAdapter().getClass(),
				SpreadsheetDateAdapter.class);

	}

	@Test
	public void testNotPayload() {
		Assert.assertTrue(AttributeScanner.scan(String.class).isEmpty());
	}
}

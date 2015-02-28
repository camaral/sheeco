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

import junit.framework.Assert;
import midas.sheeco.samples.domain.Cat;
import midas.sheeco.type.adapter.SpreadsheetBooleanAdapter;
import midas.sheeco.type.adapter.SpreadsheetDateAdapter;
import midas.sheeco.type.adapter.SpreadsheetStringAdapter;

import org.junit.Test;

/**
 * @author caio.amaral
 *
 */
public class AttributeScannerTest {

	@Test
	public void testReadAll() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals(3, attributes.size());
	}

	@Test
	public void testReadIndexes() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals(0, attributes.get(0).getColumnIndex());
		Assert.assertEquals(2, attributes.get(1).getColumnIndex());
		Assert.assertEquals(1, attributes.get(2).getColumnIndex());
	}

	@Test
	public void testReadFieldNames() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals("name", attributes.get(0).getColumnName());
	}

	@Test
	public void testReadAnnotationNames() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals("Birth date", attributes.get(1).getColumnName());
		Assert.assertEquals("Male?", attributes.get(2).getColumnName());
	}

	@Test
	public void testReadTypes() {
		final List<Attribute> attributes = AttributeScanner.scan(Cat.class);
		Assert.assertEquals(SpreadsheetStringAdapter.class, attributes.get(0)
				.getTypeAdapter().getClass());
		Assert.assertEquals(SpreadsheetDateAdapter.class, attributes.get(1)
				.getTypeAdapter().getClass());
		Assert.assertEquals(SpreadsheetBooleanAdapter.class, attributes.get(2)
				.getTypeAdapter().getClass());
	}
	
	@Test
	public void testNotPayload() {
		Assert.assertTrue(AttributeScanner.scan(String.class).isEmpty());
	}
}

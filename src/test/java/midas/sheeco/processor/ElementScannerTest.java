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
import midas.sheeco.annotation.SpreadsheetAttribute;
import midas.sheeco.annotation.SpreadsheetElement;
import midas.sheeco.annotation.SpreadsheetPayload;
import midas.sheeco.exceptions.SpreasheetUnmarshallingUnrecoverableException;
import midas.sheeco.samples.domain.Cat;
import midas.sheeco.samples.domain.Fur;

import org.junit.Test;

/**
 * @author caio.amaral
 *
 */
public class ElementScannerTest {
	@Test
	public void testElements() {
		final List<Element> elements = ElementScanner.scan(Cat.class);
		Assert.assertEquals(2, elements.size());

		Assert.assertEquals(Fur.class, elements.get(0).getPayload()
				.getPayloadClass());
		Assert.assertEquals(3, elements.get(0).getFirstColumnIndex());
		Assert.assertEquals(Fur.class, elements.get(1).getPayload()
				.getPayloadClass());
		Assert.assertEquals(5, elements.get(1).getFirstColumnIndex());
	}

	@Test
	public void testNoElement() {
		Assert.assertTrue(ElementScanner.scan(Fur.class).isEmpty());
	}

	@Test
	public void testNotPayload() {
		Assert.assertTrue(ElementScanner.scan(String.class).isEmpty());
	}

	@Test
	public void testMoreThanOneList() {
		try {
			ElementScanner.scan(InvalidTwoList.class);

			Assert.fail("Should have thrown "
					+ SpreasheetUnmarshallingUnrecoverableException.class
							.getName());
		} catch (AssertionError e) {
			Assert.assertEquals(
					"Payload cannot hold more than one list of midas.sheeco.annotation.SpreadsheetElement",
					e.getMessage());
		}
	}
}

@SpreadsheetPayload(name = "InvalidPayload")
class InvalidTwoList {
	@SpreadsheetElement(index = 0)
	private List<B> b1;

	@SpreadsheetElement(index = 0)
	private List<B> b2;
}

@SpreadsheetPayload(name = "B")
class B {
	@SpreadsheetAttribute(index = 0)
	private Integer b;
}

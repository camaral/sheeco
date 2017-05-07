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
package com.github.camaral.sheeco;

import java.io.File;
import java.util.List;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.github.camaral.sheeco.Sheeco;
import com.github.camaral.sheeco.exceptions.SpreadsheetUnmarshallingException;
import com.github.camaral.sheeco.samples.domain.Cat;

/**
 * @author caio.amaral
 * 
 */
public class SheecoTest {

	@Test
	public void testFromXlsxSpreadsheet() throws Exception {
		Sheeco sheeco = new Sheeco();

		List<Cat> cats = sheeco.fromSpreadsheet(new File(
				"src/test/resources/cats.xlsx"), Cat.class);
		assertsFromSpreadsheet(cats);
	}

	@Test
	public void testFromXlsSpreadsheet() throws Exception {
		Sheeco sheeco = new Sheeco();

		List<Cat> cats = sheeco.fromSpreadsheet(new File(
				"src/test/resources/cats.xls"), Cat.class);
		assertsFromSpreadsheet(cats);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testViolations() {
		try {
			Sheeco sheeco = new Sheeco();
			sheeco.fromSpreadsheet(new File(
					"src/test/resources/cats_violations.xlsx"), Cat.class);

			Assert.fail("Should have thrown SpreadsheetUnmarshallingException");
		} catch (SpreadsheetUnmarshallingException e) {
			Assert.assertNotNull(e.getPayloads());
			Assert.assertNotNull(e.getViolations());
			Assert.assertFalse(e.getViolations().isEmpty());

			assertsFromViolationSpreadsheet((List<Cat>) e.getPayloads());
		}
	}

	private void assertsFromViolationSpreadsheet(List<Cat> cats) {

		assertCompleteFields(cats);

		Assert.assertNotNull(cats.get(0).getBirthDate());
		Assert.assertNull(cats.get(1).getBirthDate());
		Assert.assertNotNull(cats.get(2).getBirthDate());

		Assert.assertNull(cats.get(0).isMale());
		Assert.assertFalse(cats.get(1).isMale());
		Assert.assertTrue(cats.get(2).isMale());

		Assert.assertEquals(Integer.valueOf(1), cats.get(0).getBody()
				.getHairLength());
		Assert.assertNull(cats.get(1).getBody().getHairLength());
		Assert.assertNull(cats.get(2).getBody().getHairLength());

		Assert.assertEquals(Integer.valueOf(2), cats.get(0).getTail()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(4), cats.get(1).getTail()
				.getHairLength());
		Assert.assertNull(cats.get(2).getTail().getHairLength());
	}

	private void assertsFromSpreadsheet(List<Cat> cats) {

		assertCompleteFields(cats);

		Assert.assertNotNull(cats.get(0).getBirthDate());
		Assert.assertNotNull(cats.get(1).getBirthDate());
		Assert.assertNotNull(cats.get(2).getBirthDate());

		Assert.assertTrue(cats.get(0).isMale());
		Assert.assertFalse(cats.get(1).isMale());
		Assert.assertTrue(cats.get(2).isMale());

		Assert.assertEquals(Integer.valueOf(1), cats.get(0).getBody()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(4), cats.get(1).getBody()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(5), cats.get(2).getBody()
				.getHairLength());

		Assert.assertEquals(Integer.valueOf(2), cats.get(0).getTail()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(4), cats.get(1).getTail()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(2), cats.get(2).getTail()
				.getHairLength());
	}

	private void assertCompleteFields(List<Cat> cats) {
		assertNames(cats);
		assertBodyHairColor(cats);
		assertTailHairColor(cats);
	}

	private void assertBodyHairColor(List<Cat> cats) {
		Assert.assertEquals("orange", cats.get(0).getBody().getHairColor());
		Assert.assertEquals("black", cats.get(1).getBody().getHairColor());
		Assert.assertEquals("white", cats.get(2).getBody().getHairColor());
	}

	private void assertTailHairColor(List<Cat> cats) {
		Assert.assertEquals("gray", cats.get(0).getTail().getHairColor());
		Assert.assertEquals("white", cats.get(1).getTail().getHairColor());
		Assert.assertEquals("white", cats.get(2).getTail().getHairColor());
	}

	private void assertNames(List<Cat> cats) {
		Assert.assertEquals(3, cats.size());
		Assert.assertEquals(cats.get(0).getName(), "Puss");
		Assert.assertEquals(cats.get(1).getName(), "billie");
		Assert.assertEquals(cats.get(2).getName(), "snow ball");
	}
}

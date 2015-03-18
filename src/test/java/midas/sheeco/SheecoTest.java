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
package midas.sheeco;

import java.io.File;
import java.util.List;

import junit.framework.Assert;
import midas.sheeco.samples.domain.Cat;

import org.junit.Test;

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

	private void assertsFromSpreadsheet(List<Cat> cats) {

		Assert.assertEquals(3, cats.size());
		Assert.assertEquals(cats.get(0).getName(), "Puss");
		Assert.assertEquals(cats.get(1).getName(), "billie");
		Assert.assertEquals(cats.get(2).getName(), "snow ball");

		Assert.assertNotNull(cats.get(0).getBirthDate());
		Assert.assertNotNull(cats.get(1).getBirthDate());
		Assert.assertNotNull(cats.get(2).getBirthDate());

		Assert.assertTrue(cats.get(0).isMale());
		Assert.assertFalse(cats.get(1).isMale());
		Assert.assertTrue(cats.get(2).isMale());

		Assert.assertEquals("orange", cats.get(0).getBody().getHairColor());
		Assert.assertEquals("black", cats.get(1).getBody().getHairColor());
		Assert.assertEquals("white", cats.get(2).getBody().getHairColor());

		Assert.assertEquals(Integer.valueOf(1), cats.get(0).getBody()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(4), cats.get(1).getBody()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(5), cats.get(2).getBody()
				.getHairLength());

		Assert.assertEquals("gray", cats.get(0).getTail().getHairColor());
		Assert.assertEquals("white", cats.get(1).getTail().getHairColor());
		Assert.assertEquals("white", cats.get(2).getTail().getHairColor());

		Assert.assertEquals(Integer.valueOf(2), cats.get(0).getTail()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(4), cats.get(1).getTail()
				.getHairLength());
		Assert.assertEquals(Integer.valueOf(2), cats.get(2).getTail()
				.getHairLength());
	}
}

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Assert;

import org.testng.annotations.Test;

import com.github.camaral.sheeco.Sheeco;
import com.github.camaral.sheeco.exceptions.SpreadsheetUnmarshallingException;
import com.github.camaral.sheeco.samples.domain.Cat;

/**
 * @author caio.amaral
 * 
 */
@Test
public class SheecoTest {
	private Sheeco sut = new Sheeco();

	public void testFromXlsxSpreadsheet() throws Exception {

		List<Cat> cats = sut.fromSpreadsheet(new File(
				"src/test/resources/cats.xlsx"), Cat.class);
		assertsFromSpreadsheet(cats);
	}

	public void testFromXlsSpreadsheet() throws Exception {
		List<Cat> cats = sut.fromSpreadsheet(new File(
				"src/test/resources/cats.xls"), Cat.class);
		assertsFromSpreadsheet(cats);
	}

	@SuppressWarnings("unchecked")
	public void testViolations() {
		try {
			sut.fromSpreadsheet(new File(
					"src/test/resources/cats_violations.xlsx"), Cat.class);

			Assert.fail("Should have thrown SpreadsheetUnmarshallingException");
		} catch (SpreadsheetUnmarshallingException e) {
			Assert.assertNotNull(e.getPayloads());
			Assert.assertNotNull(e.getViolations());
			Assert.assertFalse(e.getViolations().isEmpty());

			assertsFromViolationSpreadsheet((List<Cat>) e.getPayloads());
		}
	}

	public void testToSpreadsheetWithSet() throws Exception {
		// given
		Set<Class<? extends Object>> payloadClasses = new HashSet<>();
		payloadClasses.add(Cat.class);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// when
		sut.toSpreadsheet(out, payloadClasses);

		// then
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		Workbook wb = WorkbookFactory.create(in);
		assertCatHeaders(wb);
	}

	public void testToSpreadsheetWithVarArgs() throws Exception {
		// given
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// when
		sut.toSpreadsheet(out, Cat.class);

		// then
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		Workbook wb = WorkbookFactory.create(in);
		assertCatHeaders(wb);
	}

	private void assertsFromViolationSpreadsheet(List<Cat> cats) {

		assertCompleteFields(cats);

		Assert.assertNotNull(cats.get(0).getBirthDate());
		Assert.assertNull(cats.get(1).getBirthDate());
		Assert.assertNotNull(cats.get(2).getBirthDate());

		Assert.assertNull(cats.get(0).isMale());
		Assert.assertFalse(cats.get(1).isMale());
		Assert.assertTrue(cats.get(2).isMale());

		Assert.assertEquals(cats.get(0).getBody().getHairLength(),
				Integer.valueOf(1));
		Assert.assertNull(cats.get(1).getBody().getHairLength());
		Assert.assertNull(cats.get(2).getBody().getHairLength());

		Assert.assertEquals(cats.get(0).getTail().getHairLength(),
				Integer.valueOf(2));
		Assert.assertEquals(cats.get(1).getTail().getHairLength(),
				Integer.valueOf(4));
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

		Assert.assertEquals(cats.get(0).getBody().getHairLength(),
				Integer.valueOf(1));
		Assert.assertEquals(cats.get(1).getBody().getHairLength(),
				Integer.valueOf(4));
		Assert.assertEquals(cats.get(2).getBody().getHairLength(),
				Integer.valueOf(5));

		Assert.assertEquals(cats.get(0).getTail().getHairLength(),
				Integer.valueOf(2));
		Assert.assertEquals(cats.get(1).getTail().getHairLength(),
				Integer.valueOf(4));
		Assert.assertEquals(cats.get(2).getTail().getHairLength(),
				Integer.valueOf(2));
	}

	private void assertCompleteFields(List<Cat> cats) {
		assertNames(cats);
		assertBodyHairColor(cats);
		assertTailHairColor(cats);
	}

	private void assertBodyHairColor(List<Cat> cats) {
		Assert.assertEquals(cats.get(0).getBody().getHairColor(), "orange");
		Assert.assertEquals(cats.get(1).getBody().getHairColor(), "black");
		Assert.assertEquals(cats.get(2).getBody().getHairColor(), "white");
	}

	private void assertTailHairColor(List<Cat> cats) {
		Assert.assertEquals(cats.get(0).getTail().getHairColor(), "gray");
		Assert.assertEquals(cats.get(1).getTail().getHairColor(), "white");
		Assert.assertEquals(cats.get(2).getTail().getHairColor(), "white");
	}

	private void assertNames(List<Cat> cats) {
		Assert.assertEquals(cats.size(), 3);
		Assert.assertEquals(cats.get(0).getName(), "Puss");
		Assert.assertEquals(cats.get(1).getName(), "billie");
		Assert.assertEquals(cats.get(2).getName(), "snow ball");
	}

	private void assertCatHeaders(Workbook wb) {
		Sheet sheet = wb.getSheet("Cat");
		Assert.assertNotNull(sheet, "Sheet must be created");
		Row row = sheet.getRow(0);
		Assert.assertNotNull(sheet, "Header row must be created");
		assertHeader(row.getCell(0), "name");
		assertHeader(row.getCell(1), "Male?");
		assertHeader(row.getCell(2), "Birth date");

	}

	private void assertHeader(Cell cell, String headerName) {
		Assert.assertNotNull(cell, headerName + " header must be created");
		Assert.assertEquals(cell.getStringCellValue(), headerName);
	}

}

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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.testng.Assert;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.camaral.sheeco.processor.Payload;
import com.github.camaral.sheeco.processor.PayloadContext;
import com.github.camaral.sheeco.processor.PayloadFiller;
import com.github.camaral.sheeco.samples.domain.Cat;

/**
 * @author caio.amaral
 * 
 */
public class PayloadFillerTest {

	@Mock
	private Sheet sheet;
	@Mock
	private Row row;
	@Mock
	private Cell cell;
	@Mock
	private RichTextString richString;
	@Mock
	private FormulaEvaluator evaluator;

	private Cat instance;
	private PayloadContext<Cat> ctx;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mockFormulaCell();
		initContext();

		instance = new Cat();
	}

	@Test
	public void testInstance() {
		// given
		final Payload<Cat> payload = new Payload<>(Cat.class);

		// when
		Cat instance = payload.newInstance();

		// then
		Assert.assertNotNull(instance);
	}

	@Test
	public void fillAllAttributes() throws ParseException {
		// given
		Mockito.when(richString.getString()).thenReturn("Floofly", "0",
				"2014-12-12 01:01:01.000");

		// when
		PayloadFiller.fillAttributes(instance, row, ctx);

		// then
		Assert.assertEquals(instance.getName(), "Floofly");
		Assert.assertEquals(instance.getBirthDate(), new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS").parse("2014-12-12 01:01:01.000"));
		Assert.assertEquals(instance.isMale(), Boolean.FALSE);
	}

	@Test
	public void fillAttributesNull() throws ParseException {

		PayloadFiller.fillAttributes(instance, row, ctx);

		Assert.assertNull(instance.getName());
		Assert.assertNull(instance.getBirthDate());
		Assert.assertNull(instance.isMale());
	}

	@Test
	public void testFillElements() {
		// given
		Mockito.when(richString.getString()).thenReturn("1", "White", "2",
				"Black");

		// when
		PayloadFiller.fillElements(instance, row, ctx);

		// then
		Assert.assertNotNull(instance.getBody());
		Assert.assertNotNull(instance.getTail());
		Assert.assertEquals(instance.getBody().getHairLength(),
				Integer.valueOf(1));
		Assert.assertEquals(instance.getTail().getHairLength(),
				Integer.valueOf(2));
		Assert.assertEquals(instance.getBody().getHairColor(), "White");
		Assert.assertEquals(instance.getTail().getHairColor(), "Black");
	}

	@Test
	public void testFillElementsNullFields() {
		// The adapter excepts that the cell return BLANK on NULLs

		// when
		PayloadFiller.fillElements(instance, row, ctx);

		// then
		Assert.assertNotNull(instance.getBody());
		Assert.assertNotNull(instance.getTail());
		Assert.assertNull(instance.getBody().getHairLength());
		Assert.assertNull(instance.getTail().getHairLength());
		Assert.assertNull(instance.getBody().getHairColor());
		Assert.assertNull(instance.getTail().getHairColor());
	}

	private void mockFormulaCell() {
		Mockito.when(richString.getString()).thenReturn("", "", "");
		Mockito.when(cell.getRichStringCellValue()).thenReturn(richString);
		Mockito.when(
				row.getCell(Mockito.anyInt(),
						Mockito.any(MissingCellPolicy.class))).thenReturn(cell);
		Mockito.when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
	}

	private void initContext() {
		final Payload<Cat> payload = new Payload<>(Cat.class);
		ctx = new PayloadContext<>(sheet, evaluator, payload);
	}

}

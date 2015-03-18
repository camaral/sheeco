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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.Assert;
import midas.sheeco.processor.Payload;
import midas.sheeco.processor.PayloadContext;
import midas.sheeco.samples.domain.Cat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author caio.amaral
 *
 */
public class PayloadFillerTest {

	@Test
	public void fillAllAttributes() throws ParseException {
		final Sheet sheet = Mockito.mock(Sheet.class);
		final Row row = Mockito.mock(Row.class);
		final Cell cell = Mockito.mock(Cell.class);
		final RichTextString richString = Mockito.mock(RichTextString.class);

		Mockito.when(richString.getString()).thenReturn("Floofly",
				"2014-12-12 01:01:01.000", "0");
		Mockito.when(cell.getRichStringCellValue()).thenReturn(richString);
		Mockito.when(
				row.getCell(Mockito.anyInt(),
						Mockito.any(MissingCellPolicy.class))).thenReturn(cell);
		Mockito.when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);

		final FormulaEvaluator evaluator = Mockito.mock(FormulaEvaluator.class);

		final Payload<Cat> payload = new Payload<>(Cat.class);
		final Cat instance = payload.newInstance();

		final PayloadContext<Cat> ctx = new PayloadContext<>(sheet, evaluator,
				payload);

		PayloadFiller.fillAttributes(instance, row, ctx);

		Assert.assertEquals("Floofly", instance.getName());
		Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.parse("2014-12-12 01:01:01.000"), instance.getBirthDate());
		Assert.assertEquals(Boolean.FALSE, instance.isMale());
	}
	
	@Test
	public void testFillElements() {
		final Sheet sheet = Mockito.mock(Sheet.class);
		final Row row = Mockito.mock(Row.class);
		final Cell cell = Mockito.mock(Cell.class);
		final RichTextString richString = Mockito.mock(RichTextString.class);

		Mockito.when(richString.getString()).thenReturn("1",
				"White", "2", "Black");
		Mockito.when(cell.getRichStringCellValue()).thenReturn(richString);
		Mockito.when(
				row.getCell(Mockito.anyInt(),
						Mockito.any(MissingCellPolicy.class))).thenReturn(cell);
		Mockito.when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_STRING);
		
		final Payload<Cat> payload = new Payload<>(Cat.class);
		final Cat instance = payload.newInstance();

		final FormulaEvaluator evaluator = Mockito.mock(FormulaEvaluator.class);
		final PayloadContext<Cat> ctx = new PayloadContext<>(sheet, evaluator,
				payload);
		
		PayloadFiller.fillElements(instance, row, ctx);
		
		Assert.assertNotNull(instance.getBody());
		Assert.assertNotNull(instance.getTail());
		Assert.assertEquals(Integer.valueOf(1), instance.getBody().getHairLength());
		Assert.assertEquals(Integer.valueOf(2), instance.getTail().getHairLength());
		Assert.assertEquals("White", instance.getBody().getHairColor());
		Assert.assertEquals("Black", instance.getTail().getHairColor());
	}
}

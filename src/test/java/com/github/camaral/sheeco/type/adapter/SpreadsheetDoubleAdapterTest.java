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
package com.github.camaral.sheeco.type.adapter;

import static org.mockito.Mockito.when;
import org.testng.Assert;

import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.camaral.sheeco.type.adapter.SpreadsheetDoubleAdapter;

/**
 * @author caio.amaral
 *
 */
public class SpreadsheetDoubleAdapterTest {
	private SpreadsheetDoubleAdapter sut;

	@Mock
	private Cell cell;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		sut = new SpreadsheetDoubleAdapter();
	}

	@Test
	public void testBlank() {
		// given
		when(cell.getCellType()).thenReturn(Cell.CELL_TYPE_BLANK);

		// when
		Double value = sut.fromSpreadsheet(cell);

		// then
		Assert.assertNull(value);
	}

}

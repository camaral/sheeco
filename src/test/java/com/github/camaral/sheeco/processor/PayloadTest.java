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

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.camaral.sheeco.processor.Payload;
import com.github.camaral.sheeco.samples.domain.Cat;
import com.github.camaral.sheeco.samples.domain.Fur;

/**
 * @author caio.amaral
 * 
 */
public class PayloadTest {

	@Test
	public void testElements() {
		// when
		Payload<Cat> payload = new Payload<>(Cat.class);

		// then
		Assert.assertNotNull(payload.getElements());
		Assert.assertFalse(payload.getElements().isEmpty());
		Assert.assertEquals(payload.getElements().get(0).getFirstColumnIndex(),
				3);
		Assert.assertEquals(payload.getElements().get(1).getFirstColumnIndex(),
				5);
		Assert.assertEquals(payload.getElements().get(0).getPayload()
				.getPayloadClass(), Fur.class);
		Assert.assertEquals(payload.getElements().get(1).getPayload()
				.getPayloadClass(), Fur.class);
	}
}

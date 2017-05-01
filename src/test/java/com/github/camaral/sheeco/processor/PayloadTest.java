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

import org.junit.Assert;
import org.junit.Test;

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
		Payload<Cat> payload = new Payload<>(Cat.class);
		Assert.assertNotNull(payload.getElements());
		Assert.assertFalse(payload.getElements().isEmpty());
		Assert.assertEquals(3, payload.getElements().get(0)
				.getFirstColumnIndex());
		Assert.assertEquals(5, payload.getElements().get(1)
				.getFirstColumnIndex());
		Assert.assertEquals(Fur.class, payload.getElements().get(0)
				.getPayload().getPayloadClass());
		Assert.assertEquals(Fur.class, payload.getElements().get(1)
				.getPayload().getPayloadClass());
	}
}

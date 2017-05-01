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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author caio.amaral
 *
 */
public class ReflectionUtils {
	/**
	 * Get the field type
	 * 
	 * @param field
	 * @return The field type; if the field is a List, then return the list
	 *         type.
	 */
	public static Class<?> getType(final Field field) {
		if (List.class.isAssignableFrom(field.getType())) {
			final Type listType = field.getGenericType();
			if (listType instanceof ParameterizedType) {
				final Type[] typeArguments = ((ParameterizedType) listType)
						.getActualTypeArguments();

				assert typeArguments.length == 1;

				return (Class<?>) typeArguments[0];

			} else {
				return String.class;
			}
		} else {
			return field.getType();
		}
	}
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.beanutils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * <p>Test case for property descriptor miss caching in PropertyUtilsBean.</p>
 * @version $Id$
 */
public class PropertyUtilsBeanMissesTestCase extends TestCase {

	public void testMissesCache() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TestBeanA a = new TestBeanA();
		a.setA("fish");

		PropertyDescriptor aProperty1 = PropertyUtils.getPropertyDescriptor(a, "a");
		PropertyDescriptor aProperty2 = PropertyUtils.getPropertyDescriptor(a, "a");
		assertNotNull(aProperty1);
		assertNotNull(aProperty1);
		assertSame(aProperty1, aProperty2);

		/* These are misses, the first one will populate the cache and the remaining two are cached misses */
		assertNull(PropertyUtils.getPropertyDescriptor(a, "b"));
		assertNull(PropertyUtils.getPropertyDescriptor(a, "b"));
		assertNull(PropertyUtils.getPropertyDescriptor(a, "b"));
		
		/* Demonstrate that mapped property descriptors cache still works */
		PropertyDescriptor mapProperty1 = PropertyUtils.getPropertyDescriptor(a, "map");
		PropertyDescriptor mapProperty2 = PropertyUtils.getPropertyDescriptor(a, "map");
		assertNotNull(mapProperty1);
		assertNotNull(mapProperty2);
		assertSame(mapProperty1, mapProperty2);
		
		assertNull(PropertyUtils.getPropertyDescriptor(a, "b"));
	}

	public static class TestBeanA {

		private String a;
		private Map<String, String> map = new HashMap<String, String>();

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}
		
		public String getMap(String key) {
			return map.get(key);
		}
		
		public void setMap(String key, String value) {
			map.put(key, value);
		}

	}

}

/*
 * Java GPX Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */
package io.jenetics.jpx;

import nl.jqno.equalsverifier.EqualsVerifier;

import java.util.Random;
import java.util.function.Supplier;

import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Double.doubleToLongBits;
import static java.lang.Double.longBitsToDouble;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 */
@Test
public class LongitudeTest extends ObjectTester<Longitude> {

	@Override
	Supplier<Longitude> factory(final Random random) {
		return () -> Longitude.ofRadians(random.nextDouble());
	}

	@Test
	public void ofRadians() {
		Assert.assertEquals(
			Longitude.ofRadians(1),
			Longitude.ofDegrees(Math.toDegrees(1))
		);

		Assert.assertEquals(
			Longitude.ofRadians(1).toRadians(),
			Longitude.ofDegrees(Math.toDegrees(1)).toRadians()
		);

		Assert.assertEquals(
			Longitude.ofRadians(excl(Math.PI)).toRadians(),
			Longitude.ofDegrees(Math.toDegrees(excl(Math.PI))).toRadians()
		);
	}

	static double excl(final double value) {
		return longBitsToDouble(doubleToLongBits(value) - 1);
	}

	@Test
	public void ofDegrees() {
		Assert.assertEquals(
			Longitude.ofDegrees(1),
			Longitude.ofRadians(Math.toRadians(1))
		);

		Assert.assertEquals(
			Longitude.ofDegrees(1).toDegrees(),
			Longitude.ofRadians(Math.toRadians(1)).toDegrees()
		);
	}

	@Test
	public void equalsVerifier() {
		EqualsVerifier.forClass(Longitude.class).verify();
	}

}

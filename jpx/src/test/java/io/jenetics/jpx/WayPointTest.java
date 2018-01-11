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

import static java.lang.String.format;
import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.ofInstant;

import nl.jqno.equalsverifier.EqualsVerifier;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.jenetics.jpx.Length.Unit;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 */
@Test
public class WayPointTest extends XMLStreamTestBase<WayPoint> {

	@Override
	public Supplier<WayPoint> factory(Random random) {
		return () -> nextWayPoint(random);
	}

	@Override
	protected Params<WayPoint> params(final Random random) {
		return new Params<>(
			() -> nextWayPoint(random),
			WayPoint.reader("wpt"),
			(p, writer) -> p.write("wpt", writer)
		);
	}

	public static WayPoint nextWayPoint(final Random random) {
		return WayPoint.builder()
			.ele(random.nextBoolean() ? Length.of(random.nextInt(1000), Unit.METER) : null)
			.speed(random.nextBoolean() ? Speed.of(random.nextDouble()*100, Speed.Unit.METERS_PER_SECOND) : null)
			.time(random.nextBoolean()
				? ofInstant(ofEpochMilli(random.nextInt(10000)), UTC)
				: null)
			.magvar(random.nextBoolean() ? Degrees.ofDegrees(random.nextDouble()*10) : null)
			.geoidheight(random.nextBoolean() ? Length.of(random.nextInt(1000), Unit.METER) : null)
			.name(random.nextBoolean() ? format("name_%s", random.nextInt(100)) : null)
			.cmt(random.nextBoolean() ? format("comment_%s", random.nextInt(100)) : null)
			.desc(random.nextBoolean() ? format("description_%s", random.nextInt(100)) : null)
			.src(random.nextBoolean() ? format("source_%s", random.nextInt(100)) : null)
			.links(LinkTest.nextLinks(random))
			.sym(random.nextBoolean() ? format("symbol_%s", random.nextInt(100)) : null)
			.type(random.nextBoolean() ? format("type_%s", random.nextInt(100)) : null)
			.fix(random.nextBoolean() ? Fix.values()[random.nextInt(Fix.values().length)] : null)
			.sat(random.nextBoolean() ? UInt.of(random.nextInt(100)) : null)
			.hdop(random.nextBoolean() ? random.nextDouble() + 2: null)
			.vdop(random.nextBoolean() ? random.nextDouble() + 2: null)
			.pdop(random.nextBoolean() ? random.nextDouble() + 2: null)
			.ageofdgpsdata(random.nextBoolean() ? Duration.ofSeconds(random.nextInt(1000)) : null)
			.dgpsid(random.nextBoolean() ? DGPSStation.of(random.nextInt(100)) : null)
			.build(48 + random.nextDouble()*2, 16 + random.nextDouble()*2);
	}

	public static List<WayPoint> nextWayPoints(final Random random) {
		final List<WayPoint> points = new ArrayList<>();
		for (int i = 0, n = random.nextInt(20); i < n; ++i) {
			points.add(nextWayPoint(random));
		}
		return points;
	}

	@Test
	public void toBuilder() {
		final WayPoint object = nextWayPoint(new Random());

		Assert.assertEquals(
			object.toBuilder().build(),
			object
		);
		Assert.assertNotSame(
			object.toBuilder().build(),
			object
		);
	}

	@Test
	public void equalsVerifier() {
		EqualsVerifier.forClass(WayPoint.class).verify();
	}

	@Test//(invocationCount = 20)
	public void serialize() throws IOException, ClassNotFoundException {
		final Object object = nextWayPoint(new Random());
		Serialization.test(WayPoint.of(1, 2));

		WayPoint wp = WayPoint.of(1.0/3.0, 1.0/3.0);

		final XMLOutputFactory factory = XMLOutputFactory.newFactory();

		try {
			final XMLStreamWriter writer = factory.createXMLStreamWriter(System.out, "UTF-8");

			wp.write("wpt", writer);
		} catch (XMLStreamException e) {
			throw new IOException(e);
		}
	}

}

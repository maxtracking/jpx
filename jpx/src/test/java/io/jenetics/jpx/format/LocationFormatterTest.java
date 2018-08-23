/*
 * Java Genetic Algorithm Library (@__identifier__@).
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
package io.jenetics.jpx.format;

import static io.jenetics.jpx.Length.Unit.METER;
import static io.jenetics.jpx.format.LocationFormatter.ISO_ELE_LONG;
import static io.jenetics.jpx.format.LocationFormatter.ISO_ELE_MEDIUM;
import static io.jenetics.jpx.format.LocationFormatter.ISO_ELE_SHORT;
import static io.jenetics.jpx.format.LocationFormatter.ISO_HUMAN_ELE_LONG;
import static io.jenetics.jpx.format.LocationFormatter.ISO_HUMAN_LAT_LONG;
import static io.jenetics.jpx.format.LocationFormatter.ISO_HUMAN_LONG;
import static io.jenetics.jpx.format.LocationFormatter.ISO_HUMAN_LON_LONG;
import static io.jenetics.jpx.format.LocationFormatter.ISO_LAT_LONG;
import static io.jenetics.jpx.format.LocationFormatter.ISO_LAT_MEDIUM;
import static io.jenetics.jpx.format.LocationFormatter.ISO_LAT_SHORT;
import static io.jenetics.jpx.format.LocationFormatter.ISO_LONG;
import static io.jenetics.jpx.format.LocationFormatter.ISO_LON_LONG;
import static io.jenetics.jpx.format.LocationFormatter.ISO_LON_MEDIUM;
import static io.jenetics.jpx.format.LocationFormatter.ISO_LON_SHORT;
import static io.jenetics.jpx.format.LocationFormatter.ISO_MEDIUM;
import static io.jenetics.jpx.format.LocationFormatter.ISO_SHORT;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.jenetics.jpx.Latitude;
import io.jenetics.jpx.Length;
import io.jenetics.jpx.Longitude;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 */
public class LocationFormatterTest {

	@Test(dataProvider = "formats")
	public void format(
		final LocationFormatter formatter,
		final Location location,
		final String format
	) {
		Assert.assertEquals(formatter.format(location), format);
	}

	@DataProvider
	public Object[][] formats() {
		return new Object[][] {
			{ISO_HUMAN_LAT_LONG, Location.of(Latitude.ofDegrees(23.987635)), "24°59'15.486\"N"},
			{ISO_HUMAN_LAT_LONG, Location.of(Latitude.ofDegrees(-65.234275)), "65°14'03.390\"S"},
			{ISO_HUMAN_LON_LONG, Location.of(Longitude.ofDegrees(23.987635)), "24°59'15.486\"E"},
			{ISO_HUMAN_LON_LONG, Location.of(Longitude.ofDegrees(-65.234275)), "65°14'03.390\"W"},
			{ISO_HUMAN_ELE_LONG, Location.of(Length.of(23.987635, METER)), "23.99m"},
			{ISO_HUMAN_ELE_LONG, Location.of(Length.of(-65.234275, METER)), "-65.23m"},
			{ISO_HUMAN_LONG, Location.of(
				Latitude.ofDegrees(23.987635),
				Longitude.ofDegrees(-65.234275),
				Length.of(-65.234275, METER)), "24°59'15.486\"N 65°14'03.390\"W -65.23m"},

			{ISO_LAT_SHORT, Location.of(Latitude.ofDegrees(23.987635)), "+23.99"},
			{ISO_LAT_SHORT, Location.of(Latitude.ofDegrees(-65.234275)), "-65.23"},
			{ISO_LON_SHORT, Location.of(Longitude.ofDegrees(23.987635)), "+023.99"},
			{ISO_LON_SHORT, Location.of(Longitude.ofDegrees(-65.234275)), "-065.23"},
			{ISO_ELE_SHORT, Location.of(Length.of(23.987635, METER)), "+24CRS"},
			{ISO_ELE_SHORT, Location.of(Length.of(-65.234275, METER)), "-65CRS"},
			{ISO_SHORT, Location.of(
				Latitude.ofDegrees(23.987635),
				Longitude.ofDegrees(-65.234275),
				Length.of(-65.234275, METER)), "+23.99-065.23-65CRS"},

			{ISO_LAT_MEDIUM, Location.of(Latitude.ofDegrees(23.987635)), "+2459.258"},
			{ISO_LAT_MEDIUM, Location.of(Latitude.ofDegrees(-65.234275)), "-6514.056"},
			{ISO_LON_MEDIUM, Location.of(Longitude.ofDegrees(23.987635)), "+02459.258"},
			{ISO_LON_MEDIUM, Location.of(Longitude.ofDegrees(-65.234275)), "-06514.056"},
			{ISO_ELE_MEDIUM, Location.of(Length.of(23.987635, METER)), "+24.0CRS"},
			{ISO_ELE_MEDIUM, Location.of(Length.of(-65.234275, METER)), "-65.2CRS"},
			{ISO_MEDIUM, Location.of(
				Latitude.ofDegrees(23.987635),
				Longitude.ofDegrees(-65.234275),
				Length.of(-65.234275, METER)), "+2459.258-06514.056-65.2CRS"},

			{ISO_LAT_LONG, Location.of(Latitude.ofDegrees(23.987635)), "+245915.49"},
			{ISO_LAT_LONG, Location.of(Latitude.ofDegrees(-65.234275)), "-651403.39"},
			{ISO_LON_LONG, Location.of(Longitude.ofDegrees(23.987635)), "+0245915.49"},
			{ISO_LON_LONG, Location.of(Longitude.ofDegrees(-65.234275)), "-0651403.39"},
			{ISO_ELE_LONG, Location.of(Length.of(23.987635, METER)), "+23.99CRS"},
			{ISO_ELE_LONG, Location.of(Length.of(-65.234275, METER)), "-65.23CRS"},
			{ISO_LONG, Location.of(
				Latitude.ofDegrees(23.987635),
				Longitude.ofDegrees(-65.234275),
				Length.of(-65.234275, METER)), "+245915.49-0651403.39-65.23CRS"}
		};
	}

}

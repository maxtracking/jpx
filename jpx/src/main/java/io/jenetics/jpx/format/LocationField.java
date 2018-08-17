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

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static io.jenetics.jpx.Length.Unit.METER;

import java.util.Optional;
import java.util.function.Function;

import io.jenetics.jpx.Latitude;
import io.jenetics.jpx.Longitude;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version !__version__!
 * @since !__version__!
 */
final class LocationField {

	static final LocationField LATITUDE = new LocationField(
		"latitude",
		loc -> loc.latitude().map(Latitude::toDegrees)
	);

	static final LocationField LONGITUDE = new LocationField(
		"longitude",
		loc -> loc.longitude().map(Longitude::toDegrees)
	);

	static final LocationField ELEVATION = new LocationField(
		"elevation",
		loc -> loc.elevation().map(l -> l.to(METER))
	);

	private final String _name;
	private final Function<Location, Optional<Double>> _value;

	private LocationField(
		final String name,
		final Function<Location, Optional<Double>> value
	) {
		_name = requireNonNull(name);
		_value = requireNonNull(value);
	}

	String name() {
		return _name;
	}

	Optional<Double> value(final Location location) {
		return _value.apply(requireNonNull(location));
	}

	static LocationField of(final char character) {
		switch (character) {
			case 'H': return ELEVATION;
			case 'D':
			case 'M':
			case 'S': return LATITUDE;
			case 'd':
			case 'm':
			case 's': return LONGITUDE;
			default: throw new IllegalStateException(format(
				"Unknown field character: %s", character
			));
		}
	}

}

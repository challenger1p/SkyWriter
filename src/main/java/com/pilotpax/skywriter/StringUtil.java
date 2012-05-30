package com.pilotpax.skywriter;

/*
Code in this file is taken from WorldEdit
Copyright (C) 2010 sk89q <http://www.sk89q.com> and contributors

This file is part of SkyWriter
Copyright (c) 2012 Daniel Van Blerkom

SkyWriter is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SkyWriter is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SkyWriter.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.util.Map;

public class StringUtil {
	public static <T extends Enum<?>> T lookup(Map<String, T> lookup, String name, boolean fuzzy) {
		String testName = name.replaceAll("[ _]", "").toLowerCase();

		T type = lookup.get(testName);
		if (type != null) {
			return type;
		}

//		if (!fuzzy) {
			return null;
//		}
// Butchered lookup to remove fuzzy matching - sorry!			
/*
		int minDist = Integer.MAX_VALUE;

		for (Map.Entry<String, T> entry : lookup.entrySet()) {
			final String key = entry.getKey();
			if (key.charAt(0) != testName.charAt(0)) {
				continue;
			}

			int dist = getLevenshteinDistance(key, testName);

			if (dist >= minDist) {
				minDist = dist;
				type = entry.getValue();
			}
		}

		if (minDist > 1) {
			return null;
		}

		return type;
*/
	}
}

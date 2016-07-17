package org.xine.flywaynominator.business.migration.boundary;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.xine.flywaynominator.business.migration.entity.Attendee;
import org.xine.flywaynominator.business.migration.entity.Migration;

public class MigrationService {
	
	public List<Migration> all(String filePath) {
		final File source = new File(filePath);
		
		if (source.isDirectory()) {

			final File[] files = source.listFiles();

		final List<Migration> migrations = Arrays.asList(files).stream().map(f -> new Migration(f))
					.collect(Collectors.toList());
		
		return migrations;
		} else {
			return Collections.singletonList(new Migration(source));
		}
	}

	public void changeNames(Attendee value, List<Migration> migrations) {
		int index = Integer.valueOf(value.getFirtsIndex());
		for (final Migration m : migrations) {
			m.change(value.getReleaseName(), index, value.getTaskName());
			index++;
		}
	}

}

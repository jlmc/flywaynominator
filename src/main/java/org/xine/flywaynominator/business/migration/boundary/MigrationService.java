package org.xine.flywaynominator.business.migration.boundary;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.xine.flywaynominator.business.migration.entity.Migration;

public class MigrationService {
	
	public List<Migration> all(String filePath) {
		File source = new File(filePath);
		File[] files = source.listFiles();
		
		List<Migration> migrations = Arrays.asList(files).stream().map(f -> new Migration(f))
					.collect(Collectors.toList());
		
		return migrations;
	}

}

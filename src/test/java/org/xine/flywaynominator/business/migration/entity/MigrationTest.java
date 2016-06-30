package org.xine.flywaynominator.business.migration.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

public class MigrationTest {
	private static File file;
	
	@BeforeClass
	public static void initialize() { // src/main/resources/exemple.sql
		file = new File(MigrationTest.class.getClassLoader().getResource("exemple.sql").getFile());
	}

	@Test
	public void shouldCreateMigrationInstance() {
		Migration migration = new Migration(file);

		assertNotNull(migration);
		assertEquals(0.3037109375, migration.getFileSize(), 0.9);
		assertEquals("exemple.sql", migration.getFileName());
		assertNotNull(migration.getCreateAt());
//		assertEquals(LocalDateTime.of(2016, Month.JULY, 1, 0, 22, 54, 45681000), migration.getCreateAt());
		
		System.out.println(migration.getCreateAt());
	}


}

package org.xine.flywaynominator.business.migration.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.BeforeClass;
import org.junit.Test;

public class MigrationTest {
	private static File file;
	
	@BeforeClass
	public static void initialize() {
		file = new File(MigrationTest.class.getClassLoader().getResource("V4.5.0.187__s_rj_2014_var1_16.sql").getFile());
	}

	@Test
	public void shouldCreateMigrationInstance() {
		Migration migration = new Migration(file);

		assertNotNull(migration);
		assertEquals(0.3037109375, migration.getFileSize(), 0.9);
		assertEquals("V4.5.0.187__s_rj_2014_var1_16.sql", migration.getFileName());
		assertNotNull(migration.getCreateAt());
		assertEquals(LocalDateTime.of(2016, Month.JUNE, 27, 23, 35, 41, 64174000), migration.getCreateAt());
		
		System.out.println(migration.getCreateAt());
	}


}

package org.xine.flywaynominator.business.migration.entity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Migration {

	private File file;

	private StringProperty fileName;
	private ReadOnlyDoubleProperty fileSize;
	private ObjectProperty<LocalDateTime> createAt;


	public Migration(File file) {
		if (file == null || !file.exists() || file.isDirectory()) {
			throw new IllegalArgumentException("the file should be a existing file and directory.");
		}

		this.file = file;

		Optional<BasicFileAttributes> attrs  = getBasicAtributes();

		String name = file.getName();
		this.fileName = new SimpleStringProperty(name);
		this.fileSize = new ReadOnlyDoubleWrapper(Double.valueOf(file.length()) / 1024) ;
		LocalDateTime createdDate = attrs.map(b -> b.creationTime()).
				map(f-> LocalDateTime.ofInstant(f.toInstant(), ZoneId.systemDefault())).
				orElse( LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.systemDefault()));

		this.createAt = new ReadOnlyObjectWrapper<LocalDateTime>(createdDate); 
	}

	public ObjectProperty<LocalDateTime> getCreateAtProperty() {
		return this.createAt;
	}

	public LocalDateTime getCreateAt() {
		return this.createAt.get();
	}

	private Optional<BasicFileAttributes> getBasicAtributes() {
		try {
			BasicFileAttributes fileAtr = Files.readAttributes(this.file.toPath(), BasicFileAttributes.class);
			return Optional.of(fileAtr);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return Optional.empty();
		}
	}

	public ReadOnlyDoubleProperty getFileSizeProperty() {
		return this.fileSize;
	}

	public Double getFileSize() {
		return this.fileSize.get();
	}

	public StringProperty fileNameProperty() {
		return this.fileName;
	}

	public String getFileName() {
		return fileNameProperty().get();
	}

	public void setFileName(final String fileName) {
		fileNameProperty().set(fileName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.file);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Migration) {
			return Objects.equals(this.file, ((Migration)obj).file);
		}
		return false;
	}
	
	public void change(String realease, Integer index, String task) {
		String name = this.file.getName();
		String parent = this.file.getParent();
		String newName = realease + "." + index + "__" + task +"_"+ name;
		File newFile = new File(parent + "\\" + newName);
		this.file.renameTo(newFile);
		
		this.file = newFile;
		this.fileName.set(newFile.getName());
	}

}

package org.xine.flywaynominator.business.migration.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attendee {
	private StringProperty releaseNameProperty;
	private StringProperty firtsIndexProperty;
	private StringProperty taskNameProperty;

	public Attendee() {
		this.releaseNameProperty = new SimpleStringProperty();
		this.firtsIndexProperty = new SimpleStringProperty();
		this.taskNameProperty = new SimpleStringProperty();

	}

	public Attendee(String releaseName, String firtsIndex, String taskName) {
		this();
		this.releaseNameProperty.set(releaseName);
		this.firtsIndexProperty.set(firtsIndex);
		this.taskNameProperty.set(taskName);
	}
	
	public StringProperty releaseNameProperty() {
		return this.releaseNameProperty;
	}

	public String getReleaseName() {
		return releaseNameProperty().get();
	}

	public void setReleaseName(final String releaseName) {
		releaseNameProperty().set(releaseName);
	}

	public StringProperty firtsIndexProperty() {
		return this.firtsIndexProperty;
	}

	public  String getFirtsIndex() {
		return firtsIndexProperty().get();
	}

	public void setFirtsIndex(final String firtsIndex) {
		firtsIndexProperty().set(firtsIndex);
	}

	public StringProperty taskNameProperty() {
		return this.taskNameProperty;
	}

	public String getTaskName() {
		return taskNameProperty().get();
	}

	public void setTaskName(final String taskName) {
		taskNameProperty().set(taskName);
	}

	@Override
	public String toString() {
		return "Attendee [releaseName=" + this.releaseNameProperty.get() + ", firtsIndex=" + this.firtsIndexProperty.get()
		+ ", taskName=" + this.taskNameProperty.get() + "]";
	}

}

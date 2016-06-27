package org.xine.flywaynominator.presentation.attendeeinput;

import java.net.URL;
import java.util.ResourceBundle;

import org.xine.flywaynominator.business.migration.entity.Attendee;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class AttendeeinputPresenter implements Initializable {
	
	@FXML private TextField releaseName;
	@FXML private TextField firtsIndex;
	@FXML private TextField taskName;
	
	private ObjectProperty<Attendee> attendee;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.attendee = new SimpleObjectProperty<Attendee>();
		
		// TODO Auto-generated method stub
	}

	public ObjectProperty<Attendee> attendee() {
		return this.attendee;
	}
}

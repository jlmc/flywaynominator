package org.xine.flywaynominator.presentation.dashboard;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.xine.flywaynominator.business.migration.boundary.MigrationService;
import org.xine.flywaynominator.presentation.attendeeinput.AttendeeinputPresenter;
import org.xine.flywaynominator.presentation.attendeeinput.AttendeeinputView;
import org.xine.flywaynominator.presentation.migrations.MigrationPresenter;
import org.xine.flywaynominator.presentation.migrations.MigrationView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class DashboardPresenter implements Initializable {
	@FXML private AnchorPane input;
	@FXML private AnchorPane overview;

	@Inject
	LocalDate date;
	@Inject
	MigrationService service;

	AttendeeinputPresenter attendeeinputPresenter;
	MigrationPresenter migrationPresenter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AttendeeinputView inputView = new AttendeeinputView();
		this.attendeeinputPresenter = (AttendeeinputPresenter) inputView.getPresenter();
		this.input.getChildren().add(inputView.getView());
		
		MigrationView migrationView = new MigrationView();
		
		this.migrationPresenter = (MigrationPresenter) migrationView.getPresenter();
		this.overview.getChildren().add(migrationView.getView());
		
		anchor(migrationView.getView(), 0d);
	}

	private void anchor(Parent parent, double padding) {
		AnchorPane.setTopAnchor(parent, padding);
		AnchorPane.setLeftAnchor(parent, padding);
		AnchorPane.setRightAnchor(parent, padding);
		AnchorPane.setBottomAnchor(parent, padding);
	}

	public void lancher() {
		System.out.println("hello");
		//this.lbtest.setText("Date" + this.date + " -> ");

	}

}

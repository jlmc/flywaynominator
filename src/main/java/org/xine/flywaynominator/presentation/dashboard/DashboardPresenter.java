package org.xine.flywaynominator.presentation.dashboard;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.xine.flywaynominator.business.migration.boundary.MigrationService;
import org.xine.flywaynominator.business.migration.entity.Attendee;
import org.xine.flywaynominator.business.migration.entity.Migration;
import org.xine.flywaynominator.presentation.attendeeinput.AttendeeinputPresenter;
import org.xine.flywaynominator.presentation.attendeeinput.AttendeeinputView;
import org.xine.flywaynominator.presentation.migrations.MigrationPresenter;
import org.xine.flywaynominator.presentation.migrations.MigrationView;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class DashboardPresenter implements Initializable {
	@FXML private AnchorPane input;
	@FXML private AnchorPane overview;

	@Inject private LocalDate date;
	@Inject MigrationService service;

	private AttendeeinputPresenter attendeeinputPresenter;
	private MigrationPresenter migrationPresenter;
	
	private ListProperty<Migration> migrationsProperty;
	private ObjectProperty<Attendee> attendee;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.migrationsProperty = new SimpleListProperty(FXCollections.observableArrayList());
		this.attendee = new SimpleObjectProperty<>();
		
		
		AttendeeinputView inputView = new AttendeeinputView();
		this.attendeeinputPresenter = (AttendeeinputPresenter) inputView.getPresenter();
		this.input.getChildren().add(inputView.getView());
		this.attendee.bind(this.attendeeinputPresenter.attendeeProperty());
		
		
		MigrationView migrationView = new MigrationView();
		this.migrationPresenter = (MigrationPresenter) migrationView.getPresenter();
		this.overview.getChildren().add(migrationView.getView());
		anchor(migrationView.getView(), 0d);
		this.migrationsProperty.bindBidirectional(this.migrationPresenter.getMigrationsProperty());
		
		this.attendeeinputPresenter.setOnPreview( e -> {
			System.out.println(this.migrationsProperty.size());
			System.out.println(this.attendee.get());
			System.out.println("doing on preview");
			
			
			this.service.changeNames(this.attendee.getValue(), getMigrations());
			});
	}

	private void anchor(Parent parent, double padding) {
		AnchorPane.setTopAnchor(parent, padding);
		AnchorPane.setLeftAnchor(parent, padding);
		AnchorPane.setRightAnchor(parent, padding);
		AnchorPane.setBottomAnchor(parent, padding);
	}

	List<Migration> getMigrations(){
		return this.migrationsProperty.getValue();
	}

}

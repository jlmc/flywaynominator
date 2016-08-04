package org.xine.flywaynominator.presentation.attendeeinput;

import java.net.URL;
import java.util.ResourceBundle;

import org.xine.flywaynominator.business.migration.entity.Attendee;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AttendeeinputPresenter implements Initializable {
	@FXML private TextField releaseName;
	@FXML private TextField firtsIndex;
	@FXML private TextField taskName;

	@FXML
	private Button previewButton;

	private final ObjectProperty<Attendee> attendee = new SimpleObjectProperty<Attendee>();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.attendee.set(new Attendee());
		
		this.firtsIndex.addEventFilter(KeyEvent.KEY_TYPED, t -> {
            final char ar[] = t.getCharacter().toCharArray();
            final char ch = ar[t.getCharacter().toCharArray().length - 1];
            if (!(ch >= '0' && ch <= '9')) {
               System.out.println("The char you entered is not a number");
               t.consume();
            }
         });
		
		getAttendee().releaseNameProperty().bindBidirectional(this.releaseName.textProperty());
		getAttendee().firtsIndexProperty().bindBidirectional(this.firtsIndex.textProperty());
		getAttendee().taskNameProperty().bindBidirectional(this.taskName.textProperty());
		
		this.previewButton.disableProperty().bind(
				Bindings.createBooleanBinding(() -> 
					getAttendee().releaseNameProperty().isEmpty().getValue() || 
					getAttendee().firtsIndexProperty().isEmpty().getValue() ||
					getAttendee().taskNameProperty().isEmpty().getValue(), 
				getAttendee().releaseNameProperty(), 
				getAttendee().firtsIndexProperty(), 
				getAttendee().taskNameProperty()));
	}

	@FXML
	void onPreview(final ActionEvent event) {
		System.out.println("> on Preview");
		getOnPreview().handle(event);
	}

	public ObjectProperty<Attendee> attendeeProperty() {
		return this.attendee;
	}
	
	public Attendee getAttendee() {
		return this.attendee.getValue();
	}

	public void setAttendee(final Attendee attendee) {
		attendeeProperty().set(attendee);
	}
	
	private final ObjectProperty<EventHandler<ActionEvent>> onPreview = new SimpleObjectProperty<EventHandler<ActionEvent>>();
    public final ObjectProperty<EventHandler<ActionEvent>> onPreviewProperty() { return this.onPreview; }
    public final void setOnPreview(final EventHandler<ActionEvent> value) { onPreviewProperty().set(value); }
    public final EventHandler<ActionEvent> getOnPreview() { return onPreviewProperty().get(); }
	
}

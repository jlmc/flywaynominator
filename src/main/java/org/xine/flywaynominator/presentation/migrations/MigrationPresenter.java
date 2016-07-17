package org.xine.flywaynominator.presentation.migrations;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.xine.flywaynominator.business.migration.boundary.MigrationService;
import org.xine.flywaynominator.business.migration.entity.Migration;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

public class MigrationPresenter implements Initializable {
	private ListProperty<Migration> migrationsProperty;

	@FXML private TableView<Migration> migrationsTable;
	
	@Inject
	MigrationService service;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.migrationsProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
		prepareTable();
	}
	
	public ListProperty<Migration> getMigrationsProperty() {
		return this.migrationsProperty;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void prepareTable() {
		this.migrationsTable.setEditable(false);

		// table.getSelectionModel().setCellSelectionEnabled(true);
		// table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		this.migrationsTable.getSelectionModel().setCellSelectionEnabled(true);
		// this.migrationsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		this.migrationsTable.setOnMouseClicked(
				e -> this.migrationsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE));

		final ObservableList<TableColumn<Migration, ?>> columns = this.migrationsTable.getColumns();

		final TableColumn<Migration, String> fileNameColumn = createTextColumn("fileName", "File Name");
		columns.add(fileNameColumn);
		final TableColumn fileSizeColumn = createDoubleColumn("fileSize", "File Size (KB)");
		fileSizeColumn.setMaxWidth(150.0D);
		fileSizeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		columns.add(fileSizeColumn);
		final TableColumn<Migration, LocalDateTime> fileCreateAt = createLocalDateTimeColumn("createAt", " Create At");
		fileCreateAt.setMaxWidth(150.0D);
		fileCreateAt.setStyle("-fx-alignment: CENTER-RIGHT;");
		columns.add(fileCreateAt);

		this.migrationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.migrationsTable.setItems(getMigrations());
		this.migrationsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		setOnDrag();
		setOnDeleteRows();
	}

	private void setOnDeleteRows() {
		this.migrationsTable.setOnKeyPressed(e -> {
			if (KeyCode.DELETE != e.getCode()) {
				return;
			}

			final ObservableList<Migration> selectedItems = this.migrationsTable.getSelectionModel().getSelectedItems();
			if (selectedItems.isEmpty()) {
				return;
			}

			this.migrationsProperty.removeAll(selectedItems);
			this.migrationsTable.getSelectionModel().clearSelection();
			e.consume();
		});
	}

	private void setOnDrag() {
		this.migrationsTable.setOnDragOver(event -> {
		     event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			 event.consume();
		});
		
		this.migrationsTable.setOnDragDropped(event -> {
			 final Dragboard db = event.getDragboard();
			 System.out.println(db.hasFiles());
             if (db.hasFiles()) {
            	 final List<File> sourceFolders = db.getFiles();
            	 final File source = sourceFolders.get(0);
            	 final String filePath = source.getAbsolutePath();
            	 loadFromStore(filePath);
             }
             event.consume();
		});
	}

	private void loadFromStore(String filePath) {
		final List<Migration> all = this.service.all(filePath);
		for (final Migration migration : all) {
			if (getMigrations().contains(migration)) {
				continue;
			}
			add(migration);
		}
	}
	
	private TableColumn<Migration, LocalDateTime> createLocalDateTimeColumn(String name, String caption){
		final TableColumn<Migration, LocalDateTime> column = new TableColumn<>(caption);
		appendEditListeners(column);
		column.setCellValueFactory(new PropertyValueFactory<Migration, LocalDateTime>(name));
		column.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
		return column;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TableColumn createDoubleColumn(String name, String caption){
		final TableColumn column = new TableColumn<>(caption);
		appendEditListeners(column);
		column.setCellValueFactory(new PropertyValueFactory<Migration, Double>(name));
		column.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter(new DecimalFormat("###.###"))));
		return column;
	}
	
//	private TableColumn<Migration, Boolean> createBooleanColumn(String name, String caption) {
//		TableColumn<Migration, Boolean> column = new TableColumn<Migration, Boolean>(caption);
//		appendEditListeners(column);
//		column.setCellValueFactory(new PropertyValueFactory<Migration, Boolean>(name));
//		column.setCellFactory(CheckBoxTableCell.forTableColumn(column));
//		return column;
//	}

	private TableColumn<Migration, String> createTextColumn(String name, String caption) {
		final TableColumn<Migration, String> column = new TableColumn<Migration, String>(caption);
		appendEditListeners(column);
		column.setCellValueFactory(new PropertyValueFactory<Migration, String>(name));
		column.setCellFactory(TextFieldTableCell.forTableColumn());
		return column;
	}

	@SuppressWarnings("rawtypes")
	private void appendEditListeners( TableColumn column) {
//		column.setOnEditStart(e -> {
//			// nothing 
//		});
//
//		column.setOnEditCancel(e -> {
//		});
	}

	public void add(Migration migration) {
		getMigrations().add(migration);
	}
	
	public ObservableList<Migration> getMigrations(){
		return this.migrationsProperty.get();
	}
}

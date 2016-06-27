package org.xine.flywaynominator.presentation.migrations;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.xine.flywaynominator.business.migration.entity.Migration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

public class MigrationPresenter implements Initializable {

	private ObservableList<Migration> migrations;  

	@FXML private TableView<Migration> migrationsTable;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.migrations = FXCollections.observableArrayList();
		prepareTable();
	}

	private void prepareTable() {
		this.migrationsTable.setEditable(true);
		ObservableList<TableColumn<Migration, ?>> columns = this.migrationsTable.getColumns();

		final TableColumn<Migration, String> fileNameColumn = createTextColumn("fileName", "File Name");
		columns.add(fileNameColumn);
		TableColumn fileSizeColumn = createDoubleColumn("fileSize", "File Size (KB)");
		fileSizeColumn.setMaxWidth(150.0D);
		fileSizeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		columns.add(fileSizeColumn);
		TableColumn<Migration, LocalDateTime> fileCreateAt = createLocalDateTimeColumn("createAt", " Create At");
		fileCreateAt.setMaxWidth(150.0D);
		fileCreateAt.setStyle("-fx-alignment: CENTER-RIGHT;");
		columns.add(fileCreateAt);

		this.migrationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.migrationsTable.setItems(this.migrations);
		this.migrationsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	private TableColumn<Migration, LocalDateTime> createLocalDateTimeColumn(String name, String caption){
		TableColumn<Migration, LocalDateTime> column = new TableColumn<>(caption);
		appendEditListeners(column);
		column.setCellValueFactory(new PropertyValueFactory<Migration, LocalDateTime>(name));
		column.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
		return column;
	}
	
	private TableColumn createDoubleColumn(String name, String caption){
		TableColumn column = new TableColumn<>(caption);
		appendEditListeners(column);
		column.setCellValueFactory(new PropertyValueFactory<Migration, Double>(name));
		column.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter(new DecimalFormat("###.###"))));
		return column;
	}
	
	private TableColumn createBooleanColumn(String name, String caption) {
		TableColumn column = new TableColumn(caption);
		appendEditListeners(column);
		column.setCellValueFactory(new PropertyValueFactory<Migration, Boolean>(name));
		column.setCellFactory(CheckBoxTableCell.forTableColumn(column));
		return column;
	}

	private TableColumn createTextColumn(String name, String caption) {
		TableColumn column = new TableColumn(caption);
		appendEditListeners(column);
		column.setCellValueFactory(new PropertyValueFactory<Migration, String>(name));
		column.setCellFactory(TextFieldTableCell.forTableColumn());
		return column;
	}

	private void appendEditListeners(TableColumn column) {
		column.setOnEditStart(e -> {
			System.out.println("edit started");
		});

		column.setOnEditCancel(e -> {
			System.out.println("edit canceled");
		});

	}

	public void add(Migration migration) {
		this.migrations.add(migration);
	}
}

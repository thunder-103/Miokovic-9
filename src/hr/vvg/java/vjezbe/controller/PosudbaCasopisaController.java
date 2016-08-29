package hr.vvg.java.vjezbe.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.vvg.java.vjezbe.baza.BazaPodataka;
import hr.vvg.java.vjezbe.entitet.Casopis;
import hr.vvg.java.vjezbe.entitet.Posudba;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class PosudbaCasopisaController {
	
	@FXML
	private TextField nazivCasopisa;
	@FXML
	private TableView<Posudba<Casopis>> posudbaTable;
	@FXML
	private TableColumn<Posudba<Casopis>, String> nazivCasopisaColumn;
	@FXML
	private TableColumn<Posudba<Casopis>, String> prezimeClanaColumn;
	@FXML
	private TableColumn<Posudba<Casopis>, String> imeClanaColumn;
	@FXML
	private TableColumn<Posudba<Casopis>, String> datumPosudbeColumn;			
	@FXML 
	public void initialize() { 
		 
		nazivCasopisaColumn.setCellValueFactory(new Callback<CellDataFeatures<Posudba<Casopis>, String>, 
				ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Posudba<Casopis>, String> data) {
						return new ReadOnlyObjectWrapper<String>(data
								.getValue().getPublikacija().getNaziv());
					}
				});

		prezimeClanaColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Posudba<Casopis>, String>, 
						ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Posudba<Casopis>, String> data) {
						return new ReadOnlyObjectWrapper<String>(data
								.getValue().getClan().getPrezimeClana());
					}
				});

		imeClanaColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Posudba<Casopis>, String>, 
						ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Posudba<Casopis>, String> data) {
						return new ReadOnlyObjectWrapper<String>(data
								.getValue().getClan().getImeClana());
					}
				});

		datumPosudbeColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Posudba<Casopis>, String>, 
						ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Posudba<Casopis>, String> data) {
						DateTimeFormatter format = DateTimeFormatter
								.ofPattern("dd.MM.yyyy.");
						return new ReadOnlyObjectWrapper<String>(format
								.format(data.getValue().getDatumPosudbe()));
					}
				});						
	}
	
	public void prikaziSveCasopise() {

		List<Posudba<Casopis>> posudbe = new ArrayList<>(); 
		
		try {
			posudbe = BazaPodataka.dohvatiPosudbeCasopisa();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Posudba<Casopis>> filtriranePosudbe = new ArrayList<>();

		if (nazivCasopisa.getText().isEmpty() == false) {
			filtriranePosudbe = posudbe
					.stream()
					.filter(p -> p.getPublikacija().getNaziv().contains(nazivCasopisa.getText()))
					.collect(Collectors.toList());
		} else {
			filtriranePosudbe = posudbe;
		}
				
		ObservableList<Posudba<Casopis>> listaPosudbi = FXCollections
				.observableArrayList(filtriranePosudbe);

		posudbaTable.setItems(listaPosudbi);
	}

}

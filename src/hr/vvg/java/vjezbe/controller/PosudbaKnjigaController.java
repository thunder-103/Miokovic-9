package hr.vvg.java.vjezbe.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import hr.vvg.java.vjezbe.baza.BazaPodataka;
import hr.vvg.java.vjezbe.entitet.Knjiga;
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

public class PosudbaKnjigaController {
	
	
	@FXML
	private TextField nazivKnjige;
	@FXML
	private TableView<Posudba<Knjiga>> posudbeTable;
	@FXML
	private TableColumn<Posudba<Knjiga>, String> nazivKnjigeColumn;
	@FXML
	private TableColumn<Posudba<Knjiga>, String> prezimeClanaColumn;
	@FXML
	private TableColumn<Posudba<Knjiga>, String> imeClanaColumn;
	@FXML
	private TableColumn<Posudba<Knjiga>, String> datumPosudbeColumn;
	
	
	@FXML
	public void initialize() {

		nazivKnjigeColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Posudba<Knjiga>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Posudba<Knjiga>, String> data) {
						return new ReadOnlyObjectWrapper<String>(data
								.getValue().getPublikacija().getNaziv());
					}
				});

		prezimeClanaColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Posudba<Knjiga>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Posudba<Knjiga>, String> data) {
						return new ReadOnlyObjectWrapper<String>(data
								.getValue().getClan().getPrezimeClana());
					}
				});

		imeClanaColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Posudba<Knjiga>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Posudba<Knjiga>, String> data) {
						return new ReadOnlyObjectWrapper<String>(data
								.getValue().getClan().getImeClana());
					}
				});

		datumPosudbeColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Posudba<Knjiga>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Posudba<Knjiga>, String> data) {
						DateTimeFormatter format = DateTimeFormatter
								.ofPattern("dd.MM.yyyy.");
						return new ReadOnlyObjectWrapper<String>(format
								.format(data.getValue().getDatumPosudbe()));
					}
				});
	}

	public void prikaziSveKnjige() {

		List<Posudba<Knjiga>> posudbe = new ArrayList<>();

		try {
			posudbe = BazaPodataka.dohvatiPosudbeKnjiga();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Posudba<Knjiga>> filtriranePosudbe = new ArrayList<>();

		if (nazivKnjige.getText().isEmpty() == false) {
			filtriranePosudbe = posudbe
					.stream()
					.filter(p -> p.getPublikacija().getNaziv()
							.contains(nazivKnjige.getText()))
					.collect(Collectors.toList());
		} else {
			filtriranePosudbe = posudbe;
		}

		ObservableList<Posudba<Knjiga>> listaPosudbi = FXCollections
				.observableArrayList(filtriranePosudbe);

		posudbeTable.setItems(listaPosudbi);
	}

}

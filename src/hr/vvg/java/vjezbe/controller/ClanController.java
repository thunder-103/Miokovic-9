package hr.vvg.java.vjezbe.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.dialog.Dialogs;

import hr.vvg.java.vjezbe.baza.BazaPodataka;
import hr.vvg.java.vjezbe.controller.base.DialogHelpers;
import hr.vvg.java.vjezbe.entitet.Casopis;
import hr.vvg.java.vjezbe.entitet.Clan;
import hr.vvg.java.vjezbe.entitet.Knjiga;
import hr.vvg.java.vjezbe.entitet.Posudba;
import hr.vvg.java.vjezbe.entitet.Publikacija;
import hr.vvg.java.vjezbe.glavna.JavaFXGlavna;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class ClanController {
	
	List<Clan> listaClanova = new ArrayList<>();
	
	private Publikacija publikacija;
	
	private Stage stage;
	
	
	@FXML 
	private TextField prezimeClana; 
	
	@FXML 
	private TableView<Clan> clanTable;
	
	@FXML 
	private TableColumn<Knjiga, String> imeClanaColumn; 
	
	@FXML 
	private TableColumn<Knjiga, String> prezimeClanaColumn; 
	
	@FXML 
	private TableColumn<Knjiga, String> oibClanaColumn; 
	
	
	
	@FXML 
	public void initialize() { 
		
		imeClanaColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, String>("imeClana"));
		prezimeClanaColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, String>("prezimeClana"));
		oibClanaColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, String>("oibClana"));
		
		
		clanTable.setOnMouseClicked(new EventHandler<MouseEvent>() { 
			
			@Override 
			public void handle(MouseEvent event) { 
				
				if (publikacija == null) 
					
					return; 
				
				if (event.getClickCount() > 1) { 
					
					Clan clan = (Clan) clanTable.getSelectionModel() .getSelectedItem(); 
					
					if (publikacija instanceof Knjiga) { 
						
						Posudba<Knjiga> posudba = new Posudba<>(0,
								clan, (Knjiga) publikacija, LocalDateTime.now()); 
						
						try { 
							
							BazaPodataka.spremiPosudbuKnjige(posudba); 
						
						} catch (Exception e) { 
							
							e.printStackTrace();
							
							DialogHelpers.konekcijskiError(); 
							
						} 
						
					} else { 

						
						Posudba<Casopis> posudba = new Posudba<>(0, clan, (Casopis) publikacija, LocalDateTime.now()); 
						
						try { BazaPodataka.spremiPosudbuCasopisa(posudba); 
						
						} catch (Exception e) { 
							
							e.printStackTrace(); 
							
							DialogHelpers.konekcijskiError(); 
							
						} 
						
					} Dialogs.create().title("Informacija") .message("Posudba je uspješno kreirana.") 
					
					.showInformation(); 
					
					stage.close(); 
					
				} 
				
			} 
			
		});
	
	}

	
	public void prikaziSveClanove() {
		
		
		try {
			
			listaClanova = BazaPodataka.dohvatiClanove();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	List<Clan> filtriraniClanovi = new ArrayList<Clan>();
	
	if (prezimeClana.getText().isEmpty() == false) {
		
		filtriraniClanovi = listaClanova.stream().filter(p -> p.getPrezimeClana() .contains(prezimeClana.getText())) 
				.collect(Collectors.toList()); 
		} else { 
			
			filtriraniClanovi = listaClanova;
			
				} 
	
	ObservableList<Clan> listaClanova = FXCollections.observableArrayList(filtriraniClanovi);
	
	clanTable.setItems(listaClanova); 
	
	
	}
	
//public void obrisi() { 
//		
//		Clan c = clanTable.getSelectionModel().getSelectedItem(); 
//		
//		clanTable.getItems().remove(c); 
//		
//		try {
//			Datoteke.unesiClana(clanTable.getItems());
//		} catch (FileNotFoundException | ParseException
//				| DuplikatPublikacijeException e) {
//			e.printStackTrace();
//		} 
//		
//	} 
	
	public void uredi() { 
		
		try { 
			
			FXMLLoader l = new FXMLLoader(getClass().getResource( "../javafx/clanUredi.fxml")); 
			
			BorderPane root = (BorderPane)l.load(); 
			
			ClanoviUrediController cont = l .<ClanoviUrediController> getController(); 
			
			cont.urediParametri(clanTable.getItems(), clanTable .getSelectionModel().getSelectedItem()); 
			
			JavaFXGlavna.setCenterPane(root); 
			
		} 
		
		catch (IOException e) { 
			
			e.printStackTrace(); 
			
		}
	}
	
	public Publikacija getPublikacija() {
		return publikacija;
	}


	public Stage getStage() {
		return stage;
	}


	public void setPublikacija(Publikacija publikacija) {
		this.publikacija = publikacija;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}

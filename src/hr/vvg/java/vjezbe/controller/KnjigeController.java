package hr.vvg.java.vjezbe.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.vvg.java.vjezbe.baza.BazaPodataka;
import hr.vvg.java.vjezbe.entitet.Knjiga;
import hr.vvg.java.vjezbe.enumeracija.Jezik;
import hr.vvg.java.vjezbe.enumeracija.VrstaPublikacije;
import hr.vvg.java.vjezbe.glavna.JavaFXGlavna;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class KnjigeController {
	

	List<Knjiga> listaKnjiga = new ArrayList<>();
	
	@FXML 
	private TextField nazivKnjige; 
	
	@FXML 
	private TableView<Knjiga> knjigaTable;
	
	@FXML 
	private TableColumn<Knjiga, String> nazivKnjigeColumn; 
	
	@FXML 
	private TableColumn<Knjiga, VrstaPublikacije> vrstaKnjigeColumn; 
	
	@FXML 
	private TableColumn<Knjiga, Integer> godinaIzdanjaKnjigeColumn; 
	
	@FXML 
	private TableColumn<Knjiga, Integer> brojStranicaKnjigeColumn; 
	
	@FXML 
	private TableColumn<Knjiga, Jezik> jezikKnjigeColumn; 
	
	@FXML 
	private TableColumn<Knjiga, String> nazivIzdavacaKnjigeColumn;
	
	
	@FXML 
	public void initialize() { 
		nazivKnjigeColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, String>("naziv")); 
		vrstaKnjigeColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, VrstaPublikacije>("vrstaPublikacije")); 
		godinaIzdanjaKnjigeColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, Integer>("godinaIzdanja")); 
		brojStranicaKnjigeColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, Integer>("brojStranica")); 
		jezikKnjigeColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, Jezik>("jezikKnjige")); 
		nazivIzdavacaKnjigeColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, String>("nazivIzdavaca"));
		
		knjigaTable.setOnMouseClicked(new EventHandler<MouseEvent>(){ 
			
			@Override public void handle(MouseEvent event) { 
				
				if (event.getClickCount()>1) { 
					
					Knjiga knjiga = (Knjiga) knjigaTable.getSelectionModel().getSelectedItem(); 
					
					try { 
						
						FXMLLoader fxmlLoader = new FXMLLoader(); 
						
						URL location = ClanController.class.getResource("../javafx/clanovi.fxml"); 
						
						fxmlLoader.setLocation(location); 
						
						fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory()); 
						
						Parent root = (Parent)fxmlLoader.load(location.openStream()); 
						
						ClanController controller = (ClanController)fxmlLoader.getController(); 
						
						controller.setPublikacija(knjiga); 
						
						Stage stage = new Stage(); 
						
						stage.setTitle("Odabir èlana za posudbu knjige " + knjiga.getNaziv()); 
						
						stage.setScene(new Scene(root, 650, 250)); 
						
						stage.show(); 
						
						controller.setStage(stage); 
						
					} catch (IOException e) { 
						
						e.printStackTrace(); 
						
					} 
					
				}
				
			}
			
		});
		
	}
		
		
		
	
	public void prikaziSveKnjige() {
		

		
		try {
			
			listaKnjiga = BazaPodataka.dohvatiKnjige();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	List<Knjiga> filtriraneKnjige = new ArrayList<Knjiga>();
	
	if (nazivKnjige.getText().isEmpty() == false) { 
		filtriraneKnjige = listaKnjiga.stream().filter(p -> p.getNaziv() .contains(nazivKnjige.getText())) 
				.collect(Collectors.toList()); 
		} else { 
			
			filtriraneKnjige = listaKnjiga;
			
				} 
	ObservableList<Knjiga> listaKnjiga = FXCollections.observableArrayList(filtriraneKnjige); 
	knjigaTable.setItems(listaKnjiga); 
	
	}
	
//	public void obrisi() { 
//		
//		Knjiga c = knjigaTable.getSelectionModel().getSelectedItem(); 
//		
//		knjigaTable.getItems().remove(c); 
//		
//		try {
//			Datoteke.unesiKnjige(knjigaTable.getItems());
//		} catch (FileNotFoundException | ParseException
//				| DuplikatPublikacijeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//	} 
	
	public void uredi() { 
		
		try { 
			
			FXMLLoader l = new FXMLLoader(getClass().getResource( "../javafx/knjigaUredi.fxml")); 
			
			BorderPane root = (BorderPane)l.load(); 
			
			KnjigaUrediController cont = l .<KnjigaUrediController> getController(); 
			
			cont.urediParametri(knjigaTable.getItems(), knjigaTable .getSelectionModel().getSelectedItem()); 
			
			JavaFXGlavna.setCenterPane(root); 
			
		} 
		
		catch (IOException e) { 
			
			e.printStackTrace(); 
			
		}
		
	}
	
}
	
	



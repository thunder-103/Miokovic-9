package hr.vvg.java.vjezbe.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.vvg.java.vjezbe.baza.BazaPodataka;
import hr.vvg.java.vjezbe.entitet.Casopis;
import hr.vvg.java.vjezbe.entitet.Knjiga;
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

public class CasopisController {
	
	List<Casopis> listaCasopisa = new ArrayList<>();
	
	
	@FXML 
	private TextField nazivCasopisa; 
	
	@FXML 
	private TableView<Casopis> casopisTable;
	
	@FXML 
	private TableColumn<Knjiga, String> nazivCasopisaColumn; 
	
	@FXML 
	private TableColumn<Knjiga, VrstaPublikacije> vrstaCasopisaColumn; 
	
	@FXML 
	private TableColumn<Knjiga, Integer> godinaIzdanjaCasopisaColumn; 
	
	@FXML 
	private TableColumn<Knjiga, Integer> brojStranicaCasopisaColumn; 
	
	@FXML 
	private TableColumn<Knjiga, Integer> mjesecIzavanjaCasopisaColumn;
	
	
	
	@FXML 
	public void initialize() { 
		
		nazivCasopisaColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, String>("naziv")); 
		vrstaCasopisaColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, VrstaPublikacije>("vrstaPublikacije")); 
		godinaIzdanjaCasopisaColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, Integer>("godinaIzdanja")); 
		brojStranicaCasopisaColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, Integer>("brojStranica")); 
		mjesecIzavanjaCasopisaColumn.setCellValueFactory( new PropertyValueFactory<Knjiga, Integer>("mjesecIzdavanjaCasopisa")); 
		
		
		casopisTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
			
				if (event.getClickCount() > 1) {
					
					Casopis casopis = (Casopis) casopisTable.getSelectionModel().getSelectedItem();
					
					try {
						
						FXMLLoader fxmlLoader = new FXMLLoader();
						
						URL location = ClanController.class
								.getResource("../javafx/clanovi.fxml");
						
						fxmlLoader.setLocation(location);
						
						fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
						
						Parent root = (Parent) fxmlLoader.load(location.openStream());
						
						ClanController controller = (ClanController) fxmlLoader.getController();
						
						controller.setPublikacija(casopis);
						
						Stage stage = new Stage();
						
						stage.setTitle("Odabir èlana za posudbu èasopisa " + casopis.getNaziv());
						
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

	
	
	public void prikaziSveCasopise() {
		

		
		try {
			
			listaCasopisa = BazaPodataka.dohvatiCasopise();
			
		} catch (Exception e) {
				
				e.printStackTrace();
			}
		
		
	List<Casopis> filtriraniCasopisi = new ArrayList<Casopis>();
	
	if (nazivCasopisa.getText().isEmpty() == false) {
		
		filtriraniCasopisi = listaCasopisa.stream().filter(p -> p.getNaziv() .contains(nazivCasopisa.getText())) 
				.collect(Collectors.toList()); 
		} else { 
			
			filtriraniCasopisi = listaCasopisa;
			
				} 
	
	ObservableList<Casopis> listaCasopisa = FXCollections.observableArrayList(filtriraniCasopisi);
	
	casopisTable.setItems(listaCasopisa); 
	
	
	}
	
//public void obrisi() { 
//		
//		Casopis c = casopisTable.getSelectionModel().getSelectedItem(); 
//		
//		casopisTable.getItems().remove(c); 
//		
//		try {
//			Datoteke.unesiCasopis(casopisTable.getItems());
//		} catch (FileNotFoundException | ParseException
//				| DuplikatPublikacijeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//	} 
	
	public void uredi() { 
		
		try { 
			
			FXMLLoader l = new FXMLLoader(getClass().getResource( "../javafx/casopisUredi.fxml")); 
			
			BorderPane root = (BorderPane)l.load(); 
			
			CasopisiUrediController cont = l .<CasopisiUrediController> getController(); 
			
			cont.urediParametri(casopisTable.getItems(), casopisTable .getSelectionModel().getSelectedItem()); 
			
			JavaFXGlavna.setCenterPane(root); 
			
		} 
		
		catch (IOException e) { 
			
			e.printStackTrace(); 
			
		} 
		
	}

}

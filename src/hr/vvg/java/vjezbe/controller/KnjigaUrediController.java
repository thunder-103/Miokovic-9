package hr.vvg.java.vjezbe.controller;
import java.math.BigDecimal;
import java.util.List;
import org.controlsfx.dialog.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import hr.vvg.java.vjezbe.baza.BazaPodataka;
import hr.vvg.java.vjezbe.controller.base.DialogHelpers;
import hr.vvg.java.vjezbe.controller.base.UrediBase;
import hr.vvg.java.vjezbe.entitet.Izdavac;
import hr.vvg.java.vjezbe.entitet.Knjiga;
import hr.vvg.java.vjezbe.enumeracija.Jezik;
import hr.vvg.java.vjezbe.enumeracija.VrstaPublikacije;


@SuppressWarnings("deprecation")
public class KnjigaUrediController extends UrediBase {
	
	@FXML 
	private TextField nazivKnjiga;
	
	@FXML 
	private ComboBox<String> vrstaKnjige;
	
	@FXML 
	private TextField godinaKnjige;
	
	@FXML 
	private TextField brStranicaKnjige;
	
	@FXML 
	private ComboBox<String> jezikKnjige;

	@FXML 
	private TextField nazivIzdavaca;
	
	@FXML 
	private TextField drzavaIzdavaca;
	
	private boolean isEdit; 
	
	private Knjiga zaPrikaz; 
	
	public KnjigaUrediController() {
		
	} 
	
	public void urediParametri(List<Knjiga> knjige, Knjiga zaPrikaz) { 
		
		this.zaPrikaz = zaPrikaz;
		isEdit = true; 
		nazivKnjiga.setText(zaPrikaz.getNaziv()); 
		vrstaKnjige.setValue(zaPrikaz.getVrstaPublikacije().toString()); 
		godinaKnjige.setText(zaPrikaz.getGodinaIzdanja() + ""); 
		brStranicaKnjige.setText(zaPrikaz.getBrojStranica() + ""); 
		jezikKnjige.setValue(zaPrikaz.getJezikKnjige().toString()); 
		nazivIzdavaca.setText(zaPrikaz.getIzdavacKnjige().getNazivIzdavaca()); 
		drzavaIzdavaca.setText(zaPrikaz.getDrzavaIzdavaca()); } 
	
	@FXML 
	public void initialize() { 
		
		jezikKnjige.getItems().addAll(Jezik.vrijednosti()); 
		vrstaKnjige.getItems().addAll(VrstaPublikacije.vrijednosti()); 
		
	}
	
	@FXML 
	private void unesiKnjigu() { 
		
		if (!(validirajVrijednost(nazivIzdavaca) & validirajVrijednost(nazivKnjiga) 
				& validirajVrijednost(drzavaIzdavaca) & validirajVrijednost(vrstaKnjige) 
				& validirajVrijednost(jezikKnjige) & validirajBroj(godinaKnjige) 
				& validirajBroj(brStranicaKnjige))) { 
			
			Dialogs.create().title("Gre�ka") .message("Podaci nisu u ispravnom formatu!").showError(); 
			
			return; 
			
		} 
		
		Jezik jezik = Jezik.valueOf(jezikKnjige.getValue());
		
		float cijenaStranice = (jezik == Jezik.HRVATSKI) ? 0.45f : 0.75f; 
		
		Knjiga knjiga = new Knjiga(zaPrikaz.getId(), nazivKnjiga.getText(), jezik, 
				new Izdavac(zaPrikaz.getIzdavacKnjige().getIdIzdavaca(), nazivIzdavaca.getText(), 
						drzavaIzdavaca.getText()), 
				Integer.parseInt(godinaKnjige.getText()), VrstaPublikacije.valueOf(vrstaKnjige.getValue()), 
				BigDecimal.valueOf(cijenaStranice), Integer.parseInt(brStranicaKnjige.getText()), isEdit);
		
		
		try { 
			
			if (isEdit) { 
				
				knjiga.setId(zaPrikaz.getId()); 
				
				knjiga.getIzdavacKnjige().setId(zaPrikaz.getIzdavacKnjige().getIdIzdavaca()); 
				
				BazaPodataka.promijeniKnjigu(knjiga); 
				
			} else 
				
				BazaPodataka.spremiKnjigu(knjiga); 
			
		} catch (Exception e) { 
			
			DialogHelpers.konekcijskiError(); 
			
			e.printStackTrace(); 
			
			return; 
			
		} Dialogs.create().title("Informacija") .message("Knjiga je uspje�no unesena").showInformation(); 
		
	}
	
}


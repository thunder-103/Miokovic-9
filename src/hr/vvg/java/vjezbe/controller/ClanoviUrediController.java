package hr.vvg.java.vjezbe.controller;


import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import org.controlsfx.dialog.Dialogs;

import hr.vvg.java.vjezbe.baza.BazaPodataka;
import hr.vvg.java.vjezbe.controller.base.UrediBase;
import hr.vvg.java.vjezbe.entitet.Clan;


@SuppressWarnings("deprecation")
public class ClanoviUrediController extends UrediBase {
	
	@FXML 
	private TextField imeClana;
	
	@FXML 
	private TextField prezimeClana;
	
	@FXML 
	private TextField oibClana;
	
	private boolean isEdit;
	
	private Clan zaPrikaz; 
	
	private List<Clan> clanovi; 
	
	public ClanoviUrediController() {
		
	} 
	
public void urediParametri(List<Clan> clanovi, Clan zaPrikaz) { 
		
		this.zaPrikaz = zaPrikaz;
		this.clanovi = clanovi;
		
		imeClana.setText(zaPrikaz.getImeClana()); 
		prezimeClana.setText(zaPrikaz.getPrezimeClana()); 
		oibClana.setText(zaPrikaz.getOibClana()); 

 
		
} 
	
	@FXML 
	private void unesiClana() {
		
		List<Clan> clanovi = null; 
		if (!(validirajVrijednost(imeClana) 
				 & validirajVrijednost(prezimeClana) 
				& validirajVrijednost(oibClana))) { 
			
			Dialogs.create().title("Gre�ka") .message("Podaci nisu u ispravnom formatu!").showError(); 
			
			return; 
			
		} 
		
		if (isEdit) { 
			
			clanovi = this.clanovi; 
			clanovi.remove(zaPrikaz); 
			
		} 
		
		else {
			
			try {
				clanovi = BazaPodataka.dohvatiClanove();
			}  catch (Exception e) {

				e.printStackTrace();
			} 
		}
		
		
		Clan clan = new Clan(0, imeClana.getText(),prezimeClana.getText(), oibClana.getText()); 
		
		
		try {
			BazaPodataka.spremiClana(clan);
		}  catch (Exception e) {

			e.printStackTrace();
		}
		
		Dialogs.create().title("Informacija") .message("�lan je uspje�no unesen").showInformation();
	}


}

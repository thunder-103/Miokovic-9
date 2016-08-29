package hr.vvg.java.vjezbe.controller.base;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;



public class DialogHelpers {
	
	@SuppressWarnings("null")
	static void prikaziGresku() {
		
		Control ctl = null;
		
		ctl.getStyleClass().add("error"); 
		
		ctl.setTooltip(new Tooltip("Pogreška prilikom pristupanja bazi podataka"));
		
	}
	
	public static void konekcijskiError() {
		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("Problem sa pristupom bazi podataka!");
		alert.setTitle("Baza podataka");
		alert.show();
		
	}

}

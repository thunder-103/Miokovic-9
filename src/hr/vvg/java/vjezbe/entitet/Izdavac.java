package hr.vvg.java.vjezbe.entitet;

import java.io.Serializable;

public class Izdavac implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nazivIzdavaca;
	private String drzavaIzdavaca;
	private int id;
	
	public Izdavac(int id, String nazivIzdavaca, String drzavaIzdavaca) {
		
		this.id = id;
		this.nazivIzdavaca = nazivIzdavaca;
		this.drzavaIzdavaca = drzavaIzdavaca;
	}
	
	public int getIdIzdavaca() {
		
		return id;
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazivIzdavaca() {
		return nazivIzdavaca;
	}

	public String getDrzavaIzdavaca() {
		return drzavaIzdavaca;
	}
}

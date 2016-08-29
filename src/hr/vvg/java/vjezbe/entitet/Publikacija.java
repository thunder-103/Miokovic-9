package hr.vvg.java.vjezbe.entitet;

import hr.vvg.java.vjezbe.enumeracija.VrstaPublikacije;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Publikacija implements ZaProdaju, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal cijena;
	private BigDecimal cijenaPoStranici;
	private int godinaIzdanja;
	private int brojStranica;
	private VrstaPublikacije vrstaPublikacije;
	private String naziv;
	private int id;
	
	//private static final String VRSTA_PUBLIKACIJE_ELEKTRONICKA = "Vrsta publikacije elektroni�ka";
	//private static final String VRSTA_PUBLIKACIJE_PAPIRNATA = "Vrsta publikacije papirnata";
	
	
	public Publikacija(int id, String naziv, int godinaIzdanja, int brojStranica, 
			VrstaPublikacije vrstaPublikacije, BigDecimal cijenaPoStranici) {
		this.id = id;
		this.naziv = naziv;
		this.godinaIzdanja = godinaIzdanja;
		this.brojStranica = brojStranica;
		this.vrstaPublikacije = vrstaPublikacije;
		this.cijenaPoStranici = cijenaPoStranici;
		this.cijena = cijenaPublikacije(brojStranica, vrstaPublikacije, this.cijenaPoStranici);
	}
	
	public int getId() {
		
		return id;
		
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getCijena() {
		return cijena;
	}


	public String getNaziv() {
		return naziv;
	}

	public int getGodinaIzdanja() {
		return godinaIzdanja;
	}


	public int getBrojStranica() {
		return brojStranica;
	}


	public VrstaPublikacije getVrstaPublikacije() {
		return vrstaPublikacije;
	}


//	public static String getVrstaPublikacijeElektronicka() {
//		return VRSTA_PUBLIKACIJE_ELEKTRONICKA;
//	}


//	public static String getVrstaPublikacijePapirnata() {
//		return VRSTA_PUBLIKACIJE_PAPIRNATA;
//	}

}

package hr.vvg.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Posudba<T extends Publikacija> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Clan clan;
	
	private T publikacija;
	
	private LocalDateTime datumPosudbe;
	
	private int id;

	public Posudba(int id, Clan clan, T publikacija, LocalDateTime datumPosudbe) {
		
		this.id = id;
		this.clan = clan;
		this.publikacija = publikacija;
		this.datumPosudbe = datumPosudbe;
	}

	public int getIdPosudbe() {
		return id;
	}



	public Clan getClan() {
		return clan;
	}

	public T getPublikacija() {
		return publikacija;
	}

	public LocalDateTime getDatumPosudbe() {
		return datumPosudbe;
	}
}

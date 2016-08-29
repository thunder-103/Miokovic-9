package hr.vvg.java.vjezbe.baza;

import hr.vvg.java.vjezbe.entitet.Casopis;
import hr.vvg.java.vjezbe.entitet.Clan;
import hr.vvg.java.vjezbe.entitet.Izdavac;
import hr.vvg.java.vjezbe.entitet.Knjiga;
import hr.vvg.java.vjezbe.entitet.Posudba;
import hr.vvg.java.vjezbe.enumeracija.Jezik;
import hr.vvg.java.vjezbe.enumeracija.VrstaPublikacije;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BazaPodataka {
	
	
	static Connection spojiNaBazu() throws SQLException {
	
		
		Connection veza= DriverManager.getConnection(
					"jdbc:h2:tcp://localhost/~/H2baza", "student", "student");
		
		return veza;

		
	}
	
	private static void zatvoriKonekciju(Connection vezaNaBazu) throws SQLException {
		
	
		vezaNaBazu.close();
		
	}
	
	public static List<Knjiga> dohvatiKnjige() throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
		
		List<Knjiga> listaKnjiga = new ArrayList<>(); 
		
		String queryString = "SELECT * FROM RAZVOJ.KNJIGA"; 
		
		PreparedStatement preparedStatement = konekcijaNaBazu.prepareStatement(queryString); 
		
		ResultSet resultSet = preparedStatement.executeQuery(); 
		
		while (resultSet.next()) { 
			
			int id = resultSet.getInt("id"); 
			
			String naziv = resultSet.getString("naziv"); 
			
			Integer godinaIzdanja = resultSet.getInt("godinaIzdanja"); 
			
			Integer vrstaPublikacijeId = resultSet.getInt("vrstaPublikacije"); 
			
			VrstaPublikacije vrstaPublikacije = null; 
			
			for (VrstaPublikacije temp : VrstaPublikacije.values()) { 
				
				if (vrstaPublikacijeId == temp.getBrojVrste()) { 
					
					vrstaPublikacije = temp; 
					
				} 
				
			}
		
			Integer brojStranica = resultSet.getInt("brojStranica"); 
			
			Integer jezikId = resultSet.getInt("jezik"); 
			
			Jezik jezik = null; 
			
			for (Jezik temp : Jezik.values()) { 
				
				if (jezikId == temp.getBrojJezika()) { 
					
					jezik = temp; 
					
				} 
				
			} 
			
			Integer izdavacId = resultSet.getInt("izdavac"); 
			
			Izdavac izdavac = dohvatiIzdavaca(izdavacId); 
			
			float cijenaStranice = (jezik == Jezik.HRVATSKI) ? 0.45f : 0.75f; 
			
			Knjiga knjiga = new Knjiga(id, naziv, jezik, izdavac, godinaIzdanja, vrstaPublikacije, 
					BigDecimal.valueOf(cijenaStranice), brojStranica, null);
			
			listaKnjiga.add(knjiga); 
					
		} 
		
		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu); 
		
		return listaKnjiga; 
		
	}
	
	private static Izdavac dohvatiIzdavaca(Integer izdavacId) throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
		
		String queryString = "SELECT * FROM RAZVOJ.IZDAVAC WHERE ID = ?"; 
		
		PreparedStatement preparedStatement = konekcijaNaBazu .prepareStatement(queryString); 
		
		preparedStatement.setInt(1, izdavacId); 
		
		ResultSet rs = preparedStatement.executeQuery();
		
		Izdavac izdavac = null; 
		
		while (rs.next()) { 
			
			int id = rs.getInt("id"); 
			
			String naziv = rs.getString("naziv"); 
			
			String drzava = rs.getString("drzava"); 
			
			izdavac = new Izdavac(id, naziv, drzava); 
			
		} 
		
		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu); 
		
		return izdavac;
	}
	
	public static List<Casopis> dohvatiCasopise() throws Exception { 
			
			Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
			
			List<Casopis> listaCasopisa = new ArrayList<>(); 
			
			String queryString = "SELECT * FROM RAZVOJ.CASOPIS"; 
			
			PreparedStatement preparedStatement = konekcijaNaBazu .prepareStatement(queryString); 
			
			ResultSet resultSet = preparedStatement.executeQuery(); 
			
			while (resultSet.next()) { 
				
				int id = resultSet.getInt("id"); 
				
				String naziv = resultSet.getString("naziv"); 
				
				Integer godinaIzdanja = resultSet.getInt("godinaIzdanja"); 
				
				Integer vrstaPublikacijeId = resultSet.getInt("vrstaPublikacije"); 
				
				VrstaPublikacije vrstaPublikacije = null; 
				
				for (VrstaPublikacije temp : VrstaPublikacije.values()) { 
					
					if (vrstaPublikacijeId == temp.getBrojVrste()) { 
						
						vrstaPublikacije = temp; 
						
					} 
					
				}
			
				Integer brojStranica = resultSet.getInt("brojStranica"); ; 
				
				Integer mjesecIzdavanjaCasopisa = resultSet.getInt("mjesecIzdanja");
				
				Casopis casopis = new Casopis(id, naziv, godinaIzdanja, brojStranica, vrstaPublikacije, mjesecIzdavanjaCasopisa);
				
				listaCasopisa.add(casopis); 
						
			} 
			
			BazaPodataka.zatvoriKonekciju(konekcijaNaBazu); 
			
			return listaCasopisa; 
			
		}

	public static List<Clan> dohvatiClanove() throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
		
		List<Clan> listaClanova = new ArrayList<>(); 
		
		String queryString = "SELECT * FROM RAZVOJ.CLAN"; 
		
		PreparedStatement preparedStatement = konekcijaNaBazu .prepareStatement(queryString); 
		
		ResultSet resultSet = preparedStatement.executeQuery(); 
		
		while (resultSet.next()) { 
			
			int id = resultSet.getInt("id"); 
			
			String oib = resultSet.getString("oib"); 
			
			String ime = resultSet.getString("ime");
			
			String prezime = resultSet.getString("prezime");
			
			Clan clan = new Clan(id, oib, ime, prezime);
			
			listaClanova.add(clan); 
					
		} 
		
		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu); 
		
		return listaClanova; 
		
	}
	
	public static void spremiKnjigu(Knjiga knjiga) throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
		
		konekcijaNaBazu.setAutoCommit(false); 
		
		PreparedStatement insertIzdavac = konekcijaNaBazu .prepareStatement("INSERT INTO RAZVOJ.IZDAVAC (naziv, drzava) VALUES (?, ?)"); 
		
		insertIzdavac.setString(1, knjiga.getNazivIzdavaca()); 
		
		insertIzdavac.setString(2, knjiga.getDrzavaIzdavaca()); 
		
		insertIzdavac.executeUpdate(); 
		
		//dohvat generiranog kljuèa zbog potrebe za unosom knjige koja je vezana na //izdavaèa 
		
		ResultSet generatedKeys = insertIzdavac.getGeneratedKeys(); 
		
		if (generatedKeys.next()) { 
			
			knjiga.getIzdavacKnjige().setId(generatedKeys.getInt(1)); 
			
		}
		
		PreparedStatement insertKnjiga = konekcijaNaBazu 
				.prepareStatement("INSERT INTO RAZVOJ.KNJIGA (naziv, godinaizdanja, vrstapublikacije, brojstranica, jezik, izdavac) "
						+ "VALUES (?, ?, ?, ?, ?, ?)"); insertKnjiga.setString(1, knjiga.getNaziv()); 
						
						insertKnjiga.setInt(2, knjiga.getGodinaIzdanja()); 
						
						insertKnjiga.setInt(3, knjiga.getVrstaPublikacije().getBrojVrste()); 
						
						insertKnjiga.setInt(4, knjiga.getBrojStranica()); 
						
						insertKnjiga.setInt(5, knjiga.getJezikKnjige().getBrojJezika()); 
						
						insertKnjiga.setInt(6, knjiga.getIzdavacKnjige().getIdIzdavaca()); 
						
						insertKnjiga.executeUpdate(); 
						
						konekcijaNaBazu.commit(); 
						
						konekcijaNaBazu.setAutoCommit(true); 
						
						BazaPodataka.zatvoriKonekciju(konekcijaNaBazu); 
						
	}
	
	public static void spremiCasopis(Casopis casopis) throws Exception { 
			
			Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
			
			PreparedStatement insertCasopsi = konekcijaNaBazu 
					.prepareStatement("INSERT INTO RAZVOJ.CASOPIS (naziv, godinaizdanja, vrstapublikacije, brojstranica, mjesecizdanja) "
							+ "VALUES (?, ?, ?, ?, ?)"); 
			
			insertCasopsi.setString(1, casopis.getNaziv()); 
							
			insertCasopsi.setInt(2, casopis.getGodinaIzdanja()); 
							
			insertCasopsi.setInt(3, casopis.getVrstaPublikacije().getBrojVrste()); 
							
			insertCasopsi.setInt(4, casopis.getBrojStranica()); 
							
			insertCasopsi.setInt(5, casopis.getMjesecIzdavanjaCasopisa());  
							
			insertCasopsi.executeUpdate(); 
							
			BazaPodataka.zatvoriKonekciju(konekcijaNaBazu); 
							
	}

	
	public static void spremiClana(Clan clan) throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
		
		PreparedStatement insertCasopsi = konekcijaNaBazu 
				.prepareStatement("INSERT INTO RAZVOJ.CLAN (oib, ime, prezime) "
						+ "VALUES (?, ?, ?)"); 
		
		insertCasopsi.setString(1, clan.getOibClana());
		
		insertCasopsi.setString(2, clan.getImeClana());
		
		insertCasopsi.setString(3, clan.getPrezimeClana());
						 			
		insertCasopsi.executeUpdate(); 
						
		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu); 
						
	}
	
	public static void promijeniKnjigu(Knjiga knjiga) throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
		
		konekcijaNaBazu.setAutoCommit(false); 
		
		PreparedStatement insertIzdavac = konekcijaNaBazu 
				.prepareStatement("UPDATE RAZVOJ.IZDAVAC SET naziv = ?, drzava = ? WHERE ID = ?"); 
		
		insertIzdavac.setString(1, knjiga.getNazivIzdavaca()); 
		
		insertIzdavac.setString(2, knjiga.getDrzavaIzdavaca()); 
		
		insertIzdavac.setInt(3, knjiga.getIzdavacKnjige().getIdIzdavaca()); 
		
		insertIzdavac.executeUpdate(); 
		
		PreparedStatement insertKnjiga = konekcijaNaBazu 
				.prepareStatement("UPDATE RAZVOJ.KNJIGA SET naziv = ?, godinaizdanja = ?, vrstapublikacije = ?, "
						+ "brojstranica = ?, jezik = ?, izdavac = ? WHERE ID = ?"); 
		
		insertKnjiga.setString(1, knjiga.getNaziv()); 
		
		insertKnjiga.setInt(2, knjiga.getGodinaIzdanja()); 
		
		insertKnjiga.setInt(3, knjiga.getVrstaPublikacije().getBrojVrste()); 
		
		insertKnjiga.setInt(4, knjiga.getBrojStranica()); 
		
		insertKnjiga.setInt(5, knjiga.getJezikKnjige().getBrojJezika()); 
		
		insertKnjiga.setInt(6, knjiga.getIzdavacKnjige().getIdIzdavaca()); 
		
		insertKnjiga.setInt(7, knjiga.getId()); 
		
		insertKnjiga.executeUpdate(); 
		
		konekcijaNaBazu.commit(); 
		
		konekcijaNaBazu.setAutoCommit(true); 
		
		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu);  
		
	}
	
	public static void promijeniCasopis(Casopis casopsi) throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
		
		PreparedStatement insertCasopis = konekcijaNaBazu 
				.prepareStatement("UPDATE RAZVOJ.CASOPIS SET naziv = ?, godinaizdanja = ?, vrstapublikacije = ?, "
						+ "brojstranica = ?, mjesecizdanja = ?  WHERE ID = ?"); 
		
		insertCasopis.setString(1, casopsi.getNaziv()); 
		
		insertCasopis.setInt(2, casopsi.getGodinaIzdanja()); 
		
		insertCasopis.setInt(3, casopsi.getVrstaPublikacije().getBrojVrste()); 
		
		insertCasopis.setInt(4, casopsi.getBrojStranica()); 
		
		insertCasopis.setInt(5, casopsi.getMjesecIzdavanjaCasopisa()); 
		
		insertCasopis.setInt(6, casopsi.getId()); 
		
		insertCasopis.executeUpdate(); 
		
		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu);  
		
	}
	
	public static void promijeniClana(Clan clan) throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 
		
		PreparedStatement insertCasopis = konekcijaNaBazu 
				.prepareStatement("UPDATE RAZVOJ.CLAN SET oib = ?, ime = ?, prezime = ? WHERE ID = ?"); 
		
		insertCasopis.setString(1, clan.getOibClana()); 
		
		insertCasopis.setString(1, clan.getImeClana());
		
		insertCasopis.setString(1, clan.getPrezimeClana());

		insertCasopis.setInt(6, clan.getIdClana()); 
		
		insertCasopis.executeUpdate(); 
		
		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu);
		
	}
	
	public static void spremiPosudbuKnjige(Posudba<Knjiga> posudba) throws Exception { 
		
		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu();  
		
		String queryString = null; 
		
		queryString = "INSERT INTO RAZVOJ.POSUDBA_KNJIGA (clan, knjiga, datumPosudbe) "
				+ "VALUES (?, ?, ?)"; 
		
		PreparedStatement preparedStatement = konekcijaNaBazu .prepareStatement(queryString); 
		
		preparedStatement.setInt(1, posudba.getClan().getIdClana()); 
		
		preparedStatement.setInt(2, ((Knjiga) posudba.getPublikacija()).getId()); 
		
		preparedStatement.setDate(3, Date.valueOf(posudba.getDatumPosudbe().toLocalDate())); 
		
		preparedStatement.executeUpdate(); 
		
		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu);
		
	}
	
	public static void spremiPosudbuCasopisa(Posudba<Casopis> posudba) throws Exception { 
			
			Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu();  
			
			String queryString = null; 
			
			queryString = "INSERT INTO RAZVOJ.POSUDBA_CASOPISA (clan, casopis, datumPosudbe) "
					+ "VALUES (?, ?, ?)"; 
			
			PreparedStatement preparedStatement = konekcijaNaBazu .prepareStatement(queryString); 
			
			preparedStatement.setInt(1, posudba.getClan().getIdClana()); 
			
			preparedStatement.setInt(2, ((Casopis) posudba.getPublikacija()).getId()); 
			
			preparedStatement.setDate(3, Date.valueOf(posudba.getDatumPosudbe().toLocalDate())); 
			
			preparedStatement.executeUpdate(); 
			
			BazaPodataka.zatvoriKonekciju(konekcijaNaBazu);
			
		}
	
	public static List<Posudba<Knjiga>> dohvatiPosudbeKnjiga() throws Exception {

		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 

		List<Posudba<Knjiga>> listaPosudbi = new ArrayList<>();

		List<Knjiga> listaKnjiga = dohvatiKnjige();
		
		List<Clan> listaClanova = dohvatiClanove();
			
		String queryString = "SELECT * FROM RAZVOJ.POSUDBA_KNJIGA";
		
		PreparedStatement preparedStatement = konekcijaNaBazu.prepareStatement(queryString);
		
		ResultSet resultSet = preparedStatement.executeQuery();
			
		while (resultSet.next()) {

			int id, knjiga_id, clan_id;				
			Date datum;		
							
			id = resultSet.getInt("id");
			knjiga_id = resultSet.getInt("knjiga");
			clan_id = resultSet.getInt("clan");			
			datum = resultSet.getDate("datumposudbe");
			
			Clan clan = listaClanova.stream().filter(c->c.getIdClana() == clan_id).findFirst().get();
			
			Knjiga knjiga = listaKnjiga.stream().filter(c->c.getId() == knjiga_id).findFirst().get();			
									
			Posudba<Knjiga> posudba = new Posudba<>(id, clan, knjiga, LocalDateTime.of(datum.toLocalDate().getYear(), datum.toLocalDate().getMonth(), datum.toLocalDate().getDayOfMonth(),0,0));
			
			listaPosudbi.add(posudba);
		}

		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu);

		return listaPosudbi;
	}
	
	public static List<Posudba<Casopis>> dohvatiPosudbeCasopisa() throws Exception {

		Connection konekcijaNaBazu = BazaPodataka.spojiNaBazu(); 

		List<Posudba<Casopis>> listaPosudbi = new ArrayList<>();

		List<Casopis> listaCasopisa = dohvatiCasopise();
		
		List<Clan> listaClanova = dohvatiClanove();
			
		String queryString = "SELECT * FROM RAZVOJ.POSUDBA_CASOPISA";
		
		PreparedStatement preparedStatement = konekcijaNaBazu.prepareStatement(queryString);
		
		ResultSet resultSet = preparedStatement.executeQuery();
			
		while (resultSet.next()) {

			int id, casopis_id, clan_id;				
			Date datum;		
							
			id = resultSet.getInt("id");
			casopis_id = resultSet.getInt("casopis");
			clan_id = resultSet.getInt("clan");			
			datum = resultSet.getDate("datumposudbe");
			
			Clan clan = listaClanova.stream().filter(c->c.getIdClana() == clan_id).findFirst().get();
			
			Casopis casopis = listaCasopisa.stream().filter(c->c.getId() == casopis_id).findFirst().get();		
									
			Posudba<Casopis> posudba = new Posudba<>(id, clan, casopis, LocalDateTime.of(datum.toLocalDate().getYear(), datum.toLocalDate().getMonth(), datum.toLocalDate().getDayOfMonth(),0,0));
			
			listaPosudbi.add(posudba);
		}

		BazaPodataka.zatvoriKonekciju(konekcijaNaBazu);

		return listaPosudbi;
	}


	
}

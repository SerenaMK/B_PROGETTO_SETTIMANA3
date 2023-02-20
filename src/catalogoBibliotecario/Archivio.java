package catalogoBibliotecario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Archivio {
	
	static Logger log = LoggerFactory.getLogger(Archivio.class);
	static ArrayList<Pubblicazioni> archivio = new ArrayList<Pubblicazioni>();
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("B_PROGETTO_SETTIMANA3");

	public static void main(String[] args) {
		
		// Creo elementi vari
		Libri l1 = new Libri("978-1-78663-729-1", "Full Surrogacy Now: Feminism Against Family", 2019, 200, "Sophie Lewis", "Politica");
		Libri l2 = new Libri("978-0-425-27399-7", "Captive Prince", 2017, 140, "C. S. Pacat", "Fantasy");
		Libri l3 = new Libri("978-0-425-27399-8", "Prince's Gambit", 2018, 145, "C. S. Pacat", "Fantasy");
		Libri l4 = new Libri("978-0-425-27399-9", "Kings Rising", 2019, 150, "C. S. Pacat", "Fantasy");
		Libri l5 = new Libri("978-1-420-69420-6", "Libro a caso da rimuovere", 2020, 50, "Tizio Caio", "Thriller");
		Riviste r1 = new Riviste("978-3-16-148410-0", "Il Manifesto", 2022, 20, Periodicita.MENSILE);
		Riviste r2 = new Riviste("978-3-16-148410-1", "La Rivista", 2021, 15, Periodicita.SETTIMANALE);
		
		Utente u1 = new Utente("Mario", "Rossi", LocalDate.of(1994, 11, 29));
		Utente u2 = new Utente("Luigi", "Verdi", LocalDate.of(1990, 05, 20));
		Utente u3 = new Utente("Anna", "Bianchi", LocalDate.of(2000, 12, 8));
		
		Prestito pr1 = new Prestito(u1, l2, LocalDate.of(2022, 12, 18));
		Prestito pr2 = new Prestito(u1, l3, LocalDate.of(2023, 8, 15));
		Prestito pr3 = new Prestito(u2, l1, LocalDate.of(2023, 5, 30));
		Prestito pr4 = new Prestito(u3, r1, LocalDate.of(2023, 1, 10));
		
		lineBreak();
		
		// Aggiungo libri e riviste al DB
		addElemento(l1);
		addElemento(l2);
		addElemento(l3);
		addElemento(l4);
		addElemento(l5);
		addElemento(r1);
		addElemento(r2);
		addUtente(u1);
		addUtente(u2);
		addUtente(u3);
		addPrestito(pr1);
		addPrestito(pr2);
		addPrestito(pr3);
		addPrestito(pr4);
		
		lineBreak();
		
		// Cerco un elemento
		getElemento("978-0-425-27399-9");
		getElemento("978-3-16-148410-0");
		
		lineBreak();
		
		// Cancello un elemento
		deleteElemento("978-1-420-69420-6");
		
		lineBreak();
		
		// Trovo tutti i libri di un dato autore
		List<Libri> lista1 = getElementoByAutore("C. S. Pacat");
		for (Libri l : lista1) {
			log.info("Titolo: \"" + l.getTitolo() + "\"");
		}
		
		lineBreak();
		
		// Trovo tutti i libri il cui titolo contiene questa stringa
		List<Pubblicazioni> lista2 = getElementoByTitolo("Prince");
		for (Pubblicazioni p : lista2) {
			log.info("Titolo: \"" + p.getTitolo() + "\"");
		}
		
		lineBreak();
		
		// Trovo tutti i libri pubblicati in un dato anno
		List<Pubblicazioni> lista3 = getElementoByAnno(2019);
		for (Pubblicazioni p : lista3) {
			log.info("Titolo: \"" + p.getTitolo() + "\"");
		}
		
		lineBreak();
		
		// Trovo tutte le pubblicazioni attualmente in prestito ad un utente con un dato numero tessera
		List<Prestito> lista4 = getElementiPrestatiByTessera(1);
		for (Prestito p : lista4) {
			log.info("Titolo: \"" + p.getElementoPrestato().getTitolo() + "\"");
		}
		
		lineBreak();
		
		// Trovo tutti i prestiti scaduti e non ancora restituiti
		List<Prestito> lista5 = getPrestitiScadutiNonRestituiti();;
		for (Prestito p : lista5) {
			log.info("Titolo: \"" + p.getElementoPrestato().getTitolo() + "\"");
		}
		
		lineBreak();

	}
	
	
	
	
	
    // Aggiunta di un elemento del catalogo
	public static void addElemento(Pubblicazioni p) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			log.info("Pubblicazione \"" + p.getTitolo() + "\" aggiunta al DB.");
		} catch (Exception ec) {
			em.getTransaction().rollback();
			log.error(ec.getMessage());
		} finally {
			em.close();
		}
	}
	
	// Rimozione di un elemento del catalogo dato un codice ISBN
	private static void deleteElemento(String isbn) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Pubblicazioni p = em.find(Pubblicazioni.class, isbn);
			em.getTransaction().commit();
			log.info("Pubblicazione con codice ISBN " + isbn + " cancellata dal DB.");
		} catch (Exception ec) {
			em.getTransaction().rollback();
			log.error(ec.getMessage());
		} finally {
			em.close();
		}
	}
	
	// Ricerca per ISBN
	private static Pubblicazioni getElemento(String isbn) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Pubblicazioni p = em.find(Pubblicazioni.class, isbn);
			em.getTransaction().commit();
			log.info("Pubblicazione \"" + p.getTitolo() + "\" con codice ISBN " + isbn + " trovata nel DB.");
			return p;
		} finally {
			em.close();
		}
		
	}
	
	// Ricerca per anno pubblicazione
	private static List<Pubblicazioni> getElementoByAnno(int anno) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Query q = em.createQuery("SELECT p FROM Pubblicazioni p WHERE p.anno = :anno");
			q.setParameter("anno", anno);
			
			em.getTransaction().commit();
			log.info("Lista di libri pubblicati nell'anno " + anno + ":");
			return q.getResultList();
		} finally {
			em.close();
		}
		
	}
	
	// Ricerca per autore
	private static List<Libri> getElementoByAutore(String autore) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Query q = em.createQuery("SELECT l FROM Libri l WHERE l.autore = :autore");
			q.setParameter("autore", autore);
			
			em.getTransaction().commit();
			log.info("Lista di libri con autore \"" + autore + "\":");
			return q.getResultList();
		} finally {
			em.close();
		}
		
	}
	
	// Ricerca per titolo o parte di esso
	private static List<Pubblicazioni> getElementoByTitolo(String titolo) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Query q = em.createQuery("SELECT p FROM Pubblicazioni p WHERE p.titolo LIKE :titolo");
			q.setParameter("titolo", "%" + titolo + "%");
			
			em.getTransaction().commit();
			log.info("Lista di libri con \"" + titolo + "\" nel titolo:");
			return q.getResultList();
		} finally {
			em.close();
		}
		
	}
	
	// Ricerca degli elementi attualmente in prestito dato un numero di tessera utente
	private static List<Prestito> getElementiPrestatiByTessera(int tessera) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Query q = em.createQuery("SELECT p FROM Prestito p WHERE p.utente.id = :tessera");
			q.setParameter("tessera", tessera);
			
			em.getTransaction().commit();
			log.info("Lista di pubblicazioni attualmente in prestito all'utente con tessera numero " + tessera + ":");
			return q.getResultList();
		} finally {
			em.close();
		}
		
	}
	
	// Ricerca di tutti i prestiti scaduti e non ancora restituiti
	private static List<Prestito> getPrestitiScadutiNonRestituiti() {
		EntityManager em = emf.createEntityManager();
		LocalDate today = LocalDate.now();
		
		try {
			em.getTransaction().begin();
			Query q = em.createQuery("SELECT p FROM Prestito p WHERE p.restituzionePrevista < :data");
			q.setParameter("data", today);
			
			em.getTransaction().commit();
			log.info("Lista di pubblicazioni attualmente in prestito la cui data di restituzione è già passata:");
			return q.getResultList();
		} finally {
			em.close();
		}
		
	}
	
	
	
	
	
	////////////////////////////////////////////////
	
	//// METODI AGGIUNTIVI
	
	// Aggiugi utente
	public static void addUtente(Utente u) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
			log.info("Utente \"" + u.getNome() + " " + u.getCognome() + "\" aggiunto al DB.");
		} catch (Exception ec) {
			em.getTransaction().rollback();
			log.error(ec.getMessage());
		} finally {
			em.close();
		}
	}
	
	// Aggiungi prestito
	public static void addPrestito(Prestito p) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			log.info("Prestito della pubblicazione \"" + p.getElementoPrestato().getTitolo() + "\" aggiunto al DB.");
		} catch (Exception ec) {
			em.getTransaction().rollback();
			log.error(ec.getMessage());
		} finally {
			em.close();
		}
	}
	
	
	// Riga per leggibilità in console
	private static void lineBreak() {
		System.out.println("----------------------------------------------------------------------");
	}

}
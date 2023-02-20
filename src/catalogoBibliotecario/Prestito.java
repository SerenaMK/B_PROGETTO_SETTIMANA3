package catalogoBibliotecario;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name= "prestito")
public class Prestito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_prestito")
	private int idPrestito;
	@Column(nullable=false, columnDefinition = "DATE")
	private LocalDate inizioPrestito;
	@Column(nullable=false, columnDefinition = "DATE")
	private LocalDate restituzionePrevista;
	@Column(columnDefinition = "DATE")
	private LocalDate restituzioneEffettiva;
	
	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="utente")
	private Utente utente;
	
	@OneToOne
	@JoinColumn(name="elemento_prestato")
	private Pubblicazioni elementoPrestato;
	
	public Prestito() {
		super();
	}
	
	public Prestito(Utente utente, Pubblicazioni elementoPrestato, LocalDate inizioPrestito,
			LocalDate restituzioneEffettiva) {
		super();
		this.utente = utente;
		this.elementoPrestato = elementoPrestato;
		this.inizioPrestito = inizioPrestito;
		this.restituzionePrevista = inizioPrestito.plusDays(30);
		this.restituzioneEffettiva = restituzioneEffettiva;
	}
	
	public Prestito(Utente utente, Pubblicazioni elementoPrestato, LocalDate inizioPrestito) {
		super();
		this.utente = utente;
		this.elementoPrestato = elementoPrestato;
		this.inizioPrestito = inizioPrestito;
		this.restituzionePrevista = inizioPrestito.plusDays(30);
	}

	public Utente getUtente() {
		return utente;
	}

	public Pubblicazioni getElementoPrestato() {
		return elementoPrestato;
	}

	public LocalDate getInizioPrestito() {
		return inizioPrestito;
	}

	public LocalDate getRestituzionePrevista() {
		return restituzionePrevista;
	}

	public LocalDate getRestituzioneEffettiva() {
		return restituzioneEffettiva;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public void setElementoPrestato(Pubblicazioni elementoPrestato) {
		this.elementoPrestato = elementoPrestato;
	}

	public void setInizioPrestito(LocalDate inizioPrestito) {
		this.inizioPrestito = inizioPrestito;
	}

	public void setRestituzionePrevista(LocalDate restituzionePrevista) {
		this.restituzionePrevista = restituzionePrevista;
	}

	public void setRestituzioneEffettiva(LocalDate restituzioneEffettiva) {
		this.restituzioneEffettiva = restituzioneEffettiva;
	}

}

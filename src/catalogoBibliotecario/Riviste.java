package catalogoBibliotecario;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Riviste extends Pubblicazioni {
	
	@Column(nullable = false)
	private Periodicita periodicita;
	

	public Riviste() {
		super();
	}

	public Riviste(String isbn, String titolo, int anno, int pagine) {
		super(isbn, titolo, anno, pagine);
	}

	public Riviste(String isbn, String titolo, int anno, int pagine, Periodicita periodicita) {
		super(isbn, titolo, anno, pagine);
		this.periodicita = periodicita;
	}

	public Periodicita getPeriodicita() {
		return periodicita;
	}
	
	@Override
	public String toString() {
		return ( super.toString() +
				"\nPeriodicità: " + getPeriodicita() + "\n");
	}

}

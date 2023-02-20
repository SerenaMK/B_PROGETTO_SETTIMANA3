package catalogoBibliotecario;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Libri extends Pubblicazioni {
	
	@Column(nullable = false)
	private String autore;
	@Column(nullable = false)
	private String genere;
	
	public Libri() {
		super();
	}
	
	public Libri(String isbn, String titolo, int anno, int pagine, String autore, String genere) {
		super(isbn, titolo, anno, pagine);
		this.autore = autore;
		this.genere = genere;
	}

	public String getAutore() {
		return autore;
	}

	public String getGenere() {
		return genere;
	}
	
	@Override
	public String toString() {
		return ( super.toString() +
				"\nAutore: " + getISBN() +
				"\nGenere: " + getGenere() + "\n");
	}

}

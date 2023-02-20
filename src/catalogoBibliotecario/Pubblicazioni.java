package catalogoBibliotecario;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import model.Utente;

@Entity
@Table(name= "pubblicazioni")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pubblicazioni {
	
	@Id
	@Column(name="isbn")
	private String ISBN;
	@Column(nullable = false)
	private String titolo;
	@Column(nullable = false)
	private int anno;
	@Column(nullable = false)
	private int pagine;
	
	//bi-directional one-to-one association to Pubblicazioni
	@OneToOne(mappedBy="elementoPrestato")
	private Prestito prestito;
	
	public Pubblicazioni() {}
	
	public Pubblicazioni(String isbn, String titolo, int anno, int pagine) {
		super();
		ISBN = isbn;
		this.titolo = titolo;
		this.anno = anno;
		this.pagine = pagine;
	}

	public String getISBN() {
		return ISBN;
	}

	public String getTitolo() {
		return titolo;
	}

	public int getAnno() {
		return anno;
	}

	public int getPagine() {
		return pagine;
	}
	
	@Override
	public String toString() {
		return ("\nISBN: " + getISBN() +
				"\nTitolo: \"" + getTitolo() + "\"" +
				"\nAnno: " + getAnno() +
				"\nPagine : " + getPagine());
	}

}

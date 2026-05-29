/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package model;

/**
 * Rappresenta un film presente nel palinsesto del cinema.
 */
public class Film {

	private String titolo;
	private String genere;
	private String regista;
	private int anno;
	private int durata;
	private int etaMinima;

	/**
	 * Crea una nuova istanza della classe Film.
	 *
	 * @param titolo    titolo del film.
	 * @param genere    genere del film.
	 * @param regista   regista del film.
	 * @param anno      anno di uscita del film.
	 * @param durata    durata del film in minuti.
	 * @param etaMinima età minima consigliata per la visione.
	 */
	public Film(String titolo, String genere, String regista, int anno, int durata, int etaMinima) {

		this.titolo = titolo;
		this.genere = genere;
		this.regista = regista;
		this.anno = anno;
		this.durata = durata;
		this.etaMinima = etaMinima;

	}

	public String getTitolo() {
		return titolo;
	}

	public String getGenere() {
		return genere;
	}

	public String getRegista() {
		return regista;
	}

	public int getAnno() {
		return anno;
	}

	public int getDurata() {
		return durata;
	}

	public int getEtaMinima() {
		return etaMinima;
	}

}
/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package model;

/**
 * Rappresenta una prenotazione effettuata da un cliente per una specifica
 * proiezione.
 */
public class Prenotazione {

	private String codicePrenotazione;
	private Proiezione proiezione;
	private int numeroPostiPrenotati;
	private String utente;

	/**
	 * Crea una nuova istanza della classe Prenotazione.
	 *
	 * @param codicePrenotazione   codice univoco identificativo della prenotazione.
	 * @param proiezione           proiezione prenotata.
	 * @param numeroPostiPrenotati numero di posti prenotati.
	 * @param utente               utente che effettua l’operazione.
	 */
	public Prenotazione(String codicePrenotazione, Proiezione proiezione, int numeroPostiPrenotati, String utente) {

		this.codicePrenotazione = codicePrenotazione;
		this.proiezione = proiezione;
		this.numeroPostiPrenotati = numeroPostiPrenotati;
		this.utente = utente;

	}

	public String getCodicePrenotazione() {
		return codicePrenotazione;
	}

	public Proiezione getProiezione() {
		return proiezione;
	}

	public int getNumeroPostiPrenotati() {
		return numeroPostiPrenotati;
	}

	public String getUsername() {
		return utente;
	}

}
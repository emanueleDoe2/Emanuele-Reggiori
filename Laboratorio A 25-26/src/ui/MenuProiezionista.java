/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package ui;

import java.util.Scanner;
import java.time.LocalDateTime;

import model.Film;
import model.Proiezione;
import util.InputUtil;
import model.Utente;
import service.AuthService;
import service.ProiezioneService;

/**
 * Gestisce l’interfaccia testuale dedicata al ruolo proiezionista.
 */
public class MenuProiezionista {

	private Scanner scanner;
	private ProiezioneService proiezioneservice;

	/**
	 * Crea una nuova istanza della classe MenuProiezionista.
	 *
	 * @param scanner           scanner utilizzato per leggere l’input da terminale.
	 * @param utente            utente che effettua l’operazione.
	 * @param authservice       servizio di autenticazione.
	 * @param proiezioneservice servizio delle proiezioni.
	 */
	public MenuProiezionista(Scanner scanner, Utente utente, AuthService authservice,
			ProiezioneService proiezioneservice) {

		this.scanner = scanner;
		this.proiezioneservice = proiezioneservice;
	}

	/**
	 * Avvia il menu.
	 */
	public void start() {

		int scelta;

		do {

			stampaMenuProiezionista();

			scelta = InputUtil.leggiInteroObbligatorio(scanner, "Scelta: ", "Scelta non valida. Inserisci un numero.");

			if (scelta == 1) {

				inserisciProiezione();

			} else if (scelta == 2) {

				modificaDataProiezione();

			} else if (scelta == 3) {

				eliminaProiezione();

			} else if (scelta == 0) {

				System.out.println("Logout effettuato");

			} else {

				System.out.println("Scelta non valida");
			}

		} while (scelta != 0);
	}

	/**
	 * Stampa le opzioni del menu proiezionista.
	 */
	public void stampaMenuProiezionista() {

		System.out.println();
		System.out.println("===== MENU PROIEZIONISTA =====");
		System.out.println("1. Inserisci film/proiezione");
		System.out.println("2. Modifica data proiezione");
		System.out.println("3. Elimina proiezione");
		System.out.println("0. Logout");
	}

	/**
	 * Gestisce l’inserimento di un film e di una o più proiezioni associate.
	 */
	private void inserisciProiezione() {

		System.out.println("===== INSERISCI FILM =====");

		String titolo = InputUtil.leggiStringaObbligatoria(scanner, "Titolo: ", "Il titolo è obbligatorio");

		String genere = InputUtil.leggiStringaObbligatoria(scanner, "Genere: ", "Il genere è obbligatorio");

		String regista = InputUtil.leggiStringaObbligatoria(scanner, "Regista: ", "Il regista è obbligatorio");

		int anno = InputUtil.leggiInteroObbligatorio(scanner, "Anno: ", "Anno non valido");

		int durata = InputUtil.leggiInteroObbligatorio(scanner, "Durata in minuti: ", "Durata non valida");

		int etaMinima = InputUtil.leggiInteroObbligatorio(scanner, "Età minima: ", "Età minima non valida");

		Film film = new Film(titolo, genere, regista, anno, durata, etaMinima);

		int scelta;

		do {

			System.out.println("===== INSERISCI PROIEZIONE =====");

			LocalDateTime dataOra = InputUtil.leggiDataOraObbligatoria(scanner,
					"Data e ora proiezione (YYYY-MM-DD HH:mm:ss): ");

			Double costoBiglietto = InputUtil.leggiDoubleObbligatorio(scanner, "Costo biglietto: ", "Costo non valido");

			Proiezione proiezione = new Proiezione(film, dataOra, costoBiglietto);

			boolean inserita = proiezioneservice.aggiungiProiezione(proiezione);

			if (inserita) {
				System.out.println("Proiezione inserita correttamente");
			} else {
				System.out.println("Inserimento fallito: proiezione sovrapposta o non valida");
			}

			scelta = InputUtil.leggiInteroObbligatorio(scanner,
					"Vuoi inserire un'altra proiezione per questo film? 1 = sì, 0 = no: ", "Scelta non valida");

		} while (scelta == 1);
	}

	/**
	 * Elimina una proiezione se è passata e non ha prenotazioni.
	 */
	private void eliminaProiezione() {

		UICercaProiezioni uiCercaProiezioni = new UICercaProiezioni(proiezioneservice, scanner);

		Proiezione proiezioneSelezionata = uiCercaProiezioni.RicercaProiezioni();

		if (proiezioneSelezionata == null) {
			return;
		}

		System.out.println("Confermi l'eliminazione della proiezione?");
		System.out.println("1. Sì");
		System.out.println("0. No");
		int conferma = InputUtil.leggiInteroObbligatorio(scanner, "Scelta: ",
				"Scelta non valida. Inserisci un numero.");

		if (conferma == 1) {

			boolean eliminata = proiezioneservice.eliminaProiezione(proiezioneSelezionata);

			if (eliminata) {

				System.out.println("Proiezione eliminata correttamente");

			} else {

				System.out.println("Eliminazione non consentita: proiezione già passata o con prenotazioni");
			}

		} else {

			System.out.println("Eliminazione annullata");
		}
	}

	/**
	 * Modifica la data e l’ora di una proiezione se l’operazione è consentita.
	 */
	private void modificaDataProiezione() {

		UICercaProiezioni uiCercaProiezioni = new UICercaProiezioni(proiezioneservice, scanner);

		Proiezione proiezioneSelezionata = uiCercaProiezioni.RicercaProiezioni();

		if (proiezioneSelezionata == null) {
			return;
		}

		LocalDateTime nuovaDataOra = InputUtil.leggiDataOraObbligatoria(scanner,
				"Nuova data e ora (YYYY-MM-DD HH:mm:ss): ");

		boolean modificata = proiezioneservice.modificaDataProiezione(proiezioneSelezionata, nuovaDataOra);

		if (modificata) {

			System.out.println("Proiezione modificata correttamente");

		} else {

			System.out.println("Modifica non consentita: proiezione già passata, con prenotazioni o data occupata");
		}
	}
}
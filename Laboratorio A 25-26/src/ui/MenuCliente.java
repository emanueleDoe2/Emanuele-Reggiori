/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package ui;

import java.util.Scanner;
import java.util.ArrayList;

import model.Proiezione;
import model.Utente;
import model.Prenotazione;
import service.ProiezioneService;
import service.PrenotazioneService;
import util.InputUtil;

public class MenuCliente {

	private Scanner scanner;
	private Utente utente;
	private ProiezioneService proiezioneservice;
	private PrenotazioneService prenotazioneservice;

	public MenuCliente(Scanner scanner, Utente utente, ProiezioneService proiezioneservice,
			PrenotazioneService prenotazioneservice) {

		this.scanner = scanner;
		this.utente = utente;
		this.proiezioneservice = proiezioneservice;
		this.prenotazioneservice = prenotazioneservice;

	}

	public void start() {

		int scelta;

		do {

			stampaMenuCliente();

			scelta = InputUtil.leggiInteroObbligatorio(scanner, "Scelta: ", "Scelta non valida. Inserisci un numero.");

			if (scelta == 1) {

				cercaEPrenotaProiezione();

			} else if (scelta == 2) {

				gestisciPrenotazioniUtente();

			} else if (scelta == 0) {

				System.out.println("Logout effettuato");

			} else {

				System.out.println("Scelta non valida");
			}

		} while (scelta != 0);
	}

	private void cercaEPrenotaProiezione() {

		UICercaProiezioni uiCercaProiezioni = new UICercaProiezioni(proiezioneservice, scanner);

		Proiezione proiezioneSelezionata = uiCercaProiezioni.RicercaProiezioni();

		if (proiezioneSelezionata == null) {
			return;
		}

		int scelta;

		do {

			System.out.println("==== PRENOTAZIONE ====");
			System.out.println("1. Crea una prenotazione per questa proiezione");
			System.out.println("0. Torna indietro");
			scelta = InputUtil.leggiInteroObbligatorio(scanner, "Scelta: ", "Scelta non valida. Inserisci un numero.");

			if (scelta == 1) {

				creaPrenotazione(proiezioneSelezionata);

			} else if (scelta == 0) {

				System.out.println("Ritorno al menu cliente");

			} else {

				System.out.println("Scelta non valida");
			}

		} while (scelta != 0 && scelta != 1);
	}

	private void creaPrenotazione(Proiezione proiezioneSelezionata) {

		int numeroPosti;

		while (true) {

			numeroPosti = InputUtil.leggiInteroObbligatorio(scanner, "Numero posti da prenotare: ",
					"Scelta non valida. Inserisci un numero.");

			if (numeroPosti <= 0) {

				System.out.println("Il numero posti deve essere maggiore di 0");

			} else {

				break;
			}
		}

		boolean prenotazioneCreata = prenotazioneservice.creaPrenotazione(utente, proiezioneSelezionata, numeroPosti);

		if (prenotazioneCreata) {

			System.out.println("Prenotazione creata con successo");

		} else {

			System.out.println("Prenotazione fallita - posti insufficienti");
		}
	}

	private void gestisciPrenotazioniUtente() {

		ArrayList<Prenotazione> prenotazioniUtente = prenotazioneservice.getPrenotazioniUtente(utente);

		if (prenotazioniUtente.isEmpty()) {

			System.out.println("Non hai prenotazioni");
			return;
		}

		stampaPrenotazioniUtente(prenotazioniUtente);

		Prenotazione prenotazioneSelezionata = selezionaPrenotazione(prenotazioniUtente);

		if (prenotazioneSelezionata == null) {
			return;
		}

		visualizzaPrenotazione(prenotazioneSelezionata);

		int scelta;

		do {

			System.out.println("===== GESTIONE PRENOTAZIONE =====");
			System.out.println("1. Modifica prenotazione");
			System.out.println("2. Cancella prenotazione");
			System.out.println("0. Torna indietro");
			scelta = InputUtil.leggiInteroObbligatorio(scanner, "Scelta: ", "Scelta non valida. Inserisci un numero.");

			if (scelta == 1) {

				ArrayList<Proiezione> alternative = prenotazioneservice
						.getProiezioniAlternative(prenotazioneSelezionata);

				if (alternative.isEmpty()) {

					System.out.println("Non ci sono proiezioni alternative disponibili");

				} else {

					System.out.println("===== PROIEZIONI ALTERNATIVE =====");

					for (int i = 0; i < alternative.size(); i++) {

						Proiezione proiezione = alternative.get(i);

						System.out.println((i + 1) + ". " + proiezione.getFilm().getTitolo() + " - "
								+ proiezione.getDataOra() + " - " + proiezione.getCostoBiglietto() + "€");
					}

					while (true) {

						int sceltaProiezione = InputUtil.leggiInteroObbligatorio(scanner,
								"Seleziona nuova proiezione, oppure 0 per tornare indietro: ",
								"Scelta non valida. Inserisci un numero.");

						if (sceltaProiezione == 0) {
							break;
						}

						if (sceltaProiezione >= 1 && sceltaProiezione <= alternative.size()) {

							Proiezione nuovaProiezione = alternative.get(sceltaProiezione - 1);

							boolean modificata = prenotazioneservice.modificaPrenotazione(prenotazioneSelezionata,
									nuovaProiezione);

							if (modificata) {

								System.out.println("Prenotazione modificata correttamente");

							} else {

								System.out.println(
										"Modifica non consentita: posti insufficienti o proiezione non valida");
							}

							break;

						} else {

							System.out.println("Scelta non valida");
						}
					}
				}
			} else if (scelta == 2) {

				boolean eliminata = prenotazioneservice.eliminaPrenotazione(prenotazioneSelezionata);

				if (eliminata) {

					System.out.println("Prenotazione cancellata correttamente");

				} else {

					System.out.println(
							"Cancellazione non consentita: la data di proiezione deve essere precedente la data odierna");
				}

			} else if (scelta == 0) {

				System.out.println("Ritorno al menu cliente");

			} else {

				System.out.println("Scelta non valida");
			}

		} while (scelta != 0 && scelta != 2);
	}

	private void stampaPrenotazioniUtente(ArrayList<Prenotazione> prenotazioniUtente) {

		System.out.println("===== LE TUE PRENOTAZIONI =====");

		for (int i = 0; i < prenotazioniUtente.size(); i++) {

			Prenotazione prenotazione = prenotazioniUtente.get(i);

			System.out.println((i + 1) + ". " + prenotazione.getCodicePrenotazione() + " - "
					+ prenotazione.getProiezione().getFilm().getTitolo() + " - "
					+ prenotazione.getProiezione().getDataOra() + " - posti: "
					+ prenotazione.getNumeroPostiPrenotati());
		}
	}

	private Prenotazione selezionaPrenotazione(ArrayList<Prenotazione> prenotazioniUtente) {

		while (true) {

			System.out.print("Seleziona una prenotazione, oppure 0 per tornare indietro: ");

			int sceltaPrenotazione = InputUtil.leggiInteroObbligatorio(scanner, "Scelta: ",
					"Scelta non valida. Inserisci un numero.");

			if (sceltaPrenotazione == 0) {
				return null;
			}

			if (sceltaPrenotazione >= 1 && sceltaPrenotazione <= prenotazioniUtente.size()) {

				return prenotazioniUtente.get(sceltaPrenotazione - 1);
			}

			System.out.println("Scelta non valida");
		}
	}

	public void stampaMenuCliente() {

		System.out.println();
		System.out.println("===== MENU CLIENTE =====");
		System.out.println("1. Cerca e prenota proiezioni");
		System.out.println("2. Visualizza e modifica/cancella le tue prenotazioni");
		System.out.println("0. Logout");

	}

	private void visualizzaPrenotazione(Prenotazione prenotazione) {

		System.out.println("===== DETTAGLIO PRENOTAZIONE =====");

		System.out.println("Codice: " + prenotazione.getCodicePrenotazione());
		System.out.println("Film: " + prenotazione.getProiezione().getFilm().getTitolo());
		System.out.println("Data e ora: " + prenotazione.getProiezione().getDataOra());
		System.out.println("Posti prenotati: " + prenotazione.getNumeroPostiPrenotati());
	}
}
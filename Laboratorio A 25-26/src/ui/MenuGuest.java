/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package ui;

import java.util.Scanner;

import service.AuthService;
import service.ProiezioneService;
import util.InputUtil;

/**
 * Gestisce l’interfaccia testuale disponibile per gli utenti non autenticati.
 */
public class MenuGuest {

	private AuthService authservice;
	private ProiezioneService proiezioneservice;

	/**
	 * Crea una nuova istanza della classe MenuGuest.
	 *
	 * @param authservice       servizio di autenticazione.
	 * @param proiezioneservice servizio delle proiezioni.
	 */
	public MenuGuest(AuthService authservice, ProiezioneService proiezioneservice) {
		this.authservice = authservice;
		this.proiezioneservice = proiezioneservice;
	}

	Scanner scanner = new Scanner(System.in);

	/**
	 * Avvia il menu.
	 */
	public void start() {

		int scelta;

		do {

			stampaMenuGuest();

			scelta = InputUtil.leggiInteroObbligatorio(scanner, "Scelta: ", "Scelta non valida. Inserisci un numero.");

			if (scelta == 1) {

				UICercaProiezioni uiCercaProiezioni = new UICercaProiezioni(proiezioneservice, scanner);

				uiCercaProiezioni.RicercaProiezioni();

			} else if (scelta == 2) {

				registrazione();

			} else if (scelta == 0) {

				System.out.println("Ritorno al menu principale");

			} else {

				System.out.println("Scelta non valida");

			}

		} while (scelta != 0);

	}

	/**
	 * Stampa le opzioni del menu guest.
	 */
	public void stampaMenuGuest() {

		System.out.println();
		System.out.println("===== MENU GUEST =====");
		System.out.println("1. Cerca proiezioni");
		System.out.println("2. Registrati come cliente");
		System.out.println("0. Torna al menu principale");

	}

	/**
	 * Avvia la procedura di registrazione cliente.
	 */
	public void registrazione() {

		UIRegistrazione menuRegistrazione = new UIRegistrazione(scanner, authservice);

		menuRegistrazione.start();

	}

}
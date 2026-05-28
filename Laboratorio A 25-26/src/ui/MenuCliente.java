package ui;

import java.util.Scanner;
import java.util.ArrayList;

import model.Proiezione;
import model.Utente;
import model.Prenotazione;
import service.ProiezioneService;
import service.PrenotazioneService;

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

	        System.out.print("Scelta: ");
	        scelta = scanner.nextInt();
	        scanner.nextLine();

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

	    UICercaProiezioni uiCercaProiezioni =
	            new UICercaProiezioni(proiezioneservice, scanner);

	    Proiezione proiezioneSelezionata =
	            uiCercaProiezioni.RicercaProiezioni();

	    if (proiezioneSelezionata == null) {
	        return;
	    }

	    int scelta;

	    do {

	        System.out.println("==== PRENOTAZIONE ====");
	        System.out.println("1. Crea una prenotazione per questa proiezione");
	        System.out.println("0. Torna indietro");
	        System.out.print("Scelta: ");

	        scelta = scanner.nextInt();
	        scanner.nextLine();

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

	        System.out.print("Numero posti da prenotare: ");

	        numeroPosti = scanner.nextInt();
	        scanner.nextLine();

	        if (numeroPosti <= 0) {

	            System.out.println("Il numero posti deve essere maggiore di 0");

	        } else {

	            break;
	        }
	    }

	    boolean prenotazioneCreata =
	            prenotazioneservice.creaPrenotazione(
	                    utente,
	                    proiezioneSelezionata,
	                    numeroPosti
	            );

	    if (prenotazioneCreata) {

	        System.out.println("Prenotazione creata con successo");

	    } else {

	        System.out.println("Prenotazione fallita - posti insufficienti");
	    }
	}
	
	
	private void gestisciPrenotazioniUtente() {

	    ArrayList<Prenotazione> prenotazioniUtente =
	            prenotazioneservice.getPrenotazioniUtente(utente);

	    if (prenotazioniUtente.isEmpty()) {

	        System.out.println("Non hai prenotazioni");
	        return;
	    }

	    stampaPrenotazioniUtente(prenotazioniUtente);

	    Prenotazione prenotazioneSelezionata =
	            selezionaPrenotazione(prenotazioniUtente);

	    if (prenotazioneSelezionata != null) {

	        visualizzaPrenotazione(prenotazioneSelezionata);
	    }
	}
	
	private void stampaPrenotazioniUtente(ArrayList<Prenotazione> prenotazioniUtente) {

	    System.out.println("===== LE TUE PRENOTAZIONI =====");

	    for (int i = 0; i < prenotazioniUtente.size(); i++) {

	        Prenotazione prenotazione = prenotazioniUtente.get(i);

	        System.out.println(
	                (i + 1) + ". "
	                + prenotazione.getCodicePrenotazione()
	                + " - "
	                + prenotazione.getProiezione().getFilm().getTitolo()
	                + " - "
	                + prenotazione.getProiezione().getDataOra()
	                + " - posti: "
	                + prenotazione.getNumeroPostiPrenotati()
	        );
	    }
	}
	
	private Prenotazione selezionaPrenotazione(ArrayList<Prenotazione> prenotazioniUtente) {

	    while (true) {

	        System.out.print("Seleziona una prenotazione, oppure 0 per tornare indietro: ");

	        int sceltaPrenotazione = scanner.nextInt();
	        scanner.nextLine();

	        if (sceltaPrenotazione == 0) {
	            return null;
	        }

	        if (sceltaPrenotazione >= 1
	                && sceltaPrenotazione <= prenotazioniUtente.size()) {

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
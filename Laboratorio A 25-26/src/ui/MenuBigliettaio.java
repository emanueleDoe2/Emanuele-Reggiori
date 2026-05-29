/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package ui;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;


import util.InputUtil;
import model.Utente;
import model.Prenotazione;
import service.PrenotazioneService;
import service.AuthService;

public class MenuBigliettaio {

    private Scanner scanner;
    private PrenotazioneService prenotazioneservice;
    private AuthService authservice;

    public MenuBigliettaio(Scanner scanner,
                           Utente utente,
                           PrenotazioneService prenotazioneservice,
                           AuthService authservice) {

        this.scanner = scanner;
        this.prenotazioneservice = prenotazioneservice;
        this.authservice = authservice;
    }

    public void start() {

        int scelta;

        do {

            stampaMenuBigliettaio();

            scelta = InputUtil.leggiInteroObbligatorio(
                    scanner,
                    "Scelta: ",
                    "Scelta non valida. Inserisci un numero."
            );

            if (scelta == 1) {

            	visualizzaPrenotazioniOggi();

            } else if (scelta == 2) {

            	cercaPrenotazioni();


            } else if (scelta == 0) {

                System.out.println("Logout effettuato");

            } else {

                System.out.println("Scelta non valida");
            }

        } while (scelta != 0);
    }
    
    public void stampaMenuBigliettaio() {

        System.out.println();
        System.out.println("===== MENU BIGLIETTAIO =====");
        System.out.println("1. Visualizza prenotazioni di oggi");
        System.out.println("2. Cerca prenotazione");
        System.out.println("0. Logout");
    }
    
    private void visualizzaPrenotazione(Prenotazione prenotazione) {

        System.out.println("===== DETTAGLIO PRENOTAZIONE =====");

        
        System.out.println("Codice: " + prenotazione.getCodicePrenotazione());
        System.out.println("Username cliente: " + prenotazione.getUsername());
        System.out.println("Film: " + prenotazione.getProiezione().getFilm().getTitolo());
        System.out.println("Data e ora: " + prenotazione.getProiezione().getDataOra());
        System.out.println("Posti prenotati: " + prenotazione.getNumeroPostiPrenotati());
    }
    
    private Prenotazione selezionaPrenotazione(ArrayList<Prenotazione> prenotazioni) {

        while (true) {

            System.out.print("Seleziona una prenotazione, oppure 0 per tornare indietro: ");

            int scelta = InputUtil.leggiInteroObbligatorio(
	                scanner,
	                "Scelta: ",
	                "Scelta non valida. Inserisci un numero."
	        );

            if (scelta == 0) {
                return null;
            }

            if (scelta >= 1 && scelta <= prenotazioni.size()) {
                return prenotazioni.get(scelta - 1);
            }

            System.out.println("Scelta non valida");
        }
    }
    
    private void stampaPrenotazioni(ArrayList<Prenotazione> prenotazioni) {

        System.out.println("===== RISULTATI RICERCA =====");

        if (prenotazioni.isEmpty()) {
            System.out.println("Nessuna prenotazione trovata.");
            return;
        }

        for (int i = 0; i < prenotazioni.size(); i++) {

            Prenotazione prenotazione = prenotazioni.get(i);

            Utente utente = authservice.trovaUtentePerUsername(prenotazione.getUsername());

            double costoUnitario = prenotazione.getProiezione().getCostoBiglietto();
            double totale = costoUnitario * prenotazione.getNumeroPostiPrenotati();

            System.out.println((i + 1) + ". Codice prenotazione: " 
                    + prenotazione.getCodicePrenotazione());

            if (utente != null) {
                System.out.println("Cliente: " 
                        + utente.getNome() + " " + utente.getCognome());
            } else {
                System.out.println("Cliente: " + prenotazione.getUsername());
            }

            System.out.println("Film: " 
                    + prenotazione.getProiezione().getFilm().getTitolo());

            System.out.println("Data e ora: " 
                    + prenotazione.getProiezione().getDataOra());

            System.out.println("Numero biglietti: " 
                    + prenotazione.getNumeroPostiPrenotati());

            System.out.println("Costo unitario: " 
                    + costoUnitario + " euro");

            System.out.println("Costo totale: " 
                    + totale + " euro");

            System.out.println("--------------------------------");
        }
    }
    
    private void cercaPrenotazioni() {

        System.out.println("===== CERCA PRENOTAZIONI =====");

        System.out.print("Codice prenotazione: ");
        String codicePrenotazione = scanner.nextLine().trim();

        if (codicePrenotazione.isEmpty()) {
            codicePrenotazione = null;
        }

        System.out.print("Nome cliente: ");
        String nomeCliente = scanner.nextLine().trim();

        if (nomeCliente.isEmpty()) {
            nomeCliente = null;
        }

        System.out.print("Cognome cliente: ");
        String cognomeCliente = scanner.nextLine().trim();

        if (cognomeCliente.isEmpty()) {
            cognomeCliente = null;
        }

        System.out.print("Titolo film: ");
        String titoloFilm = scanner.nextLine().trim();

        if (titoloFilm.isEmpty()) {
            titoloFilm = null;
        }

        LocalDate dataDa =
                InputUtil.leggiDataFacoltativa(
                        scanner,
                        "Data da (YYYY-MM-DD): "
                );

        LocalDate dataA =
                InputUtil.leggiDataFacoltativa(
                        scanner,
                        "Data a (YYYY-MM-DD): "
                );

        ArrayList<Prenotazione> risultati =
                prenotazioneservice.cercaPrenotazioni(
                        codicePrenotazione,
                        nomeCliente,
                        cognomeCliente,
                        titoloFilm,
                        dataDa,
                        dataA
                );

        if (risultati.isEmpty()) {

            System.out.println("Nessuna prenotazione trovata");
            return;
        }

        stampaPrenotazioni(risultati);

        Prenotazione prenotazioneSelezionata =
                selezionaPrenotazione(risultati);

        if (prenotazioneSelezionata != null) {
            visualizzaPrenotazione(prenotazioneSelezionata);
        }
    }
    
    private void visualizzaPrenotazioniOggi() {

        ArrayList<Prenotazione> prenotazioniOggi =
                prenotazioneservice.getPrenotazioniOggi();

        if (prenotazioniOggi.isEmpty()) {

            System.out.println("Non ci sono prenotazioni per oggi");
            return;
        }

        stampaPrenotazioni(prenotazioniOggi);

        Prenotazione prenotazioneSelezionata =
                selezionaPrenotazione(prenotazioniOggi);

        if (prenotazioneSelezionata != null) {
            visualizzaPrenotazione(prenotazioneSelezionata);
        }
    }
}
/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package ui;

import java.util.Scanner;
import java.util.ArrayList;

import service.AuthService;
import service.ProiezioneService;
import service.PrenotazioneService;

import model.Utente;
import model.Prenotazione;
import model.Proiezione;
import model.Ruolo;
import util.InputUtil;

public class MenuPrincipale {

    private ArrayList<Utente> utenti;
    private ArrayList<Proiezione> proiezioni;
    private ArrayList<Prenotazione> prenotazioni;
    private AuthService authservice;
    private ProiezioneService proiezioneservice;
    private PrenotazioneService prenotazioneservice;

    Scanner scanner = new Scanner(System.in);

    public MenuPrincipale(ArrayList<Utente> utenti,
                          ArrayList<Proiezione> proiezioni,
                          ArrayList<Prenotazione> prenotazioni,
                          AuthService authservice,
                          ProiezioneService proiezioneservice,
                          PrenotazioneService prenotazioneservice) {

        this.utenti = utenti;
        this.proiezioni = proiezioni;
        this.prenotazioni = prenotazioni;
        this.authservice = authservice;
        this.proiezioneservice = proiezioneservice;
        this.prenotazioneservice = prenotazioneservice;
    }

    public void start() {

        int scelta;

        do {
            stampaMenu();

            scelta = InputUtil.leggiInteroObbligatorio(
                    scanner,
                    "Scelta: ",
                    "Scelta non valida. Inserisci un numero."
            );

            if (scelta == 1) {
                login();

            } else if (scelta == 2) {
                MenuGuest menuGuest = new MenuGuest(authservice, proiezioneservice);
                menuGuest.start();

            } else if (scelta == 3) {
                registrazione();

            } else if (scelta == 0) {
                System.out.println("Chiusura applicazione");

            } else {
                System.out.println("Scelta non valida");
            }

        } while (scelta != 0);
    }

    public void stampaMenu() {
        System.out.println("===== CINEMAX =====");
        System.out.println("1. Login");
        System.out.println("2. Accesso guest");
        System.out.println("3. Registrazione");
        System.out.println("0. Esci");
    }

    public void login() {

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Utente utente = authservice.login(username, password);

        if (utente == null) {

            System.out.println("Username o password errati");

        } else {

            System.out.println("Login effettuato");
            System.out.println("Benvenuto " + utente.getNome());
            
            Ruolo ruolo = utente.getRuolo();

            if (ruolo == Ruolo.CLIENTE) {

                MenuCliente menuCliente =
                        new MenuCliente(
                                scanner,
                                utente,
                                proiezioneservice,
                                prenotazioneservice
                        );

                menuCliente.start();

            } else if (ruolo == Ruolo.PROIEZIONISTA) {

                MenuProiezionista menuProiezionista =
                        new MenuProiezionista(
                                scanner,
                                utente,
                                authservice,
                                proiezioneservice
                        );

                menuProiezionista.start();

            } else if (ruolo == Ruolo.BIGLIETTAIO) {

                MenuBigliettaio menuBigliettaio =
                        new MenuBigliettaio(
                                scanner,
                                utente,
                                prenotazioneservice,
                                authservice
                        );

                menuBigliettaio.start();
                
            } else {

                System.out.println("Ruolo utente non valido");
            }
        }
    }

    public void registrazione() {
    	
    	UIRegistrazione menuRegistrazione =
    			new UIRegistrazione(scanner, authservice);
    	
    	menuRegistrazione.start();
        
        
    }
}
package ui;

import java.util.Scanner;
import java.util.ArrayList;

import service.AuthService;
import service.ProiezioneService;

import model.Utente;
import model.Prenotazione;
import model.Proiezione;

public class MenuPrincipale {

    private ArrayList<Utente> utenti;
    private ArrayList<Proiezione> proiezioni;
    private ArrayList<Prenotazione> prenotazioni;
    private AuthService authservice;
    private ProiezioneService proiezioneservice;

    Scanner scanner = new Scanner(System.in);

    public MenuPrincipale(ArrayList<Utente> utenti,
                          ArrayList<Proiezione> proiezioni,
                          ArrayList<Prenotazione> prenotazioni,
                          AuthService authservice,
                          ProiezioneService proiezioneservice) {

        this.utenti = utenti;
        this.proiezioni = proiezioni;
        this.prenotazioni = prenotazioni;
        this.authservice = authservice;
        this.proiezioneservice = proiezioneservice;
    }

    public void start() {

        int scelta;

        do {
            stampaMenu();

            System.out.print("Scelta: ");
            scelta = scanner.nextInt();
            scanner.nextLine();

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
        }
    }

    public void registrazione() {
    	
    	MenuRegistrazione menuRegistrazione =
    			new MenuRegistrazione(scanner, authservice);
    	
    	menuRegistrazione.start();
        
        
    }
}
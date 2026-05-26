package ui;

import java.util.Scanner;
import java.util.ArrayList;

import service.AuthService;

import model.Utente;
import model.Prenotazione;
import model.Proiezione;

public class MenuPrincipale {

    private ArrayList<Utente> utenti;
    private ArrayList<Proiezione> proiezioni;
    private ArrayList<Prenotazione> prenotazioni;
    private AuthService authservice;

    Scanner scanner = new Scanner(System.in);

    public MenuPrincipale(ArrayList<Utente> utenti,
                          ArrayList<Proiezione> proiezioni,
                          ArrayList<Prenotazione> prenotazioni,
                          AuthService authservice) {

        this.utenti = utenti;
        this.proiezioni = proiezioni;
        this.prenotazioni = prenotazioni;
        this.authservice = authservice;
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
                MenuGuest menuGuest = new MenuGuest(authservice);
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

        System.out.println("===== REGISTRAZIONE CLIENTE =====");

        String nome = "";

        while (nome.isBlank()) {

            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();

            if (nome.isBlank()) {
                System.out.println("Il nome è obbligatorio");
            }
        }

        String cognome = "";

        while (cognome.isBlank()) {

            System.out.print("Cognome: ");
            cognome = scanner.nextLine().trim();

            if (cognome.isBlank()) {
                System.out.println("Il cognome è obbligatorio");
            }
        }

        String username = "";

        while (username.isBlank()) {

            System.out.print("Username: ");
            username = scanner.nextLine().trim();

            if (username.isBlank()) {
                System.out.println("Lo username è obbligatorio");
            }
        }

        String password = "";

        while (password.isBlank()) {

            System.out.print("Password: ");
            password = scanner.nextLine().trim();

            if (password.isBlank()) {
                System.out.println("La password è obbligatoria");
            }
        }

        System.out.println("Data di nascita facoltativa");
        System.out.print("Formato YYYY-MM-DD: ");

        String dataNascita = scanner.nextLine().trim();

        if (dataNascita.isEmpty()) {
            dataNascita = null;
        }

        String domicilio = "";

        while (domicilio.isBlank()) {

            System.out.print("Domicilio: ");
            domicilio = scanner.nextLine().trim();

            if (domicilio.isBlank()) {
                System.out.println("Il domicilio è obbligatorio");
            }
        }

        boolean registrato = authservice.registraCliente(
                nome,
                cognome,
                username,
                password,
                dataNascita,
                domicilio
        );

        if (registrato) {

            System.out.println("Registrazione completata");

        } else {

            System.out.println("Registrazione fallita - username già esistente");

        }
    }
}
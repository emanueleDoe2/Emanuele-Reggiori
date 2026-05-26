package ui;

import java.util.Scanner;

import service.AuthService;

public class MenuGuest {

    private AuthService authservice;

    public MenuGuest(AuthService authservice) {
        this.authservice = authservice;
    }
	
	Scanner scanner = new Scanner(System.in);
    

    public void start() {

        int scelta;

        do {

            stampaMenuGuest();

            System.out.print("Scelta: ");
            scelta = scanner.nextInt();
            scanner.nextLine();

            if (scelta == 1) {

                cercaProiezioni();

            } else if (scelta == 2) {

                registrazione();

            } else if (scelta == 0) {

                System.out.println("Ritorno al menu principale");

            } else {

                System.out.println("Scelta non valida");

            }

        } while (scelta != 0);

    }

    public void stampaMenuGuest() {

        System.out.println();
        System.out.println("===== MENU GUEST =====");
        System.out.println("1. Cerca proiezioni");
        System.out.println("2. Registrati come cliente");
        System.out.println("0. Torna al menu principale");

    }

    public void cercaProiezioni() {

        //TODO
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
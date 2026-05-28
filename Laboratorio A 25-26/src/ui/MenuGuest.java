package ui;

import java.util.Scanner;

import service.AuthService;
import service.ProiezioneService;

public class MenuGuest {

    private AuthService authservice;
    private ProiezioneService proiezioneservice;

    public MenuGuest(AuthService authservice, ProiezioneService proiezioneservice) {
        this.authservice = authservice;
        this.proiezioneservice = proiezioneservice;
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
          
            	UICercaProiezioni uiCercaProiezioni =
                        new UICercaProiezioni(proiezioneservice, scanner);

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

    public void stampaMenuGuest() {

        System.out.println();
        System.out.println("===== MENU GUEST =====");
        System.out.println("1. Cerca proiezioni");
        System.out.println("2. Registrati come cliente");
        System.out.println("0. Torna al menu principale");

    }

    

    public void registrazione() {
        	
        UIRegistrazione menuRegistrazione =
        		new UIRegistrazione(scanner, authservice);
        	
        menuRegistrazione.start();
                
        }

    
    
}
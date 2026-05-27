package ui;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

import util.InputUtil;
import model.Proiezione;
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

        System.out.println("===== CERCA PROIEZIONI =====");

        System.out.print("Titolo film: ");
        String titolo = scanner.nextLine().trim();

        if (titolo.isEmpty()) {
            titolo = null;
        }

        System.out.print("Genere: ");
        String genere = scanner.nextLine().trim();

        if (genere.isEmpty()) {
            genere = null;
        }

        LocalDate dataDa = InputUtil.leggiDataFacoltativa(
                scanner,
                "Data da (YYYY-MM-DD): "
        );

        LocalDate dataA = InputUtil.leggiDataFacoltativa(
                scanner,
                "Data da (YYYY-MM-DD): "
        );

         Double costoMin = InputUtil.leggiDoubleFacoltativo(
                        scanner,
                        "Costo minimo: "
                );

        Double costoMax = InputUtil.leggiDoubleFacoltativo(
                scanner,
                "Costo minimo: "
        );

        ArrayList<Proiezione> risultati =
                proiezioneservice.cercaProiezioni(
                        titolo,
                        genere,
                        dataDa,
                        dataA,
                        costoMin,
                        costoMax
                );

        if (risultati.isEmpty()) {

            System.out.println("Nessuna proiezione trovata");

        } else {

            System.out.println("===== RISULTATI RICERCA =====");

            for (int i = 0; i < risultati.size(); i++) {

                Proiezione proiezione = risultati.get(i);

                System.out.println(
                        (i + 1) + ". "
                        + proiezione.getFilm().getTitolo()
                        + " - "
                        + proiezione.getDataOra()
                        + " - "
                        + proiezione.getCostoBiglietto()
                        + "€"
                );
            }

                while (true) {

                System.out.print("Seleziona una proiezione da visualizzare, oppure 0 per tornare indietro: ");

                int scelta = scanner.nextInt();
                scanner.nextLine();

                if (scelta == 0) {
                    return;
                }

                if (scelta >= 1 && scelta <= risultati.size()) {

                    Proiezione proiezioneSelezionata = risultati.get(scelta - 1);

                    visualizzaProiezione(proiezioneSelezionata);

                    break;

                } else {

                    System.out.println("Scelta non valida");
                }
            }
        }
    }

    public void registrazione() {
        	
        MenuRegistrazione menuRegistrazione =
        		new MenuRegistrazione(scanner, authservice);
        	
        menuRegistrazione.start();
                
        }

    
    private void visualizzaProiezione(Proiezione proiezione) {

        System.out.println("===== DETTAGLIO PROIEZIONE =====");

        System.out.println("Titolo: " + proiezione.getFilm().getTitolo());
        System.out.println("Genere: " + proiezione.getFilm().getGenere());
        System.out.println("Regista: " + proiezione.getFilm().getRegista());
        System.out.println("Anno: " + proiezione.getFilm().getAnno());
        System.out.println("Durata: " + proiezione.getFilm().getDurata() + " minuti");
        System.out.println("Data e ora: " + proiezione.getDataOra());
        System.out.println("Costo biglietto: " + proiezione.getCostoBiglietto() + "€");
        System.out.println("Posti disponibili: " + proiezioneservice.getPostiDisponibili(proiezione));
    }
}
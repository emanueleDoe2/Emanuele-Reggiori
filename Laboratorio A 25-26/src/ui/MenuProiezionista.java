/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package ui;

import java.util.Scanner;
import java.time.LocalDateTime;

import model.Film;
import model.Proiezione;
import util.InputUtil;
import model.Utente;
import service.AuthService;
import service.ProiezioneService;

public class MenuProiezionista {

    private Scanner scanner;
    private Utente utente;
    private AuthService authservice;
    private ProiezioneService proiezioneservice;

    public MenuProiezionista(Scanner scanner,
                             Utente utente,
                             AuthService authservice,
                             ProiezioneService proiezioneservice) {

        this.scanner = scanner;
        this.utente = utente;
        this.authservice = authservice;
        this.proiezioneservice = proiezioneservice;
    }

    public void start() {

        int scelta;

        do {

            stampaMenuProiezionista();

            scelta = InputUtil.leggiInteroObbligatorio(
                    scanner,
                    "Scelta: ",
                    "Scelta non valida. Inserisci un numero."
            );

            if (scelta == 1) {

            	inserisciProiezione();

            } else if (scelta == 2) {

            	modificaDataProiezione();

            } else if (scelta == 3) {

            	eliminaProiezione();

            } else if (scelta == 0) {

                System.out.println("Logout effettuato");

            } else {

                System.out.println("Scelta non valida");
            }

        } while (scelta != 0);
    }

    public void stampaMenuProiezionista() {

        System.out.println();
        System.out.println("===== MENU PROIEZIONISTA =====");
        System.out.println("1. Inserisci film/proiezione");
        System.out.println("2. Modifica data proiezione");
        System.out.println("3. Elimina proiezione");
        System.out.println("0. Logout");
    }
    
    private void inserisciProiezione() {

        System.out.println("===== INSERISCI FILM / PROIEZIONE =====");

        String titolo = InputUtil.leggiStringaObbligatoria(
                scanner,
                "Titolo: ",
                "Il titolo è obbligatorio"
        );

        String genere = InputUtil.leggiStringaObbligatoria(
                scanner,
                "Genere: ",
                "Il genere è obbligatorio"
        );

        String regista = InputUtil.leggiStringaObbligatoria(
                scanner,
                "Regista: ",
                "Il regista è obbligatorio"
        );

        int anno = InputUtil.leggiInteroObbligatorio(
                scanner,
                "Anno: ",
                "Anno non valido"
        );

        int durata = InputUtil.leggiInteroObbligatorio(
                scanner,
                "Durata in minuti: ",
                "Durata non valida"
        );

        int etaMinima = InputUtil.leggiInteroObbligatorio(
                scanner,
                "Età minima: ",
                "Età minima non valida"
        );

        LocalDateTime dataOra = InputUtil.leggiDataOraObbligatoria(
                scanner,
                "Data e ora proiezione (YYYY-MM-DD HH:mm:ss): "
        );

        Double costoBiglietto = InputUtil.leggiDoubleObbligatorio(
                scanner,
                "Costo biglietto: ",
                "Costo non valido"
        );

        Film film = new Film(
                titolo,
                genere,
                regista,
                anno,
                durata,
                etaMinima
        );

        Proiezione proiezione = new Proiezione(
                film,
                dataOra,
                costoBiglietto
        );

        boolean inserita = proiezioneservice.aggiungiProiezione(proiezione);

        if (inserita) {
            System.out.println("Proiezione inserita correttamente");
        } else {
            System.out.println("Inserimento fallito: proiezione sovrapposta o non valida");
        }
    }
    
    private void eliminaProiezione() {

        UICercaProiezioni uiCercaProiezioni =
                new UICercaProiezioni(proiezioneservice, scanner);

        Proiezione proiezioneSelezionata =
                uiCercaProiezioni.RicercaProiezioni();

        if (proiezioneSelezionata == null) {
            return;
        }

        System.out.println("Confermi l'eliminazione della proiezione?");
        System.out.println("1. Sì");
        System.out.println("0. No");
        int conferma = InputUtil.leggiInteroObbligatorio(
                scanner,
                "Scelta: ",
                "Scelta non valida. Inserisci un numero."
        );

        if (conferma == 1) {

            boolean eliminata =
                    proiezioneservice.eliminaProiezione(
                            proiezioneSelezionata
                    );

            if (eliminata) {

                System.out.println("Proiezione eliminata correttamente");

            } else {

                System.out.println(
                        "Eliminazione non consentita: proiezione già passata o con prenotazioni"
                );
            }

        } else {

            System.out.println("Eliminazione annullata");
        }
    }
    
    private void modificaDataProiezione() {

        UICercaProiezioni uiCercaProiezioni =
                new UICercaProiezioni(proiezioneservice, scanner);

        Proiezione proiezioneSelezionata =
                uiCercaProiezioni.RicercaProiezioni();

        if (proiezioneSelezionata == null) {
            return;
        }

        LocalDateTime nuovaDataOra =
                InputUtil.leggiDataOraObbligatoria(
                        scanner,
                        "Nuova data e ora (YYYY-MM-DD HH:mm:ss): "
                );

        boolean modificata =
                proiezioneservice.modificaDataProiezione(
                        proiezioneSelezionata,
                        nuovaDataOra
                );

        if (modificata) {

            System.out.println("Proiezione modificata correttamente");

        } else {

            System.out.println(
                    "Modifica non consentita: proiezione già passata, con prenotazioni o data occupata"
            );
        }
    }
}
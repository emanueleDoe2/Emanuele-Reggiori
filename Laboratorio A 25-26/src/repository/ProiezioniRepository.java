/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

import model.Film;
import model.Proiezione;

public class ProiezioniRepository {

    private String percorsoFile = "data/proiezioni.csv";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ArrayList<Proiezione> caricaProiezioni() {

        ArrayList<Proiezione> proiezioni = new ArrayList<Proiezione>();

        File file = new File(percorsoFile);

        if (!file.exists()) {
            System.out.println("File proiezioni non trovato.");
            return proiezioni;
        }

        try {

            Scanner scanner = new Scanner(file);

            if (scanner.hasNextLine()) {
                scanner.nextLine(); // salta intestazione
            }

            while (scanner.hasNextLine()) {

                String riga = scanner.nextLine();

                if (!riga.isBlank()) {

                    try {

                        String[] campi =
                                riga.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                        if (campi.length < 8) {
                            System.out.println("Riga proiezione incompleta, ignorata: " + riga);
                            continue;
                        }

                        String dataString = campi[0].replace("\"", "").trim();

                        LocalDateTime dataOra =
                                LocalDateTime.parse(dataString, FORMATTER);

                        String titolo = campi[1].replace("\"", "").trim();
                        String genere = campi[2].trim();
                        String regista = campi[3].replace("\"", "").trim();

                        int anno = Integer.parseInt(campi[4].trim());
                        int durata = Integer.parseInt(campi[5].trim());
                        int etaMinima = Integer.parseInt(campi[6].trim());

                        double costoBiglietto =
                                Double.parseDouble(campi[7].trim());

                        if (titolo.isBlank()) {
                            System.out.println("Titolo film mancante, riga ignorata: " + riga);
                            continue;
                        }

                        if (anno <= 0) {
                            System.out.println("Anno film non valido, riga ignorata: " + riga);
                            continue;
                        }

                        if (durata <= 0) {
                            System.out.println("Durata film non valida, riga ignorata: " + riga);
                            continue;
                        }

                        if (etaMinima < 0) {
                            System.out.println("Età minima non valida, riga ignorata: " + riga);
                            continue;
                        }

                        if (costoBiglietto < 0) {
                            System.out.println("Costo biglietto non valido, riga ignorata: " + riga);
                            continue;
                        }

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

                        proiezioni.add(proiezione);

                    } catch (DateTimeParseException e) {

                        System.out.println("Data proiezione non valida, riga ignorata: " + riga);

                    } catch (NumberFormatException e) {

                        System.out.println("Numero non valido nella proiezione, riga ignorata: " + riga);

                    } catch (ArrayIndexOutOfBoundsException e) {

                        System.out.println("Campi mancanti nella proiezione, riga ignorata: " + riga);
                    }
                }
            }

            scanner.close();

        } catch (IOException e) {

            System.out.println("Errore durante il caricamento delle proiezioni.");
        }

        return proiezioni;
    }

    public void salvaProiezione(Proiezione proiezione) {

        try {

            FileWriter writer = new FileWriter(percorsoFile, true);

            writer.write(
                    "\"" + proiezione.getDataOra().format(FORMATTER) + "\"," +
                    "\"" + proiezione.getFilm().getTitolo() + "\"," +
                    proiezione.getFilm().getGenere() + "," +
                    "\"" + proiezione.getFilm().getRegista() + "\"," +
                    proiezione.getFilm().getAnno() + "," +
                    proiezione.getFilm().getDurata() + "," +
                    proiezione.getFilm().getEtaMinima() + "," +
                    proiezione.getCostoBiglietto() +
                    System.lineSeparator()
            );

            writer.close();

        } catch (IOException e) {

            System.out.println("Errore durante il salvataggio della proiezione.");
        }
    }
    
    public void salvaProiezioni(ArrayList<Proiezione> proiezioni) {

        try {

            FileWriter writer = new FileWriter(percorsoFile, false);

            for (Proiezione proiezione : proiezioni) {

                writer.write(
                        "\"" + proiezione.getDataOra().format(FORMATTER) + "\"," +
                        "\"" + proiezione.getFilm().getTitolo() + "\"," +
                        proiezione.getFilm().getGenere() + "," +
                        "\"" + proiezione.getFilm().getRegista() + "\"," +
                        proiezione.getFilm().getAnno() + "," +
                        proiezione.getFilm().getDurata() + "," +
                        proiezione.getFilm().getEtaMinima() + "," +
                        proiezione.getCostoBiglietto() +
                        System.lineSeparator()
                );
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Errore durante il salvataggio delle proiezioni.");
        }
    }
}
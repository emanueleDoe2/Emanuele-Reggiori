package repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import model.Film;
import model.Proiezione;

public class ProiezioniRepository {

    private String percorsoFile = "data/proiezioni.csv";

    public ArrayList<Proiezione> caricaProiezioni() {

        ArrayList<Proiezione> proiezioni = new ArrayList<Proiezione>();

        try {

            File file = new File(percorsoFile);
            Scanner scanner = new Scanner(file);
            scanner.nextLine(); //salta intestazione

            while (scanner.hasNextLine()) {

                String riga = scanner.nextLine();
                String[] campi = riga.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");  //regex per togliere le , all'interno di " "
                
                //parsing date/time
                String dataString = campi[0].replace("\"","");
                dataString = dataString.replace(" ", "T");
                LocalDateTime dataOra = LocalDateTime.parse(dataString);
                
                //parsing del resto
                String titolo = campi[1].replace("\"","");
                String genere = campi[2];
                String regista = campi[3].replace("\"","");
                int anno = Integer.parseInt(campi[4]);
                int durata = Integer.parseInt(campi[5]);
                int etaMinima = Integer.parseInt(campi[6]);

                
                double costoBiglietto = Double.parseDouble(campi[7]);

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

            Film film = proiezione.getFilm();

            writer.write(
                    film.getTitolo() + "," +
                    film.getGenere() + "," +
                    film.getRegista() + "," +
                    film.getAnno() + "," +
                    film.getDurata() + "," +
                    film.getEtaMinima() + "," +
                    proiezione.getDataOra() + "," +
                    proiezione.getCostoBiglietto() + ","
            );

            writer.close();

        } catch (IOException e) {

            System.out.println("Errore durante il salvataggio della proiezione.");

        }
    }
}
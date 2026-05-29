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
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.Film;
import model.Prenotazione;
import model.Proiezione;


public class PrenotazioniRepository {

	private static final DateTimeFormatter FORMATTER =
	        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private String percorsoFile = "data/prenotazioni.csv";

	public PrenotazioniRepository() {

		try {

			File file = new File(percorsoFile);

			if (!file.exists()) {

				file.createNewFile();

			}

		} catch (IOException e) {

			System.out.println("Errore nella creazione del file prenotazioni.");

		}

	}

	public ArrayList<Prenotazione> caricaPrenotazioni() {

	    ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

	    File file = new File(percorsoFile);

	    if (!file.exists()) {
	        System.out.println("File prenotazioni non trovato.");
	        return prenotazioni;
	    }

	    try {

	        Scanner scanner = new Scanner(file);

	        while (scanner.hasNextLine()) {

	            String riga = scanner.nextLine();

	            if (!riga.isBlank()) {

	                try {

	                    String[] campi =
	                            riga.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

	                    if (campi.length < 5) {
	                        System.out.println("Riga prenotazione incompleta, ignorata: " + riga);
	                        continue;
	                    }

	                    String codicePrenotazione = campi[0].trim();
	                    String username = campi[1].trim();
	                    String titoloFilm = campi[2].replace("\"", "").trim();

	                    String dataString = campi[3].replace("\"", "").trim();

	                    LocalDateTime dataOra =
	                            LocalDateTime.parse(dataString, FORMATTER);

	                    int numeroPosti =
	                            Integer.parseInt(campi[4].trim());

	                    if (numeroPosti <= 0) {
	                        System.out.println("Numero posti non valido, riga ignorata: " + riga);
	                        continue;
	                    }

	                    Film film =
	                            new Film(titoloFilm, "", "", 0, 0, 0);

	                    Proiezione proiezione =
	                            new Proiezione(film, dataOra, 0);

	                    Prenotazione prenotazione =
	                            new Prenotazione(
	                                    codicePrenotazione,
	                                    proiezione,
	                                    numeroPosti,
	                                    username
	                            );

	                    prenotazioni.add(prenotazione);

	                } catch (DateTimeParseException e) {

	                    System.out.println("Data prenotazione non valida, riga ignorata: " + riga);

	                } catch (NumberFormatException e) {

	                    System.out.println("Numero posti non valido, riga ignorata: " + riga);

	                } catch (ArrayIndexOutOfBoundsException e) {

	                    System.out.println("Campi mancanti nella prenotazione, riga ignorata: " + riga);
	                }
	            }
	        }

	        scanner.close();

	    } catch (IOException e) {

	        System.out.println("Errore durante il caricamento delle prenotazioni.");
	    }

	    return prenotazioni;
	}

	
	public void salvaPrenotazione(Prenotazione prenotazione) {
		try {
			FileWriter writer = new FileWriter(percorsoFile, true);
			writer.write(
			        prenotazione.getCodicePrenotazione() + "," +
			        prenotazione.getUsername() + ",\"" +
			        prenotazione.getProiezione().getFilm().getTitolo() + "\"," +
			        prenotazione.getProiezione().getDataOra().format(FORMATTER) + "," +
			        prenotazione.getNumeroPostiPrenotati() +
			        System.lineSeparator()
			);
			writer.close();
		} catch (IOException e) {
			System.out.println("Errore durante il salvataggio della prenotazione.");
		}
	}

	
	public void salvaPrenotazioni(ArrayList<Prenotazione> prenotazioni) {

		try {

			FileWriter writer = new FileWriter(percorsoFile, false);

			for (Prenotazione prenotazione : prenotazioni) {

				writer.write(prenotazione.getCodicePrenotazione() + "," + prenotazione.getUsername() + ",\""
						+ prenotazione.getProiezione().getFilm().getTitolo() + "\","
						+ prenotazione.getProiezione().getDataOra() + "," + prenotazione.getNumeroPostiPrenotati()
						+ System.lineSeparator());
			}

			writer.close();

		} catch (IOException e) {

			System.out.println("Errore durante il salvataggio delle prenotazioni.");
		}
	}

}
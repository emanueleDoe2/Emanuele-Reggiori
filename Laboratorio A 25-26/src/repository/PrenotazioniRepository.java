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

import model.Prenotazione;
import model.Proiezione;

/**
 * Gestisce il caricamento e il salvataggio delle prenotazioni su file CSV.
 */
public class PrenotazioniRepository {

	private ProiezioniRepository proiezioniRepository = new ProiezioniRepository();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private String percorsoFile = "data/prenotazioni.csv";

	/**
	 * Crea una nuova istanza della classe PrenotazioniRepository. Se il file CSV
	 * non è presente, viene creato.
	 */
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

	/**
	 * Carica da file tutte le prenotazioni valide. Dalle stringhe vengono tolti i "
	 * "
	 *
	 * @return elenco dei risultati ottenuti.
	 */
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

						String[] campi = riga.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

						if (campi.length < 5) {
							System.out.println("Riga prenotazione incompleta, ignorata: " + riga);
							continue;
						}

						String codicePrenotazione = campi[0].trim();
						String username = campi[1].trim();
						String titoloFilm = campi[2].replace("\"", "").trim();

						String dataString = campi[3].replace("\"", "").trim();

						LocalDateTime dataOra = LocalDateTime.parse(dataString, FORMATTER);

						int numeroPosti = Integer.parseInt(campi[4].trim());

						if (numeroPosti <= 0) {
							System.out.println("Numero posti non valido, riga ignorata: " + riga);
							continue;
						}

						Proiezione proiezione = trovaProiezione(titoloFilm, dataOra);

						if (proiezione == null) {
							System.out.println("Proiezione non trovata per prenotazione, riga ignorata: " + riga);
							continue;
						}

						Prenotazione prenotazione = new Prenotazione(codicePrenotazione, proiezione, numeroPosti,
								username);

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

	/**
	 * Salva su file una nuova prenotazione (in coda).
	 *
	 * @param prenotazione prenotazione da salvare.
	 */
	public void salvaPrenotazione(Prenotazione prenotazione) {
		try {
			FileWriter writer = new FileWriter(percorsoFile, true);
			writer.write(prenotazione.getCodicePrenotazione() + "," + prenotazione.getUsername() + ",\""
					+ prenotazione.getProiezione().getFilm().getTitolo() + "\","
					+ prenotazione.getProiezione().getDataOra().format(FORMATTER) + ","
					+ prenotazione.getNumeroPostiPrenotati() + System.lineSeparator());
			writer.close();
		} catch (IOException e) {
			System.out.println("Errore durante il salvataggio della prenotazione.");
		}
	}

	/**
	 * Riscrive su file l’elenco completo delle prenotazioni. Necessario per il
	 * delete delle prenotazioni.
	 *
	 * @param prenotazioni elenco delle prenotazioni caricate.
	 */
	public void salvaPrenotazioni(ArrayList<Prenotazione> prenotazioni) {

		try {

			FileWriter writer = new FileWriter(percorsoFile);

			for (Prenotazione prenotazione : prenotazioni) {

				String dataFormattata = prenotazione.getProiezione().getDataOra().format(FORMATTER);

				writer.write(prenotazione.getCodicePrenotazione() + "," + prenotazione.getUsername() + ",\""
						+ prenotazione.getProiezione().getFilm().getTitolo() + "\",\"" + dataFormattata + "\","
						+ prenotazione.getNumeroPostiPrenotati() + System.lineSeparator());
			}

			writer.close();

		} catch (IOException e) {

			System.out.println("Errore durante il salvataggio delle prenotazioni.");
		}
	}

	/**
	 * Cerca una proiezione in base a titolo del film e data/ora.
	 *
	 * @param titoloFilm titolo del film da cercare.
	 * @param dataOra    parametro utilizzato dal metodo.
	 *
	 * @return oggetto richiesto, oppure null se non disponibile.
	 */
	private Proiezione trovaProiezione(String titoloFilm, LocalDateTime dataOra) {

		ArrayList<Proiezione> proiezioni = proiezioniRepository.caricaProiezioni();

		for (Proiezione proiezione : proiezioni) {

			boolean stessoTitolo = proiezione.getFilm().getTitolo().equalsIgnoreCase(titoloFilm);

			boolean stessaData = proiezione.getDataOra().equals(dataOra);

			if (stessoTitolo && stessaData) {
				return proiezione;
			}
		}

		return null;
	}

}
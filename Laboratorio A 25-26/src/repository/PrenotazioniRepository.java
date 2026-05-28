package repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import model.Film;
import model.Prenotazione;
import model.Proiezione;


public class PrenotazioniRepository {

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

		try {

			File file = new File(percorsoFile);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {

				String riga = scanner.nextLine();

				if (!riga.isEmpty()) {

					String[] campi = riga.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // regex per togliere le ,
																					// all'interno di " "

					String codicePrenotazione = campi[0];
					String username = campi[1];
					String titoloFilm = campi[2].replace("\"", "");
					String dataString = campi[3].replace("\"", "");
					dataString = dataString.replace(" ", "T");
					LocalDateTime dataOra = LocalDateTime.parse(dataString);
					int numeroPosti = Integer.parseInt(campi[4]);

					Film film = new Film(titoloFilm, "", "", 0, 0, 0);

					Proiezione proiezione = new Proiezione(film, dataOra, 0);

					Prenotazione prenotazione = new Prenotazione(codicePrenotazione, proiezione, numeroPosti, username);

					prenotazioni.add(prenotazione);

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
			writer.write(prenotazione.getCodicePrenotazione() + "," + prenotazione.getUsername() + ",\""
					+ prenotazione.getProiezione().getFilm().getTitolo() + "\","
					+ prenotazione.getProiezione().getDataOra() + "," + prenotazione.getNumeroPostiPrenotati() + "\n");
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
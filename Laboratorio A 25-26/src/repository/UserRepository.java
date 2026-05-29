/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.Ruolo;
import model.Utente;

/**
 * Gestisce il caricamento e il salvataggio degli utenti su file CSV.
 */
public class UserRepository {

	private String percorsoFile = "data/utenti.csv";

	/**
	 * Carica da file tutti gli utenti validi.
	 *
	 * @return elenco dei risultati ottenuti.
	 */
	public ArrayList<Utente> caricaUtenti() {

		ArrayList<Utente> utenti = new ArrayList<Utente>();

		File file = new File(percorsoFile);

		if (!file.exists()) {
			System.out.println("File utenti non trovato.");
			return utenti;
		}

		try {

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {

				String riga = scanner.nextLine();

				if (!riga.isBlank()) {

					try {

						String[] campi = riga.split(",");

						if (campi.length < 7) {
							System.out.println("Riga utente incompleta, ignorata: " + riga);
							continue;
						}

						String nome = campi[0].trim();
						String cognome = campi[1].trim();
						String username = campi[2].trim();
						String password = campi[3].trim();

						LocalDate dataNascita = null;

						String data = campi[4].trim();

						if (!data.isEmpty() && !data.equalsIgnoreCase("null")) {
							dataNascita = LocalDate.parse(data);
						}

						String domicilio = campi[5].trim();

						Ruolo ruolo = Ruolo.valueOf(campi[6].trim().toUpperCase());

						if (nome.isBlank()) {
							System.out.println("Nome utente mancante, riga ignorata: " + riga);
							continue;
						}

						if (cognome.isBlank()) {
							System.out.println("Cognome utente mancante, riga ignorata: " + riga);
							continue;
						}

						if (username.isBlank()) {
							System.out.println("Username mancante, riga ignorata: " + riga);
							continue;
						}

						if (password.isBlank()) {
							System.out.println("Password mancante, riga ignorata: " + riga);
							continue;
						}

						if (domicilio.isBlank()) {
							System.out.println("Domicilio mancante, riga ignorata: " + riga);
							continue;
						}

						Utente utente = new Utente(nome, cognome, username, password, dataNascita, domicilio, ruolo);

						utenti.add(utente);

					} catch (DateTimeParseException e) {

						System.out.println("Data di nascita non valida, riga ignorata: " + riga);

					} catch (IllegalArgumentException e) {

						System.out.println("Ruolo non valido, riga ignorata: " + riga);

					} catch (ArrayIndexOutOfBoundsException e) {

						System.out.println("Campi mancanti nella riga utente, riga ignorata: " + riga);
					}
				}
			}

			scanner.close();

		} catch (IOException e) {

			System.out.println("Errore durante il caricamento degli utenti.");
		}

		return utenti;
	}

	/**
	 * Salva su file (in fondo) un nuovo utente.
	 *
	 * @param utente utente interessato dall’operazione.
	 */
	public void salvaUtente(Utente utente) {

		try {

			FileWriter writer = new FileWriter(percorsoFile, true);

			writer.write(utente.getNome() + "," + utente.getCognome() + "," + utente.getUsername() + ","
					+ utente.getPassword() + "," + utente.getDataNascita() + "," + utente.getDomicilio() + ","
					+ utente.getRuolo());

			writer.write(System.lineSeparator());
			writer.close();

		} catch (IOException e) {

			System.out.println("Errore durante il salvataggio dell'utente.");

		}
	}
}
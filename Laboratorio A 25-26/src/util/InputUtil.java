/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package util;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Fornisce metodi di utilità per leggere e validare input da terminale.
 */
public class InputUtil {

	/**
	 * Legge una stringa obbligatoria da terminale.
	 *
	 * @param scanner         scanner utilizzato per leggere l’input da terminale.
	 * @param messaggio       messaggio da mostrare prima della lettura.
	 * @param messaggioErrore messaggio da mostrare in caso di input non valido.
	 *
	 * @return valore testuale richiesto.
	 */
	public static String leggiStringaObbligatoria(Scanner scanner, String messaggio, String messaggioErrore) {

		String valore = "";

		while (valore.isBlank()) {

			System.out.print(messaggio);

			valore = scanner.nextLine().trim();

			if (valore.isBlank()) {

				System.out.println(messaggioErrore);
			}
		}

		return valore;
	}

	/**
	 * Legge una data facoltativa da terminale.
	 *
	 * @param scanner   scanner utilizzato per leggere l’input da terminale.
	 * @param messaggio messaggio da mostrare prima della lettura.
	 *
	 * @return oggetto richiesto, oppure null se non disponibile.
	 */
	public static LocalDate leggiDataFacoltativa(Scanner scanner, String messaggio) {

		while (true) {

			System.out.print(messaggio);

			String input = scanner.nextLine().trim();

			if (input.isEmpty()) {
				return null;
			}

			try {

				return LocalDate.parse(input);

			} catch (Exception e) {

				System.out.println("Data non valida. Usa il formato YYYY-MM-DD.");
			}
		}
	}

	/**
	 * Legge un numero decimale facoltativo da terminale.
	 *
	 * @param scanner   scanner utilizzato per leggere l’input da terminale.
	 * @param messaggio messaggio da mostrare prima della lettura.
	 *
	 * @return valore letto dal metodo.
	 */
	public static Double leggiDoubleFacoltativo(Scanner scanner, String messaggio) {

		while (true) {

			System.out.print(messaggio);

			String input = scanner.nextLine().trim();

			if (input.isEmpty()) {
				return null;
			}

			try {

				return Double.parseDouble(input);

			} catch (Exception e) {

				System.out.println("Numero non valido.");
			}
		}
	}

	/**
	 * Legge un numero intero obbligatorio da terminale.
	 *
	 * @param scanner         scanner utilizzato per leggere l’input da terminale.
	 * @param messaggio       messaggio da mostrare prima della lettura.
	 * @param messaggioErrore messaggio da mostrare in caso di input non valido.
	 *
	 * @return valore letto dal metodo.
	 */
	public static int leggiInteroObbligatorio(Scanner scanner, String messaggio, String messaggioErrore) {

		while (true) {

			System.out.print(messaggio);
			String input = scanner.nextLine().trim();

			try {
				return Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println(messaggioErrore);
			}
		}
	}

	/**
	 * Legge un numero decimale obbligatorio da terminale.
	 *
	 * @param scanner         scanner utilizzato per leggere l’input da terminale.
	 * @param messaggio       messaggio da mostrare prima della lettura.
	 * @param messaggioErrore messaggio da mostrare in caso di input non valido.
	 *
	 * @return valore letto dal metodo.
	 */
	public static Double leggiDoubleObbligatorio(Scanner scanner, String messaggio, String messaggioErrore) {

		while (true) {

			System.out.print(messaggio);
			String input = scanner.nextLine().trim();

			try {
				return Double.parseDouble(input);
			} catch (Exception e) {
				System.out.println(messaggioErrore);
			}
		}
	}

	/**
	 * Legge una data e ora obbligatoria da terminale.
	 *
	 * @param scanner   scanner utilizzato per leggere l’input da terminale.
	 * @param messaggio messaggio da mostrare prima della lettura.
	 *
	 * @return oggetto richiesto.
	 */
	public static LocalDateTime leggiDataOraObbligatoria(Scanner scanner, String messaggio) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		while (true) {

			System.out.print(messaggio);

			String input = scanner.nextLine().trim();

			try {

				return LocalDateTime.parse(input, formatter);

			} catch (Exception e) {

				System.out.println("Data e ora non valide. Usa il formato YYYY-MM-DD HH:mm:ss");
			}
		}
	}

}
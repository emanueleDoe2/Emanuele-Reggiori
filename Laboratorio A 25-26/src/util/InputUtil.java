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

public class InputUtil {

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
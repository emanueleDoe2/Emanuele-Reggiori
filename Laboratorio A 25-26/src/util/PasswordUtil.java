/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package util;

import java.security.MessageDigest;

/**
 * Classe di utilità per la gestione delle password.
 * 
 * Fornisce metodi per la conversione delle password in hash utilizzando
 * l'algoritmo SHA-256.
 * 
 */
public class PasswordUtil {

	/**
	 * Calcola l'hash SHA-256 della password specificata.
	 *
	 * @param password password in chiaro da cifrare
	 * @return rappresentazione esadecimale dell'hash della password
	 * @throws RuntimeException se si verifica un errore durante il calcolo
	 *                          dell'hash
	 */
	public static String hashPassword(String password) {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] hash = digest.digest(password.getBytes());

			StringBuilder hexString = new StringBuilder();

			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}

			return hexString.toString();

		} catch (Exception e) {
			throw new RuntimeException("Errore durante la cifratura della password", e);
		}
	}
}
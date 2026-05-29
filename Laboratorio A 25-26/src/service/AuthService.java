/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package service;

import java.util.ArrayList;
import java.time.LocalDate;

import model.Ruolo;
import model.Utente;
import repository.UserRepository;
import util.PasswordUtil;

/**
 * Gestisce logica applicativa di autenticazione, registrazione e ricerca degli
 * utenti.
 */
public class AuthService {

	private ArrayList<Utente> utenti;
	private UserRepository utenteRepository;

	/**
	 * Crea una nuova istanza della classe AuthService.
	 *
	 * @param utenti           elenco degli utenti caricati.
	 * @param utenteRepository repository degli utenti.
	 */
	public AuthService(ArrayList<Utente> utenti, UserRepository utenteRepository) {

		this.utenti = utenti;
		this.utenteRepository = utenteRepository;

	}

	/**
	 * Gestisce da terminale l’accesso dell’utente e apre il menu relativo al suo
	 * ruolo.
	 *
	 * @param username username dell’utente.
	 * @param password password in chiaro inserita dall’utente.
	 *
	 * @return oggetto richiesto, oppure null se non disponibile.
	 */
	public Utente login(String username, String password) {

		for (Utente utente : utenti) {

			if (utente.getUsername().equals(username)
					&& utente.getPassword().equals(PasswordUtil.hashPassword(password))) {

				return utente;

			}

		}

		return null;
	}

	/**
	 * Verifica se uno username è già presente.
	 *
	 * @param username username dell’utente.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	public boolean usernameEsiste(String username) {

		for (Utente utente : utenti) {

			if (utente.getUsername().equals(username)) {

				return true;

			}

		}

		return false;
	}

	/**
	 * Registra un nuovo utente con ruolo cliente.
	 *
	 * @param nome        nome del cliente.
	 * @param cognome     cognome del cliente.
	 * @param username    username dell’utente.
	 * @param password    password in chiaro inserita dall’utente.
	 * @param dataNascita data di nascita del cliente.
	 * @param domicilio   domicilio del cliente.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	public boolean registraCliente(String nome, String cognome, String username, String password, LocalDate dataNascita,
			String domicilio) {

		if (usernameEsiste(username)) {

			return false;

		}

		String PasswordCifrata = PasswordUtil.hashPassword(password);

		Utente nuovoUtente = new Utente(nome, cognome, username, PasswordCifrata, dataNascita, domicilio,
				Ruolo.CLIENTE);

		utenti.add(nuovoUtente);
		utenteRepository.salvaUtente(nuovoUtente);

		return true;
	}

	public Utente trovaUtentePerUsername(String username) {

		for (Utente utente : utenti) {

			if (utente.getUsername().equalsIgnoreCase(username)) {
				return utente;
			}
		}

		return null;
	}

}
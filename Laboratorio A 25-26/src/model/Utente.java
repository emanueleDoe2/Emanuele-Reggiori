/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package model;

import java.time.LocalDate;

/**
 * Rappresenta un utente dell’applicazione con dati anagrafici, credenziali e
 * ruolo.
 */
public class Utente {

	private String nome;
	private String cognome;
	private String username;
	private String password;
	private LocalDate dataNascita;
	private String domicilio;
	private Ruolo ruolo;

	/**
	 * Crea una nuova istanza della classe Utente.
	 *
	 * @param nome        nome del cliente.
	 * @param cognome     cognome del cliente.
	 * @param username    username dell’utente.
	 * @param password    password inserita dall’utente (hashata).
	 * @param dataNascita data di nascita del cliente.
	 * @param domicilio   domicilio del cliente.
	 * @param ruolo       ruolo utente (proiezionista, bigliettaio, cliente).
	 */
	public Utente(String nome, String cognome, String username, String password, LocalDate dataNascita,
			String domicilio, Ruolo ruolo) {

		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.dataNascita = dataNascita;
		this.domicilio = domicilio;
		this.ruolo = ruolo;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

}
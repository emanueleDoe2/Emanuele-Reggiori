/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package service;

import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDate;

import model.Prenotazione;
import model.Proiezione;
import model.Utente;
import repository.PrenotazioniRepository;

/**
 * Contiene la logica applicativa relativa alla creazione, ricerca, modifica ed
 * eliminazione delle prenotazioni.
 */
public class PrenotazioneService {

	private AuthService authservice;
	private ArrayList<Prenotazione> prenotazioni;
	private PrenotazioniRepository prenotazioniRepository;
	private ProiezioneService proiezioneService;

	/**
	 * Crea una nuova istanza della classe PrenotazioneService.
	 *
	 * @param prenotazioni           elenco delle prenotazioni caricate.
	 * @param prenotazioniRepository repository delle prenotazioni.
	 * @param proiezioneService      servizio delle proiezioni.
	 * @param utenti                 elenco degli utenti caricati.
	 * @param authservice            servizio di autenticazione.
	 */
	public PrenotazioneService(ArrayList<Prenotazione> prenotazioni, PrenotazioniRepository prenotazioniRepository,
			ProiezioneService proiezioneService, ArrayList<Utente> utenti, AuthService authservice) {

		this.prenotazioni = prenotazioni;
		this.prenotazioniRepository = prenotazioniRepository;
		this.proiezioneService = proiezioneService;
		this.authservice = authservice;
	}

	/**
	 * Richiede il numero di posti e crea una prenotazione.
	 *
	 * @param utente      utente che effettua l'operazione.
	 * @param proiezione  proiezione da prenotare.
	 * @param numeroPosti numero di posti richiesti.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	public boolean creaPrenotazione(Utente utente, Proiezione proiezione, int numeroPosti) {

		if (utente == null || proiezione == null) {
			return false;
		}

		if (numeroPosti <= 0) {
			return false;
		}

		int postiDisponibili = proiezioneService.getPostiDisponibili(proiezione);

		if (numeroPosti > postiDisponibili) {
			return false;
		}

		String codicePrenotazione = generaCodicePrenotazione();

		Prenotazione prenotazione = new Prenotazione(codicePrenotazione, proiezione, numeroPosti, utente.getUsername());

		prenotazioni.add(prenotazione);
		prenotazioniRepository.salvaPrenotazione(prenotazione);

		return true;
	}

	/**
	 * Genera un codice prenotazione univoco. Controlla se esistono già codici
	 * prenotazione uguali.
	 *
	 * @return valore testuale richiesto.
	 */
	private String generaCodicePrenotazione() {
		String codice;
		do {
			codice = "PRE-" + UUID.randomUUID().toString().substring(0, 8);
		} while (codicePrenotazioneEsiste(codice));
		return codice;
	}

	/**
	 * Restituisce le prenotazioni associate a uno specifico utente.
	 *
	 * @param utente utente interessato dall’operazione.
	 *
	 * @return elenco dei risultati ottenuti.
	 */
	public ArrayList<Prenotazione> getPrenotazioniUtente(Utente utente) {

		ArrayList<Prenotazione> risultati = new ArrayList<>();

		for (Prenotazione prenotazione : prenotazioni) {

			if (prenotazione.getUsername().equalsIgnoreCase(utente.getUsername())) {

				risultati.add(prenotazione);
			}
		}

		return risultati;
	}

	/**
	 * Elimina una prenotazione solo se la data della prenotazione è prima di oggi.
	 *
	 * @param prenotazioneDaEliminare prenotazione da eliminare.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	public boolean eliminaPrenotazione(Prenotazione prenotazioneDaEliminare) {

		if (prenotazioneDaEliminare == null) {
			return false;
		}

		LocalDate dataProiezione = prenotazioneDaEliminare.getProiezione().getDataOra().toLocalDate();

		LocalDate oggi = LocalDate.now();

		if (!dataProiezione.isBefore(oggi)) {
			return false;
		}

		boolean rimossa = false;

		for (int i = 0; i < prenotazioni.size(); i++) {

			Prenotazione prenotazione = prenotazioni.get(i);

			if (prenotazione.getCodicePrenotazione().equals(prenotazioneDaEliminare.getCodicePrenotazione())) {

				prenotazioni.remove(i);
				rimossa = true;
				break;
			}
		}

		if (rimossa) {
			prenotazioniRepository.salvaPrenotazioni(prenotazioni);
		}

		if (rimossa) {
			prenotazioniRepository.salvaPrenotazioni(prenotazioni);
		}

		return rimossa;
	}

	/**
	 * Restituisce le proiezioni alternative disponibili per lo stesso film.
	 *
	 * @param prenotazione prenotazione che si vuole modificare.
	 *
	 * @return elenco dei risultati ottenuti.
	 */
	public ArrayList<Proiezione> getProiezioniAlternative(Prenotazione prenotazione) {

		ArrayList<Proiezione> risultati = new ArrayList<>();

		if (prenotazione == null) {
			return risultati;
		}

		String titoloFilm = prenotazione.getProiezione().getFilm().getTitolo();

		LocalDate oggi = LocalDate.now();

		for (Proiezione proiezione : proiezioneService.getProiezioni()) {

			boolean stessoTitolo = proiezione.getFilm().getTitolo().equalsIgnoreCase(titoloFilm);

			boolean dataFutura = proiezione.getDataOra().toLocalDate().isAfter(oggi);

			boolean diversaDaAttuale = !proiezione.getDataOra().equals(prenotazione.getProiezione().getDataOra());

			if (stessoTitolo && dataFutura && diversaDaAttuale) {
				risultati.add(proiezione);
			}
		}

		return risultati;
	}

	/**
	 * Modifica la data di una prenotazione selezionando una nuova proiezione.
	 *
	 * @param prenotazioneDaModificare prenotazione da modificare.
	 * @param nuovaProiezione          nuova proiezione da associare alla
	 *                                 prenotazione.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	public boolean modificaPrenotazione(Prenotazione prenotazioneDaModificare, Proiezione nuovaProiezione) {

		if (prenotazioneDaModificare == null || nuovaProiezione == null) {
			return false;
		}

		LocalDate oggi = LocalDate.now();

		LocalDate dataVecchia = prenotazioneDaModificare.getProiezione().getDataOra().toLocalDate();

		LocalDate dataNuova = nuovaProiezione.getDataOra().toLocalDate();

		if (!dataVecchia.isAfter(oggi)) {
			return false;
		}

		if (!dataNuova.isAfter(oggi)) {
			return false;
		}

		int postiDisponibili = proiezioneService.getPostiDisponibili(nuovaProiezione);

		if (prenotazioneDaModificare.getNumeroPostiPrenotati() > postiDisponibili) {
			return false;
		}

		for (int i = 0; i < prenotazioni.size(); i++) {

			Prenotazione prenotazione = prenotazioni.get(i);

			if (prenotazione.getCodicePrenotazione().equals(prenotazioneDaModificare.getCodicePrenotazione())) {

				Prenotazione prenotazioneModificata = new Prenotazione(prenotazione.getCodicePrenotazione(),
						nuovaProiezione, prenotazione.getNumeroPostiPrenotati(), prenotazione.getUsername());

				prenotazioni.set(i, prenotazioneModificata);

				prenotazioniRepository.salvaPrenotazioni(prenotazioni);

				return true;
			}
		}

		return false;
	}

	/**
	 * Gestisce la ricerca delle prenotazioni.
	 *
	 * @param codicePrenotazione codice identificativo della prenotazione.
	 * @param nomeCliente        nome del cliente da cercare.
	 * @param cognomeCliente     cognome del cliente da cercare.
	 * @param titoloFilm         titolo del film da cercare.
	 * @param dataDa             data iniziale del filtro.
	 * @param dataA              data finale del filtro.
	 *
	 * @return elenco dei risultati ottenuti.
	 */
	public ArrayList<Prenotazione> cercaPrenotazioni(String codicePrenotazione, String nomeCliente,
			String cognomeCliente, String titoloFilm, LocalDate dataDa, LocalDate dataA) {

		ArrayList<Prenotazione> risultati = new ArrayList<>();

		for (Prenotazione prenotazione : prenotazioni) {

			boolean valida = true;

			if (codicePrenotazione != null && !codicePrenotazione.isBlank()) {

				if (!prenotazione.getCodicePrenotazione().equalsIgnoreCase(codicePrenotazione)) {

					valida = false;
				}
			}

			if (titoloFilm != null && !titoloFilm.isBlank()) {

				String titoloPrenotazione = prenotazione.getProiezione().getFilm().getTitolo().toLowerCase();

				String titoloRicerca = titoloFilm.toLowerCase();

				if (!titoloPrenotazione.contains(titoloRicerca)) {
					valida = false;
				}
			}

			if (dataDa != null) {

				LocalDate dataPrenotazione = prenotazione.getProiezione().getDataOra().toLocalDate();

				if (dataPrenotazione.isBefore(dataDa)) {
					valida = false;
				}
			}

			if (dataA != null) {

				LocalDate dataPrenotazione = prenotazione.getProiezione().getDataOra().toLocalDate();

				if (dataPrenotazione.isAfter(dataA)) {
					valida = false;
				}
			}

			if ((nomeCliente != null && !nomeCliente.isBlank())
					|| (cognomeCliente != null && !cognomeCliente.isBlank())) {

				Utente utentePrenotazione = authservice.trovaUtentePerUsername(prenotazione.getUsername());

				if (utentePrenotazione == null) {

					valida = false;

				} else {

					if (nomeCliente != null && !nomeCliente.isBlank()) {

						String nomeUtente = utentePrenotazione.getNome().toLowerCase();

						String nomeRicerca = nomeCliente.toLowerCase();

						if (!nomeUtente.contains(nomeRicerca)) {
							valida = false;
						}
					}

					if (cognomeCliente != null && !cognomeCliente.isBlank()) {

						String cognomeUtente = utentePrenotazione.getCognome().toLowerCase();

						String cognomeRicerca = cognomeCliente.toLowerCase();

						if (!cognomeUtente.contains(cognomeRicerca)) {
							valida = false;
						}
					}
				}
			}

			if (valida) {
				risultati.add(prenotazione);
			}
		}

		return risultati;
	}

	/**
	 * Restituisce le prenotazioni relative alla data odierna.
	 *
	 * @return elenco dei risultati ottenuti.
	 */
	public ArrayList<Prenotazione> getPrenotazioniOggi() {

		ArrayList<Prenotazione> risultati = new ArrayList<>();

		LocalDate oggi = LocalDate.now();

		for (Prenotazione prenotazione : prenotazioni) {

			LocalDate dataPrenotazione = prenotazione.getProiezione().getDataOra().toLocalDate();

			if (dataPrenotazione.equals(oggi)) {
				risultati.add(prenotazione);
			}
		}

		return risultati;
	}

	/**
	 * Verifica se un codice prenotazione è già presente.
	 *
	 * @param codice codice prenotazione da verificare.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	private boolean codicePrenotazioneEsiste(String codice) {

		ArrayList<Prenotazione> prenotazioni = prenotazioniRepository.caricaPrenotazioni();

		for (Prenotazione prenotazione : prenotazioni) {
			if (prenotazione.getCodicePrenotazione().equalsIgnoreCase(codice)) {
				return true;
			}
		}

		return false;
	}
}
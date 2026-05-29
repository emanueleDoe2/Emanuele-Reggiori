/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;

import repository.ProiezioniRepository;
import model.Prenotazione;
import model.Proiezione;

/**
 * Contiene la logica applicativa relativa alla ricerca, visualizzazione,
 * aggiunta, modifica ed eliminazione delle proiezioni.
 */
public class ProiezioneService {

	private ArrayList<Proiezione> proiezioni;
	private ArrayList<Prenotazione> prenotazioni;
	private ProiezioniRepository proiezioniRepository;

	private static final int CAPIENZA_SALA = 200;

	/**
	 * Crea una nuova istanza della classe ProiezioneService.
	 *
	 * @param proiezioni           elenco delle proiezioni caricate.
	 * @param prenotazioni         elenco delle prenotazioni caricate.
	 * @param proiezioniRepository repository delle proiezioni.
	 */
	public ProiezioneService(ArrayList<Proiezione> proiezioni, ArrayList<Prenotazione> prenotazioni,
			ProiezioniRepository proiezioniRepository) {

		this.proiezioni = proiezioni;
		this.prenotazioni = prenotazioni;
		this.proiezioniRepository = proiezioniRepository;
	}

	/**
	 * Calcola i posti ancora disponibili per una proiezione cercando tutte le
	 * prenotazioni associate a quella proiezione.
	 *
	 * @param proiezione proiezione da gestire.
	 *
	 * @return valore calcolato.
	 */
	public int getPostiDisponibili(Proiezione proiezione) {

		int postiPrenotati = 0;

		for (Prenotazione prenotazione : prenotazioni) {

			boolean stessoFilm = prenotazione.getProiezione().getFilm().getTitolo()
					.equalsIgnoreCase(proiezione.getFilm().getTitolo());

			boolean stessaDataOra = prenotazione.getProiezione().getDataOra().equals(proiezione.getDataOra());

			if (stessoFilm && stessaDataOra) {

				postiPrenotati += prenotazione.getNumeroPostiPrenotati();
			}
		}

		return CAPIENZA_SALA - postiPrenotati;
	}

	/**
	 * Cerca proiezioni applicando i filtri indicati.
	 *
	 * @param titolo   titolo del film.
	 * @param genere   genere del film.
	 * @param dataDa   data iniziale del filtro.
	 * @param dataA    data finale del filtro.
	 * @param costoMin costo minimo del filtro.
	 * @param costoMax costo massimo del filtro.
	 *
	 * @return elenco dei risultati ottenuti.
	 */
	public ArrayList<Proiezione> cercaProiezioni(String titolo, String genere, LocalDate dataDa, LocalDate dataA,
			Double costoMin, Double costoMax) {

		ArrayList<Proiezione> risultati = new ArrayList<>();

		for (Proiezione proiezione : proiezioni) {

			boolean valida = true;

			if (titolo != null && !titolo.isBlank()) {

				String titoloFilm = proiezione.getFilm().getTitolo().toLowerCase();

				String titoloRicerca = titolo.toLowerCase();

				if (!titoloFilm.contains(titoloRicerca)) {
					valida = false;
				}
			}

			if (genere != null && !genere.isBlank()) {

				String genereFilm = proiezione.getFilm().getGenere().toLowerCase();

				String genereRicerca = genere.toLowerCase();

				if (!genereFilm.equals(genereRicerca)) {
					valida = false;
				}
			}

			if (dataDa != null) {

				LocalDate dataProiezione = proiezione.getDataOra().toLocalDate();

				if (dataProiezione.isBefore(dataDa)) {
					valida = false;
				}
			}

			if (dataA != null) {

				LocalDate dataProiezione = proiezione.getDataOra().toLocalDate();

				if (dataProiezione.isAfter(dataA)) {
					valida = false;
				}
			}

			if (costoMin != null) {

				if (proiezione.getCostoBiglietto() < costoMin) {
					valida = false;
				}
			}

			if (costoMax != null) {

				if (proiezione.getCostoBiglietto() > costoMax) {
					valida = false;
				}
			}

			if (valida) {
				risultati.add(proiezione);
			}
		}

		return risultati;
	}

	public ArrayList<Proiezione> getProiezioni() {
		return proiezioni;
	}

	/**
	 * Aggiunge una nuova proiezione se non si sovrappone a una già esistente.
	 *
	 * @param proiezione proiezione da gestire.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	public boolean aggiungiProiezione(Proiezione proiezione) {

		if (proiezione == null) {
			return false;
		}

		LocalDateTime dataOraNuova = proiezione.getDataOra();

		for (Proiezione proiezioneEsistente : proiezioni) {

			LocalDateTime dataOraEsistente = proiezioneEsistente.getDataOra();

			if (dataOraEsistente.equals(dataOraNuova)) {

				return false;
			}
		}

		proiezioni.add(proiezione);

		proiezioniRepository.salvaProiezione(proiezione);

		return true;
	}

	/**
	 * Elimina una proiezione se è passata e non ha prenotazioni.
	 *
	 * @param proiezioneDaEliminare proiezione da eliminare.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	public boolean eliminaProiezione(Proiezione proiezioneDaEliminare) {

		if (proiezioneDaEliminare == null) {
			return false;
		}

		LocalDate oggi = LocalDate.now();

		LocalDate dataProiezione = proiezioneDaEliminare.getDataOra().toLocalDate();

		if (!dataProiezione.isAfter(oggi)) {
			return false;
		}

		for (Prenotazione prenotazione : prenotazioni) {

			boolean stessoTitolo = prenotazione.getProiezione().getFilm().getTitolo()
					.equalsIgnoreCase(proiezioneDaEliminare.getFilm().getTitolo());

			boolean stessaData = prenotazione.getProiezione().getDataOra().equals(proiezioneDaEliminare.getDataOra());

			if (stessoTitolo && stessaData) {

				return false;
			}
		}

		boolean rimossa = false;

		for (int i = 0; i < proiezioni.size(); i++) {

			Proiezione proiezione = proiezioni.get(i);

			if (proiezione.getFilm().getTitolo().equalsIgnoreCase(proiezioneDaEliminare.getFilm().getTitolo())
					&& proiezione.getDataOra().equals(proiezioneDaEliminare.getDataOra())) {

				proiezioni.remove(i);
				rimossa = true;
				break;
			}
		}

		if (rimossa) {
			proiezioniRepository.salvaProiezioni(proiezioni);
		}

		return rimossa;
	}

	/**
	 * Modifica la data e l’ora di una proiezione se le date della proiezione da
	 * modificare e selezionata sono > della data di oggi.
	 *
	 * @param proiezioneDaModificare proiezione da modificare.
	 * @param nuovaDataOra           nuova data e ora della proiezione.
	 *
	 * @return true se l’operazione ha esito positivo, false altrimenti.
	 */
	public boolean modificaDataProiezione(Proiezione proiezioneDaModificare, LocalDateTime nuovaDataOra) {

		if (proiezioneDaModificare == null || nuovaDataOra == null) {
			return false;
		}

		LocalDate oggi = LocalDate.now();

		LocalDate dataAttuale = proiezioneDaModificare.getDataOra().toLocalDate();

		if (!dataAttuale.isAfter(oggi)) {
			return false;
		}

		for (Prenotazione prenotazione : prenotazioni) {

			boolean stessoTitolo = prenotazione.getProiezione().getFilm().getTitolo()
					.equalsIgnoreCase(proiezioneDaModificare.getFilm().getTitolo());

			boolean stessaData = prenotazione.getProiezione().getDataOra().equals(proiezioneDaModificare.getDataOra());

			if (stessoTitolo && stessaData) {

				return false;
			}
		}

		for (Proiezione proiezione : proiezioni) {

			if (proiezione.getDataOra().equals(nuovaDataOra)) {

				return false;
			}
		}

		for (int i = 0; i < proiezioni.size(); i++) {

			Proiezione proiezione = proiezioni.get(i);

			boolean stessoTitolo = proiezione.getFilm().getTitolo()
					.equalsIgnoreCase(proiezioneDaModificare.getFilm().getTitolo());

			boolean stessaData = proiezione.getDataOra().equals(proiezioneDaModificare.getDataOra());

			if (stessoTitolo && stessaData) {

				Proiezione proiezioneModificata = new Proiezione(proiezione.getFilm(), nuovaDataOra,
						proiezione.getCostoBiglietto());

				proiezioni.set(i, proiezioneModificata);

				proiezioniRepository.salvaProiezioni(proiezioni);

				return true;
			}
		}

		return false;
	}

}
package service;

import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDate;

import model.Prenotazione;
import model.Proiezione;
import model.Utente;
import repository.PrenotazioniRepository;

public class PrenotazioneService {

	private ArrayList<Prenotazione> prenotazioni;
	private PrenotazioniRepository prenotazioniRepository;
	private ProiezioneService proiezioneService;

	public PrenotazioneService(ArrayList<Prenotazione> prenotazioni, PrenotazioniRepository prenotazioniRepository,
			ProiezioneService proiezioneService) {

		this.prenotazioni = prenotazioni;
		this.prenotazioniRepository = prenotazioniRepository;
		this.proiezioneService = proiezioneService;
	}

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

	private String generaCodicePrenotazione() {

		return "PREN-" + UUID.randomUUID().toString().substring(0, 8);
	}
	
	public ArrayList<Prenotazione> getPrenotazioniUtente(Utente utente) {

	    ArrayList<Prenotazione> risultati = new ArrayList<>();

	    for (Prenotazione prenotazione : prenotazioni) {

	        if (prenotazione.getUsername()
	                .equalsIgnoreCase(utente.getUsername())) {

	            risultati.add(prenotazione);
	        }
	    }

	    return risultati;
	}
	
	public boolean eliminaPrenotazione(Prenotazione prenotazioneDaEliminare) {

	    if (prenotazioneDaEliminare == null) {
	        return false;
	    }

	    LocalDate dataProiezione =
	            prenotazioneDaEliminare
	                    .getProiezione()
	                    .getDataOra()
	                    .toLocalDate();

	    LocalDate oggi = LocalDate.now();

	    if (!dataProiezione.isAfter(oggi)) {
	        return false;
	    }

	    boolean rimossa =
	            prenotazioni.removeIf(
	                    prenotazione ->
	                            prenotazione.getCodicePrenotazione()
	                                    .equals(
	                                            prenotazioneDaEliminare
	                                                    .getCodicePrenotazione()
	                                    )
	            );

	    if (rimossa) {
	        prenotazioniRepository.salvaPrenotazioni(prenotazioni);
	    }

	    return rimossa;
	}
}
package service;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Prenotazione;
import model.Proiezione;

public class ProiezioneService {

	private ArrayList<Proiezione> proiezioni;
	private ArrayList<Prenotazione> prenotazioni;

	private static final int CAPIENZA_SALA = 200;

	public ProiezioneService(ArrayList<Proiezione> proiezioni,
	                         ArrayList<Prenotazione> prenotazioni) {
	    this.proiezioni = proiezioni;
	    this.prenotazioni = prenotazioni;
	}

    public ArrayList<Proiezione> cercaProiezioni(String titolo,
                                                 String genere,
                                                 LocalDate dataDa,
                                                 LocalDate dataA,
                                                 Double costoMin,
                                                 Double costoMax) {

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
    
    public void visualizzaProiezione(Proiezione proiezione) {

        System.out.println("===== DETTAGLIO PROIEZIONE =====");

        System.out.println("Titolo: " + proiezione.getFilm().getTitolo());
        System.out.println("Genere: " + proiezione.getFilm().getGenere());
        System.out.println("Regista: " + proiezione.getFilm().getRegista());
        System.out.println("Anno: " + proiezione.getFilm().getAnno());
        System.out.println("Durata: " + proiezione.getFilm().getDurata() + " minuti");

        System.out.println("Data e ora: " + proiezione.getDataOra());
        System.out.println("Costo biglietto: " + proiezione.getCostoBiglietto() + "€");
        System.out.println("Posti disponibili: " + proiezione.getPostiDisponibili());
    }
    
    public int getPostiDisponibili(Proiezione proiezione) {

        int postiPrenotati = 0;

        for (Prenotazione prenotazione : prenotazioni) {

            boolean stessoFilm =
                    prenotazione.getProiezione().getFilm().getTitolo()
                            .equalsIgnoreCase(proiezione.getFilm().getTitolo());

            boolean stessaDataOra =
                    prenotazione.getProiezione().getDataOra()
                            .equals(proiezione.getDataOra());

            if (stessoFilm && stessaDataOra) {

                postiPrenotati += prenotazione.getNumeroPostiPrenotati();
            }
        }

        return CAPIENZA_SALA - postiPrenotati;
    }
}
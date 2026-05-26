package model;

public class Prenotazione {

    private String codicePrenotazione;
    private Proiezione proiezione;
    private int numeroPostiPrenotati;
    private Utente utente;

    public Prenotazione(String codicePrenotazione,
                         Proiezione proiezione,
                         int numeroPostiPrenotati,
                         Utente utente) {

        this.codicePrenotazione = codicePrenotazione;
        this.proiezione = proiezione;
        this.numeroPostiPrenotati = numeroPostiPrenotati;
        this.utente = utente;

    }

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public Proiezione getProiezione() {
        return proiezione;
    }

    public int getNumeroPostiPrenotati() {
        return numeroPostiPrenotati;
    }

    public Utente getUtente() {
        return utente;
    }

}
/**
 * Autore: Reggiori Emanuele
 * Matricola: 750948
 * Sede: VA
 */

package model;

import java.time.LocalDateTime;

public class Proiezione {

    private Film film;
    private LocalDateTime dataOra;
    private double costoBiglietto;

    public Proiezione(Film film,
                      LocalDateTime dataOra,
                      double costoBiglietto) {

        this.film = film;
        this.dataOra = dataOra;
        this.costoBiglietto = costoBiglietto;

    }

    public Film getFilm() {
        return film;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public double getCostoBiglietto() {
        return costoBiglietto;
    }
    

}
package model;

import java.time.LocalDate;

public class Utente {

    private String nome;
    private String cognome;
    private String username;
    private String password;
    private LocalDate dataNascita;
    private String domicilio;
    private Ruolo ruolo;

    public Utente(String nome,
                   String cognome,
                   String username,
                   String password,
                   LocalDate dataNascita,
                   String domicilio,
                   Ruolo ruolo) {

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
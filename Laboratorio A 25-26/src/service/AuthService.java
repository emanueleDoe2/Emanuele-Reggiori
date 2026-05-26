package service;

import java.util.ArrayList;

import model.Ruolo;
import model.Utente;
import repository.UserRepository;

public class AuthService {

    private ArrayList<Utente> utenti;
    private UserRepository utenteRepository;

    public AuthService(ArrayList<Utente> utenti, UserRepository utenteRepository) {

        this.utenti = utenti;
        this.utenteRepository = utenteRepository;

    }

    public Utente login(String username, String password) {

        for (Utente utente : utenti) {

            if (utente.getUsername().equals(username) &&
                utente.getPassword().equals(password)) {

                return utente;

            }

        }

        return null;
    }

    public boolean usernameEsiste(String username) {

        for (Utente utente : utenti) {

            if (utente.getUsername().equals(username)) {

                return true;

            }

        }

        return false;
    }

    public boolean registraCliente(String nome,
                                   String cognome,
                                   String username,
                                   String password,
                                   String dataNascita,
                                   String domicilio) {

        if (usernameEsiste(username)) {

            return false;

        }

        Utente nuovoUtente = new Utente(
                nome,
                cognome,
                username,
                password,
                dataNascita,
                domicilio,
                Ruolo.CLIENTE
        );

        utenti.add(nuovoUtente);
        utenteRepository.salvaUtente(nuovoUtente);

        return true;
    }

}
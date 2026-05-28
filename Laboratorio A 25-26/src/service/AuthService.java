package service;

import java.util.ArrayList;
import java.time.LocalDate;

import model.Ruolo;
import model.Utente;
import repository.UserRepository;
import util.PasswordUtil;

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
                utente.getPassword().equals(PasswordUtil.hashPassword(password))) {

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
                                   LocalDate dataNascita,
                                   String domicilio) {

        if (usernameEsiste(username)) {

            return false;

        }
        
        String PasswordCifrata = PasswordUtil.hashPassword(password);

        Utente nuovoUtente = new Utente(
                nome,
                cognome,
                username,
                PasswordCifrata,
                dataNascita,
                domicilio,
                Ruolo.CLIENTE
        );

        utenti.add(nuovoUtente);
        utenteRepository.salvaUtente(nuovoUtente);

        return true;
    }

}
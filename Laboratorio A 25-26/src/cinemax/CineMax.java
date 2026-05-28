package cinemax;

import java.util.ArrayList;

import service.AuthService;
import service.ProiezioneService;
import service.PrenotazioneService;
import model.Prenotazione;
import model.Proiezione;
import model.Utente;
import repository.PrenotazioniRepository;
import repository.ProiezioniRepository;
import repository.UserRepository;
import ui.MenuPrincipale;

public class CineMax {

    public static void main(String[] args) {

        UserRepository utenteRepository = new UserRepository();
        ArrayList<Utente> utenti = utenteRepository.caricaUtenti();

        ProiezioniRepository proiezioneRepository = new ProiezioniRepository();
        ArrayList<Proiezione> proiezioni = proiezioneRepository.caricaProiezioni();

        PrenotazioniRepository prenotazioneRepository = new PrenotazioniRepository();
        ArrayList<Prenotazione> prenotazioni = prenotazioneRepository.caricaPrenotazioni();
        
        AuthService authService = new AuthService(utenti, utenteRepository);
        ProiezioneService proiezioneService = new ProiezioneService(proiezioni, prenotazioni);
        PrenotazioneService prenotazioneService = new PrenotazioneService(prenotazioni, prenotazioneRepository, proiezioneService);

        MenuPrincipale menu = new MenuPrincipale(utenti, proiezioni, prenotazioni, authService, proiezioneService, prenotazioneService);
        menu.start();

    }

}
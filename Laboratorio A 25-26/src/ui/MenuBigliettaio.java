package ui;

import java.util.Scanner;

import model.Utente;
import service.AuthService;
import service.ProiezioneService;

public class MenuBigliettaio {

    private Scanner scanner;
    private Utente utente;
    private AuthService authservice;
    private ProiezioneService proiezioneservice;

    public MenuBigliettaio(Scanner scanner,
                            Utente utente,
                            AuthService authservice,
                            ProiezioneService proiezioneservice) {

        this.scanner = scanner;
        this.utente = utente;
        this.authservice = authservice;
        this.proiezioneservice = proiezioneservice;
    }

    public void start() {

    }
}
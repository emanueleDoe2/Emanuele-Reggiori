package ui;

import java.util.Scanner;
import java.time.LocalDate;

import service.AuthService;
import util.InputUtil;


public class UIRegistrazione {

    private Scanner scanner;
    private AuthService authservice;

    public UIRegistrazione(Scanner scanner,
                             AuthService authservice) {

        this.scanner = scanner;
        this.authservice = authservice;
    }

    public void start() {

        System.out.println("===== REGISTRAZIONE CLIENTE =====");

        String nome =
                InputUtil.leggiStringaObbligatoria(
                        scanner,
                        "Nome: ",
                        "Il nome è obbligatorio"
                );

        String cognome =
                InputUtil.leggiStringaObbligatoria(
                        scanner,
                        "Cognome: ",
                        "Il cognome è obbligatorio"
                );

        String username =
                InputUtil.leggiStringaObbligatoria(
                        scanner,
                        "Username: ",
                        "Lo username è obbligatorio"
                );

        String password = 
                InputUtil.leggiStringaObbligatoria(
                        scanner,
                        "Password: ",
                        "La password è obbligatoria"
                );

     
        LocalDate dataNascita = 
        		InputUtil.leggiDataFacoltativa(scanner, "Data di nascita facoltativa\nFormato YYYY-MM-DD: ");

        String domicilio =
                InputUtil.leggiStringaObbligatoria(
                        scanner,
                        "Domicilio: ",
                        "Il domicilio è obbligatorio"
                );

        boolean registrato =
                authservice.registraCliente(
                        nome,
                        cognome,
                        username,
                        password,
                        dataNascita,
                        domicilio
                );

        if (registrato) {

            System.out.println("Registrazione completata");

        } else {

            System.out.println(
                    "Registrazione fallita - username già esistente"
            );
        }
    }
}
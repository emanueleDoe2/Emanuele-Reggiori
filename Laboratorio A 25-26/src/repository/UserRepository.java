package repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

import model.Ruolo;
import model.Utente;

public class UserRepository {

    private String percorsoFile = "data/utenti.csv";

    public ArrayList<Utente> caricaUtenti() {

        ArrayList<Utente> utenti = new ArrayList<Utente>();

        try {

            File file = new File(percorsoFile);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String riga = scanner.nextLine();
                String[] campi = riga.split(",");

                String nome = campi[0];
                String cognome = campi[1];
                String username = campi[2];
                String password = campi[3];
                //parsing data di nascita
                LocalDate dataNascita = null;

                String data = campi[4].trim();

                if (!data.isEmpty() && !data.equalsIgnoreCase("null")) {

                    dataNascita = LocalDate.parse(data);
                }
                String domicilio = campi[5];
                Ruolo ruolo = Ruolo.valueOf(campi[6]);

                Utente utente = new Utente(
                        nome,
                        cognome,
                        username,
                        password,
                        dataNascita,
                        domicilio,
                        ruolo
                );

                utenti.add(utente);
            }

            scanner.close();

        } catch (IOException e) {

            System.out.println("Errore durante il caricamento degli utenti.");

        }

        return utenti;
    }

    public void salvaUtente(Utente utente) {

        try {

            FileWriter writer = new FileWriter(percorsoFile, true);

            writer.write(
                    utente.getNome() + "," +
                    utente.getCognome() + "," +
                    utente.getUsername() + "," +
                    utente.getPassword() + "," +
                    utente.getDataNascita() + "," +
                    utente.getDomicilio() + "," +
                    utente.getRuolo()
            );

            writer.write(System.lineSeparator());
            writer.close();

        } catch (IOException e) {

            System.out.println("Errore durante il salvataggio dell'utente.");

        }
    }
}
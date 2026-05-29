CineMax - Laboratorio Interdisciplinare A
A.A. 2025/2026

Progetto Java per la gestione di un piccolo cinema monosala.

Il progetto è stato sviluppato e testato con Java 21.


REQUISITI

- JDK 21
- Terminale oppure IDE Java, ad esempio Eclipse


STRUTTURA DEL PROGETTO

- src/
  Codice sorgente Java.

- bin/
  File eseguibile:
  cinemax.jar

- data/
  Directory utilizzata dall'applicazione per i file CSV.
  Il file proiezioni.csv viene creato automaticamente dal programma se non presente.
  Gli altri file CSV utilizzati sono:
  utenti.csv
  prenotazioni.csv

  Mentre gli utenti di tipo cliente possono essere creati da programma, gli utenti di tipo bigliettaio e proiezionista si suppone possano essere creati solo da admin/server side. 
  Delle utenze di prova sono le seguenti:
  Bigliettaio -> username: averdi psw: anna123
  Proiezionisti -> username: mrossi psw: pippo123

- doc/
  Manuale utente, manuale tecnico e Javadoc.

- autori.txt
  Dati degli autori.

- README.txt
  Istruzioni di esecuzione e compilazione.


ESECUZIONE

Aprire il terminale nella cartella radice del progetto ed eseguire:
java -jar bin/cinemax.jar
Esempio di cartella corretta:
Laboratorio A 25-26/
Il programma deve essere avviato dalla cartella radice perché utilizza la directory data/ tramite percorsi relativi.

NOTE
Il main dell'applicazione si trova nella classe:
cinemax.CineMax

Il progetto non utilizza librerie esterne.
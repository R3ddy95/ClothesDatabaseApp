import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        List<Utente> utenti = CsvLoader.caricaUtentiDaCsv("./csv/utenti.csv");
        List<Capo> capi = CsvLoader.caricaCapiDaCsv("./csv/capi.csv");
        List<Vendita> vendite = CsvLoader.caricaVenditeDaCsv("./csv/vendite.csv");

        Scanner scanner = new Scanner(System.in);
        int scelta;

        do {
            // Mostra le opzioni
            System.out.println("Scegli un'operazione:");
            System.out.println("1. Visualizza capi second hand");
            System.out.println("2. Compra un capo");
            System.out.println("3. Restituisci un capo");
            System.out.println("4. Aggiungi nuovo utente");
            System.out.println("5. Esporta capi disponibili");
            System.out.println("0. Esci");

            scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma la nuova linea rimasta nel buffer

            switch (scelta) {
                case 1:
                    System.out.println("Capi second hand disponibili:");
                    for (Capo capo : capi) {
                        if (capo.isDisponibile()) {
                            System.out.println(capo.getId() + " - " + capo.getMarca() + " " + capo.getTipologia() + " - " + capo.getPrezzo() + " EUR");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Inserisci l'ID del capo che vuoi acquistare:");
                    int idCapoDaAcquistare = scanner.nextInt();
                    scanner.nextLine(); // Consuma la nuova linea rimasta nel buffer

                    Capo capoAcquistato = null;
                    for (Capo capo : capi) {
                        if (capo.getId() == idCapoDaAcquistare && capo.isDisponibile()) {
                            capoAcquistato = capo;
                            break;
                        }
                    }

                    if (capoAcquistato != null) {
                        System.out.println("Hai acquistato il seguente capo:");
                        System.out.println("ID: " + capoAcquistato.getId());
                        System.out.println("Tipologia: " + capoAcquistato.getTipologia());
                        System.out.println("Marca: " + capoAcquistato.getMarca());
                        System.out.println("Prezzo: " + capoAcquistato.getPrezzo() + " EUR");

                        capoAcquistato.setDisponibile(false);
                        vendite.add(new Vendita(vendite.size() + 1, capoAcquistato.getId(), 1)); // Supponiamo ID utente 1
                    } else {
                        System.out.println("Capo non disponibile o ID non valido.");
                    }
                    break;
                case 3:
                    System.out.println("Inserisci l'ID della vendita da restituire:");
                    int idVenditaDaRestituire = scanner.nextInt();
                    scanner.nextLine(); // Consuma la nuova linea rimasta nel buffer

                    Vendita venditaDaRestituire = null;
                    for (Vendita vendita : vendite) {
                        if (vendita.getId() == idVenditaDaRestituire) {
                            venditaDaRestituire = vendita;
                            break;
                        }
                    }

                    if (venditaDaRestituire != null) {
                        for (Capo capo : capi) {
                            if (capo.getId() == venditaDaRestituire.getIdCapo()) {
                                capo.setDisponibile(true);
                                vendite.remove(venditaDaRestituire);
                                System.out.println("Restituzione effettuata con successo.");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Vendita non trovata.");
                    }
                    break;
                case 4:
                    System.out.println("Inserisci i dati del nuovo utente:");
                    System.out.print("ID: ");
                    int nuovoUtenteId = scanner.nextInt();
                    scanner.nextLine(); // Consuma la nuova linea rimasta nel buffer
                    System.out.print("Nome: ");
                    String nuovoUtenteNome = scanner.nextLine();
                    System.out.print("Cognome: ");
                    String nuovoUtenteCognome = scanner.nextLine();
                    System.out.print("Data di nascita (YYYY-MM-DD): ");
                    LocalDate nuovoUtenteDataNascita = LocalDate.parse(scanner.nextLine());
                    System.out.print("Indirizzo: ");
                    String nuovoUtenteIndirizzo = scanner.nextLine();
                    System.out.print("Documento ID: ");
                    String nuovoUtenteDocumentoId = scanner.nextLine();

                    Utente nuovoUtente = new Utente(nuovoUtenteId, nuovoUtenteNome, nuovoUtenteCognome,
                            nuovoUtenteDataNascita, nuovoUtenteIndirizzo, nuovoUtenteDocumentoId);
                    utenti.add(nuovoUtente);
                    System.out.println("Nuovo utente aggiunto con successo.");
                    break;
                case 5:
                    esportaCapiDisponibili(capi);
                    System.out.println("Esportazione completata.");
                    break;
                case 0:
                    System.out.println("Uscita dal programma.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        } while (scelta != 0);

        scanner.close();
    }

    private static void esportaCapiDisponibili(List<Capo> capi) {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        String fileName = "capi_" + formattedDate + ".csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("ID;Data Inserimento;Tipologia;Marca;Taglia;Prezzo\n");
            for (Capo capo : capi) {
                if (capo.isDisponibile()) {
                    writer.write(capo.getId() + ";" + capo.getDataInserimento() + ";" + capo.getTipologia() +
                            ";" + capo.getMarca() + ";" + capo.getTaglia() + ";" + capo.getPrezzo() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


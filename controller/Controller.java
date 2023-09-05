package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import model.Capo;
import model.Utente;
import model.Vendita;
import server.CsvLoader;

public class Controller {
    private List<Utente> utenti;
    private List<Capo> capi;
    private List<Vendita> vendite;
    private Scanner scanner;

    // Constructor
    public Controller() {
        utenti = CsvLoader.caricaUtentiDaCsv("./csv/utenti.csv");
        capi = CsvLoader.caricaCapiDaCsv("./csv/capi.csv");
        vendite = CsvLoader.caricaVenditeDaCsv("./csv/vendite.csv");
        scanner = new Scanner(System.in);
    }

    // Method to display available options to the user
    public void mostraOpzioni() {
        System.out.println("Choose an operation (Digit the number):");
        System.out.println("1. View second hand clothing");
        System.out.println("2. Buy an item of clothing");
        System.out.println("3. Return an item of clothing");
        System.out.println("4. Add new user");
        System.out.println("5. Export available clothing items");
        System.out.println("0. Exit");
    }

    // Method to execute the operation chosen by the user
    public void eseguiOperazione(int scelta) {
        switch (scelta) {
            case 1:
                visualizzaCapiDisponibili();
                break;
            case 2:
                eseguiAcquisto();
                break;
            case 3:
                restituisciCapo();
                break;
            case 4:
                aggiungiNuovoUtente();
                break;
            case 5:
                esportaCapiDisponibili();
                break;
            case 0:
                System.out.println("Successful exit from the program.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to display available clothing items
    private void visualizzaCapiDisponibili() {
        System.out.println("List of items available:");
        System.out.println("ID - Typology - Brand - Date - In Inventory Since - Place - Available");
        for (Capo capo : capi) {
            String disponibile = capo.isDisponibile() ? "YES" : "NO";
            System.out.println(capo.getId() + " - " + capo.getTipologia() + " - " + capo.getMarca() +
                   " - " + capo.getDataInserimento() + " - " + capo.getDurata() + " Days" + " - " + "Naples" + " - " + disponibile);
    }
    }

    // Method to reserve a clothing item for purchase
    private void eseguiAcquisto() {
        System.out.println("Enter the ID of the item of clothing you want to reserve:");
        int idCapoDaPrenotare = scanner.nextInt();
        scanner.nextLine();
    
        Capo capoDaPrenotare = null;
        for (Capo capo : capi) {
            if (capo.getId() == idCapoDaPrenotare && capo.isDisponibile()) {
                capoDaPrenotare = capo;
                break;
            }
        }
    
        if (capoDaPrenotare != null) {
            // Show the information of the selected clothing item
            System.out.println("ID: " + capoDaPrenotare.getId());
            System.out.println("Insertion Date: " + capoDaPrenotare.getDataInserimento());
            System.out.println("Typology: " + capoDaPrenotare.getTipologia());
            System.out.println("Brand: " + capoDaPrenotare.getMarca());
            System.out.println("Size: " + capoDaPrenotare.getTaglia());
            System.out.println("Price: " + capoDaPrenotare.getPrezzo() + " EUR");
            System.out.println("Available: " + (capoDaPrenotare.isDisponibile() ? "SI" : "NO"));
    
            // Generate a new ID for the sale
            int nuovoIdVendita = vendite.size() + 1;
    
            // Create a new sale record
            Vendita nuovaVendita = new Vendita(nuovoIdVendita, capoDaPrenotare.getId(), 1); // Suppose user ID 1
            vendite.add(nuovaVendita);
    
            // Update the availability status of the clothing item
            capoDaPrenotare.setDisponibile(false);
    
            System.out.println("Reservation made successfully. Sales ID: " + nuovoIdVendita);
        } else {
            System.out.println("Item not available or invalid ID.");
        }
    }

    // Method to return a clothing item
    private void restituisciCapo() {
        System.out.println("Enter the ID of the sale to return:");
        int idVenditaDaRestituire = scanner.nextInt();
        scanner.nextLine();

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
                    System.out.println("Return made successfully.");
                    break;
                }
            }
        } else {
            System.out.println("Sale not found.");
        }
    }

    // Method to add a new user
    private void aggiungiNuovoUtente() {
        System.out.println("Enter the data of the new user:");
        System.out.print("ID: ");
        int nuovoUtenteId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Name: ");
        String nuovoUtenteNome = scanner.nextLine();
        System.out.print("Surname: ");
        String nuovoUtenteCognome = scanner.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        LocalDate nuovoUtenteDataNascita = LocalDate.parse(scanner.nextLine());
        System.out.print("Adress: ");
        String nuovoUtenteIndirizzo = scanner.nextLine();
        System.out.print("Document ID: ");
        String nuovoUtenteDocumentoId = scanner.nextLine();

        Utente nuovoUtente = new Utente(nuovoUtenteId, nuovoUtenteNome, nuovoUtenteCognome,
                nuovoUtenteDataNascita, nuovoUtenteIndirizzo, nuovoUtenteDocumentoId);
        utenti.add(nuovoUtente);
        System.out.println("New user successfully added.");
    }

    // Method to export available clothing items
    private void esportaCapiDisponibili() {
        esportaCapiDisponibili(capi);
        System.out.println("Export completed.");
    }

    // Static method to export clothing items to a CSV file
    private static void esportaCapiDisponibili(List<Capo> capi) {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        String fileName = "capi_" + formattedDate + ".csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("ID;Insertion Date;Typology;Brand;Size;Price\n");
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
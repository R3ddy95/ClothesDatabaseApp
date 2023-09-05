package server;

import model.Capo;
import model.Utente;
import model.Vendita;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    // Load users from the csv file csv/utenti.csv
    public static List<Utente> caricaUtentiDaCsv(String fileName) {
        List<Utente> utenti = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                int id = Integer.parseInt(data[0]);
                String nome = data[1];
                String cognome = data[2];
                LocalDate dataNascita = LocalDate.parse(data[3]);
                String indirizzo = data[4];
                String documentoId = data[5];
                Utente utente = new Utente(id, nome, cognome, dataNascita, indirizzo, documentoId);
                utenti.add(utente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return utenti;
    }

    // Load clothing items from the csv file csv/capi.csv
    public static List<Capo> caricaCapiDaCsv(String fileName) {
        List<Capo> capi = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                int id = Integer.parseInt(data[0]);
                LocalDate dataInserimento = LocalDate.parse(data[1]);
                String tipologia = data[2];
                String marca = data[3];
                String taglia = data[4];
                double prezzo = Double.parseDouble(data[5]);
                boolean disponibile = data[6].equalsIgnoreCase("SI");
                Capo capo = new Capo(id, dataInserimento, tipologia, marca, taglia, prezzo, disponibile);
                capi.add(capo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return capi;
    }

    // Load sales records from the csv file csv/vendite.csv
    public static List<Vendita> caricaVenditeDaCsv(String fileName) {
        List<Vendita> vendite = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                int id = Integer.parseInt(data[0]);
                int idCapo = Integer.parseInt(data[1]);
                int idUtente = Integer.parseInt(data[2]);
                Vendita vendita = new Vendita(id, idCapo, idUtente);
                vendite.add(vendita);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vendite;
    }
}
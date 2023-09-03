package app;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Capo {
    private int id;
    private LocalDate dataInserimento;
    private String tipologia;
    private String marca;
    private String taglia;
    private double prezzo;
    private boolean disponibile;

    public Capo(int id, LocalDate dataInserimento, String tipologia, String marca, String taglia, double prezzo, boolean disponibile) {
        this.id = id;
        this.dataInserimento = dataInserimento;
        this.tipologia = tipologia;
        this.marca = marca;
        this.taglia = taglia;
        this.prezzo = prezzo;
        this.disponibile = disponibile;
    }

    // Metodi getter e setter per ogni campo
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(LocalDate dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTaglia() {
        return taglia;
    }

    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

        public long getDurata() {
        LocalDate dataInserimento = getDataInserimento();
        LocalDate dataAttuale = LocalDate.now();
        
        long durataInGiorni = ChronoUnit.DAYS.between(dataInserimento, dataAttuale);
        return durataInGiorni;
    }
}

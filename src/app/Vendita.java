package app;

public class Vendita {
    private int id;
    private int idCapo;
    private int idUtente;

    public Vendita(int id, int idCapo, int idUtente) {
        this.id = id;
        this.idCapo = idCapo;
        this.idUtente = idUtente;
    }

    // Metodi getter e setter per ogni campo
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCapo() {
        return idCapo;
    }

    public void setIdCapo(int idCapo) {
        this.idCapo = idCapo;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }
}

package fr.univcours.api.Models;

public class Categorie_Plat {
    private int id;
    private int id_plat;
    private int id_categorie;

    public Categorie_Plat(){

    }

    public Categorie_Plat(int id, int id_plat, int id_categorie) {
        this.id = id;
        this.id_plat = id_plat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_plat() {
        return id_plat;
    }

    public void setId_plat(int id_plat) {
        this.id_plat = id_plat;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }
}

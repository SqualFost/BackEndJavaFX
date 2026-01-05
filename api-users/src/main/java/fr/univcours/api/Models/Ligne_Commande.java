package fr.univcours.api.Models;

public class Ligne_Commande {
    private int id;
    private int id_commande;
    private int id_plat;
    private int qualite;
    private String options_choisies;

    public Ligne_Commande(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public int getId_plat() {
        return id_plat;
    }

    public void setId_plat(int id_plat) {
        this.id_plat = id_plat;
    }

    public int getQualite() {
        return qualite;
    }

    public void setQualite(int qualite) {
        this.qualite = qualite;
    }

    public String getOptions_choisies() {
        return options_choisies;
    }

    public void setOptions_choisies(String options_choisies) {
        this.options_choisies = options_choisies;
    }
}

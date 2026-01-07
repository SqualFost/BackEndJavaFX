package fr.univcours.api.Models;

public class Commande {
    private int id;
    private String heure_commande;
    private int prix_total;
    private String statut;
    private int id_utilisateur;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getHeure_commande() {
        return heure_commande;
    }

    public void setHeure_commande(String heure_commande) {
        this.heure_commande = heure_commande;
    }

    public int getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(int prix_total) {
        this.prix_total = prix_total;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }


}

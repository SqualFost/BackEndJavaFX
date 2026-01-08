package fr.univcours.api.code_gener.Models;




public class Commande {

    public int id;


    public String heure_commande;


    public int prix_total;


    public String statut;


    public int id_utilisateur;


    public int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id;
    }


    public void setId(final int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id = value;
    }


    public String getHeure_commande() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.heure_commande;
    }


    public void setHeure_commande(final String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.heure_commande = value;
    }


    public int getPrix_total() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.prix_total;
    }


    public void setPrix_total(final int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.prix_total = value;
    }


    public String getStatut() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.statut;
    }


    public void setStatut(final String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.statut = value;
    }


    public int getId_utilisateur() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id_utilisateur;
    }


    public void setId_utilisateur(final int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id_utilisateur = value;
    }

}

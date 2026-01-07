package fr.univcours.api.Models;

/**
 * Classe représentant un utilisateur
 */
public class User {
    private int id;
    private String nom;
    private int nbPoints;

    // Getters et Setters (nécessaires pour la conversion JSON)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }
}
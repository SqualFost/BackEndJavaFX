package fr.univcours.api.Models;

/**
 * Classe représentant un utilisateur
 */
public class User {
    private int id;
    private String name;
    private int nbPoints;

    // Constructeur par défaut (nécessaire pour Jackson)
    public User() {
    }

    // Constructeur avec paramètres
    public User(int id, String name, int nbPoints) {
        this.id = id;
        this.name = name;
        this.nbPoints = nbPoints;
    }

    // Getters et Setters (nécessaires pour la conversion JSON)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }
}
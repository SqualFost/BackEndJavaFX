package fr.univcours.api.Models;

public class Plat {
    private int id;
    private String name;
    private String description;
    private float prix;
    private String photoUrl;
    private int quantite;

    public Plat(){

    }

    public Plat(int id, String name, String description, float prix, String photoUrl, int quantite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.photoUrl = photoUrl;
        this.quantite = quantite;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

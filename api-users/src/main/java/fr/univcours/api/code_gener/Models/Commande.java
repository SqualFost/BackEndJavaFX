package fr.univcours.api.code_gener.Models;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("fb5c8896-1a86-44a0-852c-c7520301d925")
public class Commande {
    @objid ("064cdba9-185a-4713-adbc-c21118a92794")
    public int id;

    @objid ("d0058630-1462-4941-b45c-4833a8add878")
    public String heure_commande;

    @objid ("5a0bbad4-4ad8-4c54-93fe-05dc8226592d")
    public int prix_total;

    @objid ("0fcee181-093d-4c58-907d-fe76a8ebe4a0")
    public String statut;

    @objid ("0a3c807d-656c-4ae9-931b-2c2eb7e14dd1")
    public int id_utilisateur;

    @objid ("761a00ff-8764-4e43-9431-05bf41773ebe")
    public int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id;
    }

    @objid ("a14a7f06-6bfa-412c-aee2-34529faa10ad")
    public void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id = value;
    }

    @objid ("c06b6166-7527-4425-a1c5-987e0a5fdd50")
    public String getHeure_commande() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.heure_commande;
    }

    @objid ("1fd1dc29-4c79-47e8-b29a-d2bc52918881")
    public void setHeure_commande(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.heure_commande = value;
    }

    @objid ("b22929a6-9639-44f0-882d-1b3669a55360")
    public int getPrix_total() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.prix_total;
    }

    @objid ("7b1a3a18-fe95-4722-a7b3-b52b58e0675a")
    public void setPrix_total(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.prix_total = value;
    }

    @objid ("b6ec197e-c6e6-4186-b696-8f276100a53c")
    public String getStatut() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.statut;
    }

    @objid ("6d502810-34ad-4c90-84f0-d0a771a2703d")
    public void setStatut(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.statut = value;
    }

    @objid ("aac38f19-2446-4b04-984c-85000cb1e110")
    public int getId_utilisateur() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id_utilisateur;
    }

    @objid ("86430f0f-7388-4524-b8c1-f532d6630e28")
    public void setId_utilisateur(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id_utilisateur = value;
    }

}

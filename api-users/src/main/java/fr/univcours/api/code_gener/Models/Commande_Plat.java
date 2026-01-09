package fr.univcours.api.code_gener.Models;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("f630e25d-971d-4ad1-be80-83a880a79b94")
public class Commande_Plat {
    @objid ("d9d5ceed-e76f-4bd5-a613-c3e48e40750a")
    public int id;

    @objid ("93dcfaef-78c1-452d-94da-097cf9e53e5d")
    public int id_commande;

    @objid ("179288d3-c63f-41ab-ac77-655de875673d")
    public int id_plat;

    @objid ("26524678-224f-46cc-90fe-d4bbb0904988")
    public int quantite;

    @objid ("02633a87-1a6a-4297-8ac4-445e9ff15bce")
    public String options_choisies;

    @objid ("7301f7a6-d116-4372-bfbb-ce74c6163632")
    public int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id;
    }

    @objid ("be56fc82-5ba7-46ab-8222-fdeb27735911")
    public void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id = value;
    }

    @objid ("4841e139-a321-4ab6-b9da-f8e5eca38e4c")
    public int getId_commande() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id_commande;
    }

    @objid ("4b9d6139-f36f-4884-a1a7-c9d6cdf3d24b")
    public void setId_commande(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id_commande = value;
    }

    @objid ("a0775f5f-08aa-406e-877d-18a141709b39")
    public int getId_plat() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id_plat;
    }

    @objid ("15b7a73a-3130-4feb-85d7-e6cbd89bc52f")
    public void setId_plat(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id_plat = value;
    }

    @objid ("6322796a-8f4e-42c3-81e9-37b72ce1bb7a")
    public int getQuantite() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.quantite;
    }

    @objid ("d3ce1f4c-d804-46d2-a084-57e85b2b277f")
    public void setQuantite(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.quantite = value;
    }

    @objid ("63b0951b-41be-4aac-b594-4615b9a7c091")
    public String getOptions_choisies() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.options_choisies;
    }

    @objid ("13ccc3e1-3078-4e7f-9d8a-023ad19381a7")
    public void setOptions_choisies(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.options_choisies = value;
    }

}

package fr.univcours.api.code_gener.Models;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("1ad16383-6256-400d-b361-2a0884814d6d")
public class Categorie {
    @objid ("777ba497-a997-43c8-ba4e-10ca15841a75")
    public int id;

    @objid ("0baa29ce-c805-433a-a37b-1d3183c9f56a")
    public String nom;

    @objid ("e7b9e211-cf0d-4b86-9b00-9cc1cb759e56")
    public void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id = value;
    }

    @objid ("bd3a6cc7-9663-49c9-ba4f-a2536fa94b49")
    public int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id;
    }

    @objid ("1c2a15bd-af59-4671-8cfa-e5dd93473c92")
    public String getNom() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.nom;
    }

    @objid ("b8b3368a-53a1-4b63-af58-40a524d9dc77")
    public void setNom(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.nom = value;
    }

}

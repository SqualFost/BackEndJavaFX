package fr.univcours.api.code_gener.Models;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("9c7b4a48-0ddc-4e41-846f-5fa60894d614")
public class User {
    @objid ("08eb8412-9602-406e-95ef-f1d17506cd24")
    public int id;

    @objid ("bbae2984-3d79-488b-b3c0-71b3f04586de")
    public String nom;

    @objid ("b613ebe6-4b24-4ce0-8969-f50cabfff53b")
    public int nbPoints;

    @objid ("65de55c0-ae61-4145-9e5e-d7eaf10dcd80")
    public int getId() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.id;
    }

    @objid ("8e242d49-ea1c-4134-bb4b-50130eefaf57")
    public void setId(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.id = value;
    }

    @objid ("0a5ef4a6-a3d3-431d-9910-37e18e807dcb")
    public String getNom() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.nom;
    }

    @objid ("c141d1ce-d4e0-48ec-8fc4-82bfb4bc7bce")
    public void setNom(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.nom = value;
    }

    @objid ("39492bc9-2974-469f-b181-b26f2e593289")
    public int getNbPoints() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.nbPoints;
    }

    @objid ("a3e97cef-4b90-45e9-a09f-a6f489d75af3")
    public void setNbPoints(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.nbPoints = value;
    }

}

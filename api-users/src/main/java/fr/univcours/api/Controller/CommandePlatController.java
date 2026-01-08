package fr.univcours.api.Controller;

import fr.univcours.api.code_gener.Models.Commande_Plat;
import fr.univcours.api.ServicesImpl.CommandePlatServiceImpl;
import io.javalin.http.Context;
import java.sql.SQLException;

// Controller pour gérer les endpoints liés aux lignes de commande
public class CommandePlatController {

    // Service utilisé pour les opérations sur les lignes de commande
    private static final CommandePlatServiceImpl ligneService = new CommandePlatServiceImpl();

    // Récupère toutes les lignes de commande
    public static void getAllLignes(Context ctx) {
        ctx.json(ligneService.getAllLignesCommande());
    }

    // Récupère une ligne de commande par son id
    public static void getLigneById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        ligneService.getLigneById(id)
                .ifPresentOrElse(
                        ligne -> ctx.json(ligne), // Ligne trouvée → retourne le JSON
                        () -> ctx.status(404).result("Ligne de commande non trouvée") // Sinon 404
                );
    }

    // Ajoute une nouvelle ligne de commande
    public static void addLigne(Context ctx) {
        // Récupération des données du corps de la requête
        Commande_Plat newLigne = ctx.bodyAsClass(Commande_Plat.class);

        // Création de la ligne via le service
        Commande_Plat created = ligneService.addLigne(newLigne);

        // Retourne la ligne créée avec le code 201
        ctx.status(201).json(created);
    }

    // Met à jour une ligne de commande existante
    public static void updateLigne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Commande_Plat data = ctx.bodyAsClass(Commande_Plat.class);

        // Vérifie si la ligne existe
        ligneService.getLigneById(id)
                .ifPresentOrElse(
                        ligne -> {
                            // Mise à jour des champs de la ligne
                            ligne.setId_commande(data.getId_commande());
                            ligne.setId_plat(data.getId_plat());
                            ligne.setQuantite(data.getQuantite());
                            ligne.setOptions_choisies(data.getOptions_choisies());

                            // Appel du service pour appliquer la mise à jour
                            ligneService.updateLigne(id, ligne);

                            // Retourne la ligne mise à jour
                            ctx.json(ligne);
                        },
                        () -> ctx.status(404).result("Ligne de commande non trouvée") // Sinon 404
                );
    }

    // Supprime une ligne de commande par son id
    public static void deleteLigne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));

        // Suppression via le service
        boolean suppr = ligneService.deleteLigne(id);

        // Si suppression réussie → 204 No Content, sinon 404
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Ligne de commande non trouvée");
    }
}

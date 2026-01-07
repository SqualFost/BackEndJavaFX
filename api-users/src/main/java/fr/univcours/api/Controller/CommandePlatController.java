package fr.univcours.api.Controller;

/**
 * Controller qui gère les routes liées aux lignes de commande.
 */
import fr.univcours.api.Models.Commande_Plat;
import fr.univcours.api.Services.CommandePlatService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CommandePlatController {
    // Service utilisé pour gérer les opérations sur les lignes de commande
    private static final CommandePlatService ligneService = new CommandePlatService();

    // Récupère la liste de toutes les lignes de commande
    public static void getAllLignes(Context ctx) {
        ctx.json(ligneService.getAllLignesCommande());
    }

    // Récupère une ligne de commande à partir de son id
    public static void getLigneById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Recherche de la ligne de commande correspondant à l’id
        ligneService.getLigneById(id)
                .ifPresentOrElse(
                        ligne -> ctx.json(ligne),
                        () -> ctx.status(404).result("Ligne de commande non trouvée")
                );
    }

    // Ajoute une nouvelle ligne de commande à la base de données
    public static void addLigne(Context ctx) {
        Commande_Plat newLigne = ctx.bodyAsClass(Commande_Plat.class);
        Commande_Plat created = ligneService.addLigne(newLigne);
        ctx.status(201).json(created);
    }

    // Met à jour les informations d’une ligne de commande existante
    public static void updateLigne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Commande_Plat data = ctx.bodyAsClass(Commande_Plat.class);

        // Vérifie que la ligne de commande existe avant modification
        ligneService.getLigneById(id)
                .ifPresentOrElse(
                        ligne -> {
                            // Mise à jour des champs de la ligne de commande
                            ligne.setId_commande(data.getId_commande());
                            ligne.setId_plat(data.getId_plat());
                            ligne.setQuantite(data.getQuantite());
                            ligne.setOptions_choisies(data.getOptions_choisies());

                            ligneService.updateLigne(id, ligne);
                            ctx.json(ligne);
                        },
                        () -> ctx.status(404).result("Ligne de commande non trouvée")
                );
    }

    // Supprime une ligne de commande à partir de son id
    public static void deleteLigne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Tentative de suppression de la ligne de commande
        boolean suppr = ligneService.deleteLigne(id);
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Ligne de commande non trouvée");
    }
}
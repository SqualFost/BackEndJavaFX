package fr.univcours.api.Controller;

/**
 * Controller qui gère les routes liées aux plats.
 */
import fr.univcours.api.Models.Plat;
import fr.univcours.api.Services.PlatService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class PlatController {
    // Service utilisé pour gérer les opérations sur les plats
    private static final PlatService platService = new PlatService();

    // Récupère la liste de tous les plats
    public static void getAllPlats(Context ctx) {
        ctx.json(platService.getAllPlats());
    }

    // Récupère un plat à partir de son id
    public static void getPlatById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Recherche du plat correspondant à l’id
        platService.getPlatById(id)
                .ifPresentOrElse(
                        plat -> ctx.json(plat),
                        () -> ctx.status(404).result("Plat non trouvé")
                );
    }

    // Ajoute un nouveau plat à la base de données
    public static void addPlat(Context ctx) {
        Plat newPlat = ctx.bodyAsClass(Plat.class);
        Plat created = platService.addPlat(newPlat);
        ctx.status(201).json(created);
    }

    // Met à jour les informations d’un plat existant
    public static void updatePlat(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Plat data = ctx.bodyAsClass(Plat.class);

        // Vérifie que le plat existe avant modification
        platService.getPlatById(id)
                .ifPresentOrElse(
                        plat -> {
                            // Mise à jour des champs du plat
                            plat.setNom(data.getNom());
                            plat.setDescription(data.getDescription());
                            plat.setPrix(data.getPrix());
                            plat.setPhotoUrl(data.getPhotoUrl());
                            plat.setQuantite(data.getQuantite());

                            platService.updatePlat(id, plat);
                            ctx.json(plat);
                        },
                        () -> ctx.status(404).result("Plat non trouvé")
                );
    }

    // Supprime un plat à partir de son id
    public static void deletePlat(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Tentative de suppression du plat
        boolean suppr = platService.deletePlat(id);
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Plat non trouvé");
    }
}
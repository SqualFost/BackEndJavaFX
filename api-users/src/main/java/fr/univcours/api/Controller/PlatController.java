package fr.univcours.api.Controller;

import fr.univcours.api.ServicesImpl.PlatServiceImpl;
import fr.univcours.api.code_gener.Models.Plat;
import io.javalin.http.Context;
import java.sql.SQLException;

// Controller pour gérer les endpoints liés aux plats
public class PlatController {

    // Service utilisé pour les opérations sur les plats
    private static final PlatServiceImpl platService = new PlatServiceImpl();

    // Récupère tous les plats
    public static void getAllPlats(Context ctx) {
        ctx.json(platService.getAllPlats());
    }

    // Récupère un plat par son id
    public static void getPlatById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        platService.getPlatById(id)
                .ifPresentOrElse(
                        plat -> ctx.json(plat), // Plat trouvé → retourne le JSON
                        () -> ctx.status(404).result("Plat non trouvé") // Sinon 404
                );
    }

    // Ajoute un nouveau plat
    public static void addPlat(Context ctx) {
        // Récupération des données du corps de la requête
        Plat newPlat = ctx.bodyAsClass(Plat.class);

        // Création du plat via le service
        Plat created = platService.addPlat(newPlat);

        // Retourne le plat créé avec le code 201
        ctx.status(201).json(created);
    }

    // Met à jour un plat existant
    public static void updatePlat(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Plat data = ctx.bodyAsClass(Plat.class);

        // Vérifie si le plat existe
        platService.getPlatById(id)
                .ifPresentOrElse(
                        plat -> {
                            // Mise à jour des champs du plat
                            plat.setNom(data.getNom());
                            plat.setDescription(data.getDescription());
                            plat.setPrix(data.getPrix());
                            plat.setPhotoUrl(data.getPhotoUrl());
                            plat.setQuantite(data.getQuantite());

                            // Appel du service pour appliquer la mise à jour
                            platService.updatePlat(id, plat);

                            // Retourne le plat mis à jour
                            ctx.json(plat);
                        },
                        () -> ctx.status(404).result("Plat non trouvé") // Sinon 404
                );
    }

    // Supprime un plat par son id
    public static void deletePlat(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));

        // Suppression via le service
        boolean suppr = platService.deletePlat(id);

        // Si suppression réussie → 204 No Content, sinon 404
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Plat non trouvé");
    }
}

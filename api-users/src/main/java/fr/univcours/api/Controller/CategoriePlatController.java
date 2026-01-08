package fr.univcours.api.Controller;

/**
 * Controller qui gère les routes liées aux catégories de plats.
 */
import fr.univcours.api.Models.Categorie_Plat;
import fr.univcours.api.Services.CategoriePlatService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CategoriePlatController {
    // Service utilisé pour gérer les opérations sur les catégories de plats
    private static final CategoriePlatService cpService = new CategoriePlatService();

    // Récupère la liste de tous les liens catégorie-plat
    public static void getAll(Context ctx) {
        ctx.json(cpService.getAllCategoriePlats());
    }

    // Récupère un lien catégorie-plat à partir de son id
    public static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Recherche du lien catégorie-plat correspondant à l’id
        cpService.getById(id)
                .ifPresentOrElse(
                        cp -> ctx.json(cp),
                        () -> ctx.status(404).result("Lien Categorie-Plat non trouvé")
                );
    }

    // Ajoute un nouveau lien catégorie-plat à la base de données
    public static void add(Context ctx) {
        Categorie_Plat newCp = ctx.bodyAsClass(Categorie_Plat.class);
        Categorie_Plat created = cpService.add(newCp);
        ctx.status(201).json(created);
    }

    // Met à jour un lien catégorie-plat existant
    public static void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Categorie_Plat data = ctx.bodyAsClass(Categorie_Plat.class);

        // Vérifie que le lien existe avant modification
        cpService.getById(id)
                .ifPresentOrElse(
                        cp -> {
                            // Mise à jour des champs du lien catégorie-plat
                            cp.setId_plat(data.getId_plat());
                            cp.setId_categorie(data.getId_categorie());
                            cpService.update(id, cp);
                            ctx.json(cp);
                        },
                        () -> ctx.status(404).result("Lien Categorie-Plat non trouvé")
                );
    }

    // Supprime un lien catégorie-plat à partir de son id
    public static void delete(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Tentative de suppression du lien catégorie-plat
        boolean suppr = cpService.delete(id);
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Lien Categorie-Plat non trouvé");
    }
}
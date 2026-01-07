package fr.univcours.api.Controller;

import fr.univcours.api.Models.Categorie;
import fr.univcours.api.Services.CategorieService;
import io.javalin.http.Context;
import java.sql.SQLException;

/**
 * Controller qui gère les routes liées aux catégories.
 */
public class CategorieController {
    // Service utilisé pour gérer les opérations sur les catégories
    private static final CategorieService categorieService = new CategorieService();

    // Récupère la liste de toutes les catégories
    public static void getAllCategories(Context ctx) {
        ctx.json(categorieService.getAllCategories());
    }

    // Récupère une catégorie à partir de son id
    public static void getCategorieById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Recherche de la catégorie correspondant à l’id
        categorieService.getCategorieById(id)
                .ifPresentOrElse(
                        cat -> ctx.json(cat),
                        () -> ctx.status(404).result("Catégorie non trouvée")
                );
    }

    // Ajoute une nouvelle catégorie à la base de données
    public static void addCategorie(Context ctx) {
        Categorie newCategorie = ctx.bodyAsClass(Categorie.class);
        Categorie created = categorieService.addCategorie(newCategorie);
        ctx.status(201).json(created);
    }

    // Met à jour les informations d’une catégorie existante
    public static void updateCategorie(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Categorie data = ctx.bodyAsClass(Categorie.class);

        // Vérifie que la catégorie existe avant modification
        categorieService.getCategorieById(id)
                .ifPresentOrElse(
                        cat -> {
                            // Mise à jour des champs de la catégorie
                            cat.setNom(data.getNom());
                            categorieService.updateCategorie(id, cat);
                            ctx.json(cat);
                        },
                        () -> ctx.status(404).result("Catégorie non trouvée")
                );
    }

    // Supprime une catégorie à partir de son id
    public static void deleteCategorie(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Tentative de suppression de la catégorie
        boolean suppr = categorieService.deleteCategorie(id);

        if (suppr) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Catégorie non trouvée");
        }
    }
}
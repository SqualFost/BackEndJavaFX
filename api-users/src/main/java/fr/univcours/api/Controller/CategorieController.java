package fr.univcours.api.Controller;

import fr.univcours.api.code_gener.Models.Categorie;
import fr.univcours.api.ServicesImpl.CategorieServiceImpl;
import io.javalin.http.Context;
import java.sql.SQLException;

// Controller pour gérer les endpoints liés aux catégories
public class CategorieController {

    // Service utilisé pour les opérations sur les catégories
    private static final CategorieServiceImpl categorieService = new CategorieServiceImpl();

    // Endpoint pour récupérer toutes les catégories
    public static void getAllCategories(Context ctx) {
        ctx.json(categorieService.getAllCategories());
    }

    // Endpoint pour récupérer une catégorie par son id
    public static void getCategorieById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        categorieService.getCategorieById(id)
                .ifPresentOrElse(
                        cat -> ctx.json(cat), // Catégorie trouvée → on retourne le JSON
                        () -> ctx.status(404).result("Catégorie non trouvée") // Sinon 404
                );
    }

    // Endpoint pour ajouter une nouvelle catégorie
    public static void addCategorie(Context ctx) {
        // Récupération des données du corps de la requête
        Categorie newCategorie = ctx.bodyAsClass(Categorie.class);

        // Création de la catégorie via le service
        Categorie created = categorieService.addCategorie(newCategorie);

        // Retourne la catégorie créée avec le code 201
        ctx.status(201).json(created);
    }

    // Endpoint pour mettre à jour une catégorie existante
    public static void updateCategorie(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Categorie data = ctx.bodyAsClass(Categorie.class);

        // Vérifie si la catégorie existe
        categorieService.getCategorieById(id)
                .ifPresentOrElse(
                        cat -> {
                            // Mise à jour des informations de la catégorie
                            cat.setNom(data.getNom());
                            categorieService.updateCategorie(id, cat);
                            ctx.json(cat); // Retour de la catégorie mise à jour
                        },
                        () -> ctx.status(404).result("Catégorie non trouvée") // Sinon 404
                );
    }

    // Endpoint pour supprimer une catégorie
    public static void deleteCategorie(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));

        // Suppression via le service
        boolean suppr = categorieService.deleteCategorie(id);

        // Si suppression réussie → 204 No Content, sinon 404
        if (suppr) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Catégorie non trouvée");
        }
    }
}

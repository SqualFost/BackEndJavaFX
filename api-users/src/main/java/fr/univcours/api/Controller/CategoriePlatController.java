package fr.univcours.api.Controller;

import fr.univcours.api.code_gener.Models.Categorie_Plat;
import fr.univcours.api.ServicesImpl.CategoriePlatServiceImpl;
import io.javalin.http.Context;
import java.sql.SQLException;

// Controller pour gérer les endpoints liés aux liens catégorie-plat
public class CategoriePlatController {

    // Service utilisé pour les opérations sur les liens catégorie-plat
    private static final CategoriePlatServiceImpl cpService = new CategoriePlatServiceImpl();

    // Endpoint pour récupérer tous les liens catégorie-plat
    public static void getAll(Context ctx) {
        ctx.json(cpService.getAllCategoriePlats());
    }

    // Endpoint pour récupérer un lien catégorie-plat par son id
    public static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        cpService.getById(id)
                .ifPresentOrElse(
                        cp -> ctx.json(cp), // Lien trouvé → retourne le JSON
                        () -> ctx.status(404).result("Lien Categorie-Plat non trouvé") // Sinon 404
                );
    }

    // Endpoint pour ajouter un nouveau lien catégorie-plat
    public static void add(Context ctx) {
        // Récupération des données du corps de la requête
        Categorie_Plat newCp = ctx.bodyAsClass(Categorie_Plat.class);

        // Création du lien via le service
        Categorie_Plat created = cpService.add(newCp);

        // Retourne le lien créé avec le code 201
        ctx.status(201).json(created);
    }

    // Endpoint pour mettre à jour un lien catégorie-plat existant
    public static void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Categorie_Plat data = ctx.bodyAsClass(Categorie_Plat.class);

        // Vérifie si le lien existe
        cpService.getById(id)
                .ifPresentOrElse(
                        cp -> {
                            // Mise à jour des informations du lien
                            cp.setId_plat(data.getId_plat());
                            cp.setId_categorie(data.getId_categorie());
                            cpService.update(id, cp);

                            // Retourne le lien mis à jour
                            ctx.json(cp);
                        },
                        () -> ctx.status(404).result("Lien Categorie-Plat non trouvé") // Sinon 404
                );
    }

    // Endpoint pour supprimer un lien catégorie-plat
    public static void delete(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));

        // Suppression via le service
        boolean suppr = cpService.delete(id);

        // Si suppression réussie → 204 No Content, sinon 404
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Lien Categorie-Plat non trouvé");
    }
}

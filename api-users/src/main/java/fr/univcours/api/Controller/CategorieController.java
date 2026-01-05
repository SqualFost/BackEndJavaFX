package fr.univcours.api.Controller;

import fr.univcours.api.Models.Categorie;
import fr.univcours.api.Services.CategorieService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CategorieController {
    private static final CategorieService categorieService = new CategorieService();

    public static void getAllCategories(Context ctx) {
        ctx.json(categorieService.getAllCategories());
    }

    public static void getCategorieById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        categorieService.getCategorieById(id)
                .ifPresentOrElse(
                        cat -> ctx.json(cat),
                        () -> ctx.status(404).result("Catégorie non trouvée")
                );
    }

    public static void addCategorie(Context ctx) {
        Categorie newCategorie = ctx.bodyAsClass(Categorie.class);
        Categorie created = categorieService.addCategorie(newCategorie);
        ctx.status(201).json(created);
    }

    public static void updateCategorie(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Categorie data = ctx.bodyAsClass(Categorie.class);

        categorieService.getCategorieById(id)
                .ifPresentOrElse(
                        cat -> {
                            cat.setNom(data.getNom());
                            categorieService.updateCategorie(id, cat);
                            ctx.json(cat);
                        },
                        () -> ctx.status(404).result("Catégorie non trouvée")
                );
    }

    public static void deleteCategorie(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean suppr = categorieService.deleteCategorie(id);

        if (suppr) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Catégorie non trouvée");
        }
    }
}
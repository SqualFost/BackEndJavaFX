package fr.univcours.api.Controller;

import fr.univcours.api.Models.Categorie_Plat;
import fr.univcours.api.Services.CategoriePlatService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CategoriePlatController {
    private static final CategoriePlatService cpService = new CategoriePlatService();

    public static void getAll(Context ctx) {
        ctx.json(cpService.getAllCategoriePlats());
    }

    public static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        cpService.getById(id)
                .ifPresentOrElse(
                        cp -> ctx.json(cp),
                        () -> ctx.status(404).result("Lien Categorie-Plat non trouvé")
                );
    }

    public static void add(Context ctx) {
        Categorie_Plat newCp = ctx.bodyAsClass(Categorie_Plat.class);
        Categorie_Plat created = cpService.add(newCp);
        ctx.status(201).json(created);
    }

    public static void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Categorie_Plat data = ctx.bodyAsClass(Categorie_Plat.class);

        cpService.getById(id)
                .ifPresentOrElse(
                        cp -> {
                            cp.setId_plat(data.getId_plat());
                            cp.setId_categorie(data.getId_categorie());
                            cpService.update(id, cp);
                            ctx.json(cp);
                        },
                        () -> ctx.status(404).result("Lien Categorie-Plat non trouvé")
                );
    }

    public static void delete(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean suppr = cpService.delete(id);
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Lien Categorie-Plat non trouvé");
    }
}
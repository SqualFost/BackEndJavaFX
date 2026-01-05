package fr.univcours.api.Controller;

import fr.univcours.api.Models.Plat;
import fr.univcours.api.Services.PlatService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class PlatController {
    private static final PlatService platService = new PlatService();

    public static void getAllPlats(Context ctx) {
        ctx.json(platService.getAllPlats());
    }

    public static void getPlatById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        platService.getPlatById(id)
                .ifPresentOrElse(
                        plat -> ctx.json(plat),
                        () -> ctx.status(404).result("Plat non trouvé")
                );
    }

    public static void addPlat(Context ctx) {
        Plat newPlat = ctx.bodyAsClass(Plat.class);
        Plat created = platService.addPlat(newPlat);
        ctx.status(201).json(created);
    }

    public static void updatePlat(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Plat data = ctx.bodyAsClass(Plat.class);

        platService.getPlatById(id)
                .ifPresentOrElse(
                        plat -> {
                            // Mise à jour des champs
                            plat.setName(data.getName());
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

    public static void deletePlat(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean suppr = platService.deletePlat(id);
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Plat non trouvé");
    }
}
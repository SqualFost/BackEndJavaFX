package fr.univcours.api.Controller;

import fr.univcours.api.Models.Commande_Plat;
import fr.univcours.api.Services.CommandePlatService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CommandePlatController {
    private static final CommandePlatService ligneService = new CommandePlatService();

    public static void getAllLignes(Context ctx) {
        ctx.json(ligneService.getAllLignesCommande());
    }

    public static void getLigneById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ligneService.getLigneById(id)
                .ifPresentOrElse(
                        ligne -> ctx.json(ligne),
                        () -> ctx.status(404).result("Ligne de commande non trouvée")
                );
    }

    public static void addLigne(Context ctx) {
        Commande_Plat newLigne = ctx.bodyAsClass(Commande_Plat.class);
        Commande_Plat created = ligneService.addLigne(newLigne);
        ctx.status(201).json(created);
    }

    public static void updateLigne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Commande_Plat data = ctx.bodyAsClass(Commande_Plat.class);

        ligneService.getLigneById(id)
                .ifPresentOrElse(
                        ligne -> {
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

    public static void deleteLigne(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean suppr = ligneService.deleteLigne(id);
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Ligne de commande non trouvée");
    }
}
package fr.univcours.api.Controller;

import fr.univcours.api.Models.Ligne_Commande;
import fr.univcours.api.Services.LigneCommandeService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class LigneCommandeController {
    private static final LigneCommandeService ligneService = new LigneCommandeService();

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
        Ligne_Commande newLigne = ctx.bodyAsClass(Ligne_Commande.class);
        Ligne_Commande created = ligneService.addLigne(newLigne);
        ctx.status(201).json(created);
    }

    public static void updateLigne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Ligne_Commande data = ctx.bodyAsClass(Ligne_Commande.class);

        ligneService.getLigneById(id)
                .ifPresentOrElse(
                        ligne -> {
                            ligne.setId_commande(data.getId_commande());
                            ligne.setId_plat(data.getId_plat());
                            ligne.setQualite(data.getQualite());
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
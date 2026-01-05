package fr.univcours.api.Controller;

import fr.univcours.api.Models.Commande;
import fr.univcours.api.Services.CommandeService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CommandeController {
    private static final CommandeService commandeService = new CommandeService();

    public static void getAllCommandes(Context ctx) {
        ctx.json(commandeService.getAllCommandes());
    }

    public static void getCommandeById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        commandeService.getCommandeById(id)
                .ifPresentOrElse(
                        cmd -> ctx.json(cmd),
                        () -> ctx.status(404).result("Commande non trouvée")
                );
    }

    public static void addCommande(Context ctx) {
        Commande newCmd = ctx.bodyAsClass(Commande.class);
        Commande created = commandeService.addCommande(newCmd);
        ctx.status(201).json(created);
    }

    public static void updateCommande(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Commande data = ctx.bodyAsClass(Commande.class);

        commandeService.getCommandeById(id)
                .ifPresentOrElse(
                        cmd -> {
                            cmd.setHeure_commande(data.getHeure_commande());
                            cmd.setPrix_total(data.getPrix_total());
                            cmd.setStatut(data.getStatut());
                            cmd.setId_utilisateur(data.getId_utilisateur());

                            commandeService.updateCommande(id, cmd);
                            ctx.json(cmd);
                        },
                        () -> ctx.status(404).result("Commande non trouvée")
                );
    }

    public static void deleteCommande(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean suppr = commandeService.deleteCommande(id);
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Commande non trouvée");
    }
}
package fr.univcours.api.Controller;

/**
 * Controller qui gère les routes liées aux commandes.
 */
import fr.univcours.api.Models.Commande;
import fr.univcours.api.Services.CommandeService;
import io.javalin.http.Context;
import java.sql.SQLException;

public class CommandeController {
    // Service utilisé pour gérer les opérations sur les commandes
    private static final CommandeService commandeService = new CommandeService();

    // Récupère la liste de toutes les commandes
    public static void getAllCommandes(Context ctx) {
        ctx.json(commandeService.getAllCommandes());
    }

    // Récupère une commande à partir de son id
    public static void getCommandeById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Recherche de la commande correspondant à l’id
        commandeService.getCommandeById(id)
                .ifPresentOrElse(
                        cmd -> ctx.json(cmd),
                        () -> ctx.status(404).result("Commande non trouvée")
                );
    }

    // Ajoute une nouvelle commande à la base de données
    public static void addCommande(Context ctx) {
        Commande newCmd = ctx.bodyAsClass(Commande.class);
        Commande created = commandeService.addCommande(newCmd);
        ctx.status(201).json(created);
    }

    // Met à jour les informations d’une commande existante
    public static void updateCommande(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Commande data = ctx.bodyAsClass(Commande.class);

        // Vérifie que la commande existe avant modification
        commandeService.getCommandeById(id)
                .ifPresentOrElse(
                        cmd -> {
                            // Mise à jour des champs de la commande
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

    // Supprime une commande à partir de son id
    public static void deleteCommande(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Tentative de suppression de la commande
        boolean suppr = commandeService.deleteCommande(id);
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Commande non trouvée");
    }
}
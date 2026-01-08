package fr.univcours.api.Controller;

import fr.univcours.api.code_gener.Models.Commande;
import fr.univcours.api.ServicesImpl.CommandeServiceImpl;
import io.javalin.http.Context;
import java.sql.SQLException;

// Controller pour gérer les endpoints liés aux commandes
public class CommandeController {

    // Service utilisé pour les opérations sur les commandes
    private static final CommandeServiceImpl commandeService = new CommandeServiceImpl();

    // Récupère toutes les commandes
    public static void getAllCommandes(Context ctx) {
        ctx.json(commandeService.getAllCommandes());
    }

    // Récupère une commande par son id
    public static void getCommandeById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        commandeService.getCommandeById(id)
                .ifPresentOrElse(
                        cmd -> ctx.json(cmd), // Commande trouvée → retourne le JSON
                        () -> ctx.status(404).result("Commande non trouvée") // Sinon 404
                );
    }

    // Ajoute une nouvelle commande
    public static void addCommande(Context ctx) {
        // Récupération des données du corps de la requête
        Commande newCmd = ctx.bodyAsClass(Commande.class);

        // Création de la commande via le service
        Commande created = commandeService.addCommande(newCmd);

        // Retourne la commande créée avec le code 201
        ctx.status(201).json(created);
    }

    // Met à jour une commande existante
    public static void updateCommande(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Commande data = ctx.bodyAsClass(Commande.class);

        // Vérifie si la commande existe
        commandeService.getCommandeById(id)
                .ifPresentOrElse(
                        cmd -> {
                            // Mise à jour des champs de la commande
                            cmd.setHeure_commande(data.getHeure_commande());
                            cmd.setPrix_total(data.getPrix_total());
                            cmd.setStatut(data.getStatut());
                            cmd.setId_utilisateur(data.getId_utilisateur());

                            // Appel du service pour appliquer la mise à jour
                            commandeService.updateCommande(id, cmd);

                            // Retourne la commande mise à jour
                            ctx.json(cmd);
                        },
                        () -> ctx.status(404).result("Commande non trouvée") // Sinon 404
                );
    }

    // Supprime une commande par son id
    public static void deleteCommande(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));

        // Suppression via le service
        boolean suppr = commandeService.deleteCommande(id);

        // Si suppression réussie → 204 No Content, sinon 404
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Commande non trouvée");
    }
}

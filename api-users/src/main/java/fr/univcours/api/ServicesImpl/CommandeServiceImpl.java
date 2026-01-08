package fr.univcours.api.ServicesImpl;

import fr.univcours.api.code_gener.Services.CommandeService;
import fr.univcours.api.Database.Database;
import fr.univcours.api.code_gener.Models.Commande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implémentation du service pour la gestion des commandes
public class CommandeServiceImpl extends CommandeService {

    // Récupère toutes les commandes depuis la base de données
    @Override
    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours des résultats et création des objets Commande
            while (rs.next()) {
                commandes.add(mapResultSetToCommande(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des commandes", e
            );
        }

        return commandes;
    }

    // Récupère une commande à partir de son id
    @Override
    public Optional<Commande> getCommandeById(int id) {
        String sql = "SELECT * FROM commande WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id dans la requête
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                // Si une commande est trouvée, on la retourne
                if (rs.next()) {
                    return Optional.of(mapResultSetToCommande(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération de la commande", e
            );
        }

        return Optional.empty();
    }

    // Ajoute une nouvelle commande dans la base de données
    @Override
    public Commande addCommande(Commande commande) {
        String sql =
                "INSERT INTO commande (heure_commande, prix_total, statut, id_utilisateur) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Affectation des valeurs de la commande
            stmt.setString(1, commande.getHeure_commande());
            stmt.setInt(2, commande.getPrix_total());
            stmt.setString(3, commande.getStatut());
            stmt.setInt(4, commande.getId_utilisateur());

            // Exécution de la requête d’insertion
            int rows = stmt.executeUpdate();

            // Récupération de l'id généré automatiquement
            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        commande.setId(keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de l'ajout de la commande", e
            );
        }

        return commande;
    }

    // Met à jour une commande existante
    @Override
    public Commande updateCommande(int id, Commande commandeData) {
        String sql =
                "UPDATE commande " +
                        "SET heure_commande = ?, prix_total = ?, statut = ?, id_utilisateur = ? " +
                        "WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Mise à jour des valeurs de la commande
            stmt.setString(1, commandeData.getHeure_commande());
            stmt.setInt(2, commandeData.getPrix_total());
            stmt.setString(3, commandeData.getStatut());
            stmt.setInt(4, commandeData.getId_utilisateur());
            stmt.setInt(5, id);

            // Exécution de la requête de mise à jour
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                commandeData.setId(id);
                return commandeData;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la modification de la commande", e
            );
        }
    }

    // Supprime une commande à partir de son id
    @Override
    public boolean deleteCommande(int id) {
        String sql = "DELETE FROM commande WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id à supprimer
            stmt.setInt(1, id);

            // Exécution de la requête de suppression
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la suppression de la commande", e
            );
        }
    }

    // Convertit une ligne du ResultSet en objet Commande
    private Commande mapResultSetToCommande(ResultSet rs) throws SQLException {
        Commande c = new Commande();
        c.setId(rs.getInt("id"));
        c.setHeure_commande(rs.getString("heure_commande"));
        c.setPrix_total(rs.getInt("prix_total"));
        c.setStatut(rs.getString("statut"));
        c.setId_utilisateur(rs.getInt("id_utilisateur"));
        return c;
    }
}

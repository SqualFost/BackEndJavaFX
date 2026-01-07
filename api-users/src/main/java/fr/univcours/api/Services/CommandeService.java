package fr.univcours.api.Services;

import fr.univcours.api.Database.Database;
import fr.univcours.api.Models.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service qui gère les opérations liées aux commandes.
 */
public class CommandeService {

    // Récupère toutes les commandes depuis la base de données
    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM commande";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Parcours des résultats et création des objets Commande
            while (rs.next()) {
                commandes.add(mapResultSetToCommande(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return commandes;
    }

    // Récupère une commande à partir de son id
    public Optional<Commande> getCommandeById(int id) {
        String query = "SELECT * FROM commande WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCommande(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    // Ajoute une nouvelle commande dans la base de données
    public Commande addCommande(Commande commande) {
        String query = "INSERT INTO commande (heure_commande, prix_total, statut, id_utilisateur) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, commande.getHeure_commande());
            pstmt.setInt(2, commande.getPrix_total());
            pstmt.setString(3, commande.getStatut());
            pstmt.setInt(4, commande.getId_utilisateur());

            // Exécution de la requête d’insertion
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        commande.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return commande;
    }

    // Met à jour une commande existante
    public Commande updateCommande(int id, Commande commandeData) {
        String sql = "UPDATE commande SET heure_commande = ?, prix_total = ?, statut = ?, id_utilisateur = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, commandeData.getHeure_commande());
            stmt.setInt(2, commandeData.getPrix_total());
            stmt.setString(3, commandeData.getStatut());
            stmt.setInt(4, commandeData.getId_utilisateur());
            stmt.setInt(5, id);

            // Exécution de la requête de mise à jour
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                commandeData.setId(id);
                return commandeData;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de la commande", e);
        }
    }

    // Supprime une commande à partir de son id
    public boolean deleteCommande(int id) throws SQLException {
        String sql = "DELETE FROM commande WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            // Exécution de la requête de suppression
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
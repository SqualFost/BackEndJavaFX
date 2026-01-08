package fr.univcours.api.Services;

import fr.univcours.api.Database.Database;
import fr.univcours.api.Models.Commande_Plat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service qui gère les opérations liées aux lignes de commande.
 */
public class CommandePlatService {

    // Récupère toutes les lignes de commande depuis la base de données
    public List<Commande_Plat> getAllLignesCommande() {
        List<Commande_Plat> lignes = new ArrayList<>();
        String query = "SELECT * FROM ligne_commande";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Parcours des résultats et création des objets Commande_Plat
            while (rs.next()) {
                lignes.add(mapResultSetToLigne(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lignes;
    }

    // Récupère une ligne de commande à partir de son id
    public Optional<Commande_Plat> getLigneById(int id) {
        String query = "SELECT * FROM ligne_commande WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLigne(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    // Ajoute une nouvelle ligne de commande dans la base de données
    public Commande_Plat addLigne(Commande_Plat ligne) {
        String query = "INSERT INTO ligne_commande (id_commande, id_plat, qualite, options_choisies) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, ligne.getId_commande());
            pstmt.setInt(2, ligne.getId_plat());
            pstmt.setInt(3, ligne.getQuantite());
            pstmt.setString(4, ligne.getOptions_choisies());

            // Exécution de la requête d’insertion
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ligne.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return ligne;
    }

    // Met à jour une ligne de commande existante
    public Commande_Plat updateLigne(int id, Commande_Plat ligneData) {
        String sql = "UPDATE ligne_commande SET id_commande = ?, id_plat = ?, qualite = ?, options_choisies = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ligneData.getId_commande());
            stmt.setInt(2, ligneData.getId_plat());
            stmt.setInt(3, ligneData.getQuantite());
            stmt.setString(4, ligneData.getOptions_choisies());
            stmt.setInt(5, id);

            // Exécution de la requête de mise à jour
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ligneData.setId(id);
                return ligneData;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de la ligne de commande", e);
        }
    }

    // Supprime une ligne de commande à partir de son id
    public boolean deleteLigne(int id) throws SQLException {
        String sql = "DELETE FROM ligne_commande WHERE id = ?";

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

    // Convertit une ligne du ResultSet en objet Commande_Plat
    private Commande_Plat mapResultSetToLigne(ResultSet rs) throws SQLException {
        Commande_Plat l = new Commande_Plat();

        l.setId(rs.getInt("id"));
        l.setId_commande(rs.getInt("id_commande"));
        l.setId_plat(rs.getInt("id_plat"));
        l.setQuantite(rs.getInt("qualite"));
        l.setOptions_choisies(rs.getString("options_choisies"));

        return l;
    }
}
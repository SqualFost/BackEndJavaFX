package fr.univcours.api.Services;

import fr.univcours.api.Database.Database;
import fr.univcours.api.Models.Plat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service qui gère les opérations liées aux plats.
 */
public class PlatService {

    // Récupère tous les plats depuis la base de données
    public List<Plat> getAllPlats() {
        List<Plat> plats = new ArrayList<>();
        String query = "SELECT * FROM plat";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Parcours des résultats et création des objets Plat
            while (rs.next()) {
                plats.add(mapResultSetToPlat(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return plats;
    }

    // Récupère un plat à partir de son id
    public Optional<Plat> getPlatById(int id) {
        String query = "SELECT * FROM plat WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToPlat(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    // Ajoute un nouveau plat dans la base de données
    public Plat addPlat(Plat plat) {
        // Attention: 'name' dans le modèle Plat, pas 'nom'
        String query = "INSERT INTO plat (name, description, prix, photoUrl, quantite) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, plat.getNom());
            pstmt.setString(2, plat.getDescription());
            pstmt.setFloat(3, plat.getPrix());
            pstmt.setString(4, plat.getPhotoUrl());
            pstmt.setInt(5, plat.getQuantite());

            // Exécution de la requête d’insertion
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        plat.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return plat;
    }

    // Met à jour un plat existant
    public Plat updatePlat(int id, Plat platData) {
        String sql = "UPDATE plat SET name = ?, description = ?, prix = ?, photoUrl = ?, quantite = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, platData.getNom());
            stmt.setString(2, platData.getDescription());
            stmt.setFloat(3, platData.getPrix());
            stmt.setString(4, platData.getPhotoUrl());
            stmt.setInt(5, platData.getQuantite());
            stmt.setInt(6, id);

            // Exécution de la requête de mise à jour
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                platData.setId(id);
                return platData;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du plat", e);
        }
    }

    // Supprime un plat à partir de son id
    public boolean deletePlat(int id) throws SQLException {
        String sql = "DELETE FROM plat WHERE id = ?";

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

    // Convertit une ligne du ResultSet en objet Plat
    private Plat mapResultSetToPlat(ResultSet rs) throws SQLException {
        Plat p =  new Plat();

        p.setId(rs.getInt("id"));
        p.setNom(rs.getString("nom"));
        p.setDescription(rs.getString("description"));
        p.setPrix(rs.getFloat("prix"));
        p.setPhotoUrl(rs.getString("photoUrl"));
        p.setQuantite(rs.getInt("quantite"));

        return p;
    }
}
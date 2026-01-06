package fr.univcours.api.Services;

import fr.univcours.api.Database.Database;
import fr.univcours.api.Models.Plat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlatService {

    public List<Plat> getAllPlats() {
        List<Plat> plats = new ArrayList<>();
        String query = "SELECT * FROM plat";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                plats.add(mapResultSetToPlat(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return plats;
    }

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

    public boolean deletePlat(int id) throws SQLException {
        String sql = "DELETE FROM plat WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Plat mapResultSetToPlat(ResultSet rs) throws SQLException {
        return new Plat(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("description"),
                rs.getFloat("prix"),
                rs.getString("photoUrl"),
                rs.getInt("quantite")
        );
    }
}
package fr.univcours.api.Services;

import fr.univcours.api.Database.Database;
import fr.univcours.api.Models.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategorieService {

    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                categories.add(mapResultSetToCategorie(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return categories;
    }

    public Optional<Categorie> getCategorieById(int id) {
        String query = "SELECT * FROM categorie WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCategorie(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    public Categorie addCategorie(Categorie categorie) {
        String query = "INSERT INTO categorie (nom) VALUES (?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, categorie.getNom());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        categorie.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return categorie;
    }

    public Categorie updateCategorie(int id, Categorie categorieData) {
        String sql = "UPDATE categorie SET nom = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categorieData.getNom());
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                categorieData.setId(id);
                return categorieData;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de la catégorie", e);
        }
    }

    public boolean deleteCategorie(int id) throws SQLException {
        String sql = "DELETE FROM categorie WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Categorie mapResultSetToCategorie(ResultSet rs) throws SQLException {
        Categorie categorie = new Categorie();

        categorie.setId(rs.getInt("id"));
        categorie.setNom(rs.getString("nom"));

        return categorie;
    }
}
package fr.univcours.api.Services;

import fr.univcours.api.Database.Database;
import fr.univcours.api.Models.Categorie_Plat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriePlatService {

    public List<Categorie_Plat> getAllCategoriePlats() {
        List<Categorie_Plat> list = new ArrayList<>();
        String query = "SELECT * FROM categorie_plat";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(mapResultSetToCategoriePlat(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public Optional<Categorie_Plat> getById(int id) {
        String query = "SELECT * FROM categorie_plat WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCategoriePlat(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    public Categorie_Plat add(Categorie_Plat cp) {
        String query = "INSERT INTO categorie_plat (id_plat, id_categorie) VALUES (?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, cp.getId_plat());
            pstmt.setInt(2, cp.getId_categorie());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cp.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return cp;
    }

    public Categorie_Plat update(int id, Categorie_Plat cpData) {
        String sql = "UPDATE categorie_plat SET id_plat = ?, id_categorie = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cpData.getId_plat());
            stmt.setInt(2, cpData.getId_categorie());
            stmt.setInt(3, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                cpData.setId(id);
                return cpData;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de categorie_plat", e);
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM categorie_plat WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Categorie_Plat mapResultSetToCategoriePlat(ResultSet rs) throws SQLException {
        Categorie_Plat cp = new Categorie_Plat();

        cp.setId(rs.getInt("id"));
        cp.setId_plat(rs.getInt("id_plat"));
        cp.setId_categorie(rs.getInt("id_categorie"));

        return cp;
    }
}
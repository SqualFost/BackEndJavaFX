package fr.univcours.api.Services;

import fr.univcours.api.Database.Database;
import fr.univcours.api.Models.Ligne_Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LigneCommandeService {

    public List<Ligne_Commande> getAllLignesCommande() {
        List<Ligne_Commande> lignes = new ArrayList<>();
        String query = "SELECT * FROM ligne_commande";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                lignes.add(mapResultSetToLigne(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lignes;
    }

    public Optional<Ligne_Commande> getLigneById(int id) {
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

    public Ligne_Commande addLigne(Ligne_Commande ligne) {
        String query = "INSERT INTO ligne_commande (id_commande, id_plat, qualite, options_choisies) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, ligne.getId_commande());
            pstmt.setInt(2, ligne.getId_plat());
            pstmt.setInt(3, ligne.getQualite());
            pstmt.setString(4, ligne.getOptions_choisies());

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

    public Ligne_Commande updateLigne(int id, Ligne_Commande ligneData) {
        String sql = "UPDATE ligne_commande SET id_commande = ?, id_plat = ?, qualite = ?, options_choisies = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ligneData.getId_commande());
            stmt.setInt(2, ligneData.getId_plat());
            stmt.setInt(3, ligneData.getQualite());
            stmt.setString(4, ligneData.getOptions_choisies());
            stmt.setInt(5, id);

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

    public boolean deleteLigne(int id) throws SQLException {
        String sql = "DELETE FROM ligne_commande WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ligne_Commande mapResultSetToLigne(ResultSet rs) throws SQLException {
        Ligne_Commande l = new Ligne_Commande();
        l.setId(rs.getInt("id"));
        l.setId_commande(rs.getInt("id_commande"));
        l.setId_plat(rs.getInt("id_plat"));
        l.setQualite(rs.getInt("qualite"));
        l.setOptions_choisies(rs.getString("options_choisies"));
        return l;
    }
}
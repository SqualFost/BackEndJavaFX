package fr.univcours.api.ServicesImpl;

import fr.univcours.api.code_gener.Services.CategoriePlatService;
import fr.univcours.api.code_gener.Models.Categorie_Plat;
import fr.univcours.api.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implémentation du service pour la gestion des catégories de plats
public class CategoriePlatServiceImpl extends CategoriePlatService {

    // Récupère toutes les associations categorie / plat depuis la base de données
    @Override
    public List<Categorie_Plat> getAllCategoriePlats() {
        List<Categorie_Plat> list = new ArrayList<>();
        String sql = "SELECT * FROM categorie_plat";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours des résultats et création des objets Categorie_Plat
            while (rs.next()) {
                list.add(mapResultSetToCategoriePlat(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des catégories de plats", e);
        }

        return list;
    }

    // Récupère une association categorie / plat à partir de son id
    @Override
    public Optional<Categorie_Plat> getById(int id) {
        String sql = "SELECT * FROM categorie_plat WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id dans la requête
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                // Si un résultat est trouvé, on le convertit en objet Categorie_Plat
                if (rs.next()) {
                    return Optional.of(mapResultSetToCategoriePlat(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de categorie_plat par id", e);
        }

        return Optional.empty();
    }

    // Ajoute une nouvelle association categorie / plat dans la base de données
    @Override
    public Categorie_Plat add(Categorie_Plat cp) {
        String sql = "INSERT INTO categorie_plat (id_plat, id_categorie) VALUES (?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Affectation des valeurs à insérer
            stmt.setInt(1, cp.getId_plat());
            stmt.setInt(2, cp.getId_categorie());

            // Exécution de la requête d’insertion
            int rows = stmt.executeUpdate();

            // Récupération de l'id généré automatiquement
            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        cp.setId(keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de categorie_plat", e);
        }

        return cp;
    }

    // Met à jour une association categorie / plat existante
    @Override
    public Categorie_Plat update(int id, Categorie_Plat cpData) {
        String sql = "UPDATE categorie_plat SET id_plat = ?, id_categorie = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Mise à jour des valeurs
            stmt.setInt(1, cpData.getId_plat());
            stmt.setInt(2, cpData.getId_categorie());
            stmt.setInt(3, id);

            // Exécution de la requête de mise à jour
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                cpData.setId(id);
                return cpData;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de categorie_plat", e);
        }
    }

    // Supprime une association categorie / plat à partir de son id
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM categorie_plat WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id à supprimer
            stmt.setInt(1, id);

            // Exécution de la requête de suppression
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de categorie_plat", e);
        }
    }

    // Convertit une ligne du ResultSet en objet Categorie_Plat
    private Categorie_Plat mapResultSetToCategoriePlat(ResultSet rs) throws SQLException {
        Categorie_Plat cp = new Categorie_Plat();
        cp.setId(rs.getInt("id"));
        cp.setId_plat(rs.getInt("id_plat"));
        cp.setId_categorie(rs.getInt("id_categorie"));
        return cp;
    }
}
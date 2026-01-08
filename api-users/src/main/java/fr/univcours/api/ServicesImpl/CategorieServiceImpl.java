package fr.univcours.api.ServicesImpl;

import fr.univcours.api.code_gener.Services.CategorieService;
import fr.univcours.api.Database.Database;
import fr.univcours.api.code_gener.Models.Categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implémentation du service pour la gestion des catégories
public class CategorieServiceImpl extends CategorieService {

    // Récupère toutes les catégories depuis la base de données
    @Override
    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours des résultats et création des objets Categorie
            while (rs.next()) {
                categories.add(mapResultSetToCategorie(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des catégories", e);
        }

        return categories;
    }

    // Récupère une catégorie à partir de son id
    @Override
    public Optional<Categorie> getCategorieById(int id) {
        String sql = "SELECT * FROM categorie WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id dans la requête
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                // Si une catégorie est trouvée, on la retourne
                if (rs.next()) {
                    return Optional.of(mapResultSetToCategorie(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la catégorie", e);
        }

        return Optional.empty();
    }

    // Ajoute une nouvelle catégorie dans la base de données
    @Override
    public Categorie addCategorie(Categorie categorie) {
        String sql = "INSERT INTO categorie (nom) VALUES (?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Affectation du nom de la catégorie
            stmt.setString(1, categorie.getNom());

            // Exécution de la requête d’insertion
            int rows = stmt.executeUpdate();

            // Récupération de l'id généré automatiquement
            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        categorie.setId(keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la catégorie", e);
        }

        return categorie;
    }

    // Met à jour une catégorie existante
    @Override
    public Categorie updateCategorie(int id, Categorie categorieData) {
        String sql = "UPDATE categorie SET nom = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Mise à jour des valeurs de la catégorie
            stmt.setString(1, categorieData.getNom());
            stmt.setInt(2, id);

            // Exécution de la requête de mise à jour
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                categorieData.setId(id);
                return categorieData;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de la catégorie", e);
        }
    }

    // Supprime une catégorie à partir de son id
    @Override
    public boolean deleteCategorie(int id) {
        String sql = "DELETE FROM categorie WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id à supprimer
            stmt.setInt(1, id);

            // Exécution de la requête de suppression
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la catégorie", e);
        }
    }

    // Convertit une ligne du ResultSet en objet Categorie
    private Categorie mapResultSetToCategorie(ResultSet rs) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setId(rs.getInt("id"));
        categorie.setNom(rs.getString("nom"));
        return categorie;
    }
}

package fr.univcours.api.ServicesImpl;

import fr.univcours.api.code_gener.Services.UserService;
import fr.univcours.api.Database.Database;
import fr.univcours.api.code_gener.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implémentation du service pour la gestion des utilisateurs
public class UserServiceImpl extends UserService {

    // Récupère tous les utilisateurs depuis la base de données
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours des résultats et création des objets User
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur récupération utilisateurs", e);
        }

        return users;
    }

    // Récupère un utilisateur à partir de son id
    @Override
    public Optional<User> getUserById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id dans la requête
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                // Si un utilisateur est trouvé, on le retourne
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur récupération utilisateur", e);
        }

        return Optional.empty();
    }

    // Ajoute un nouvel utilisateur dans la base de données
    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO user (nom, nbPoints) VALUES (?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Affectation des valeurs de l'utilisateur
            stmt.setString(1, user.getNom());
            stmt.setInt(2, user.getNbPoints());

            // Exécution de la requête d’insertion
            int affectedRows = stmt.executeUpdate();

            // Récupération de l'id généré automatiquement
            if (affectedRows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        user.setId(keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur ajout utilisateur", e);
        }

        return user;
    }

    // Recherche des utilisateurs à partir de leur nom
    @Override
    public List<User> searchByName(String nom) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE nom LIKE ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Ajout du filtre de recherche
            stmt.setString(1, "%" + nom + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                // Parcours des résultats et création des objets User
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche utilisateur par nom", e);
        }

        return users;
    }

    // Met à jour un utilisateur existant à partir de son id
    @Override
    public User updateById(int id, User userData) {
        String sql = "UPDATE user SET nom = ?, nbPoints = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Mise à jour des informations de l'utilisateur
            stmt.setString(1, userData.getNom());
            stmt.setInt(2, userData.getNbPoints());
            stmt.setInt(3, id);

            // Exécution de la requête de mise à jour
            if (stmt.executeUpdate() > 0) {
                userData.setId(id);
                return userData;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur modification utilisateur", e);
        }
    }

    // Supprime un utilisateur à partir de son id
    @Override
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id à supprimer
            stmt.setInt(1, id);

            // Exécution de la requête de suppression
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur suppression utilisateur", e);
        }
    }

    // Convertit une ligne du ResultSet en objet User
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setNom(rs.getString("nom"));
        user.setNbPoints(rs.getInt("nbPoints"));

        return user;
    }
}

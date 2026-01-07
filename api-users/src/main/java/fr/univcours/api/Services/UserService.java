package fr.univcours.api.Services;

import fr.univcours.api.Database.Database;
import fr.univcours.api.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Interface qui définit le CRUD pour la gestion des utilisateurs
 */

/**
 * Service qui gère les opérations liées aux utilisateurs.
 */
public class UserService {
    // Récupère tous les utilisateurs depuis la base de données
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Parcours des résultats et création des objets User
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return users;
    }

    // Récupère un utilisateur à partir de son id
    public Optional<User> getUserById(int id) {
        String query = "SELECT * FROM user WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    // Ajoute un nouvel utilisateur dans la base de données
    public User addUser(User user) {
        String query = "INSERT INTO User (nom, nbPoints) VALUES (?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getNom());
            pstmt.setInt(2, user.getNbPoints());

            // Exécution de la requête d’insertion
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return user;
    }

    // Recherche des utilisateurs par nom
    public List<User> searchByName(String name) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user WHERE name LIKE ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                // Parcours des résultats et création des objets User
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return users;
    }

    // Met à jour un utilisateur existant
    public User updateById(int id, User userData) {
        String sql = "UPDATE User SET nom = ? , nbPoints = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userData.getNom());
            stmt.setInt(2, userData.getNbPoints());
            stmt.setInt(3, id);

            // Exécution de la requête de mise à jour
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                userData.setId(id);
                return userData;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de l'utilisateur", e);
        }
    }

    // Supprime un utilisateur à partir de son id
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            // Exécution de la requête de suppression
            int rowsAffected = stmt.executeUpdate();
            // Si rowsAffected > 0, c'est qu'on a bien supprimé quelqu'un
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

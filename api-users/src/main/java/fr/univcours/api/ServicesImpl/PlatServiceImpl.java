package fr.univcours.api.ServicesImpl;

import fr.univcours.api.code_gener.Services.PlatService;
import fr.univcours.api.Database.Database;
import fr.univcours.api.code_gener.Models.Plat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implémentation du service pour la gestion des plats
public class PlatServiceImpl extends PlatService {

    // Récupère tous les plats depuis la base de données
    @Override
    public List<Plat> getAllPlats() {
        List<Plat> plats = new ArrayList<>();
        String sql = "SELECT * FROM plat";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours des résultats et création des objets Plat
            while (rs.next()) {
                plats.add(mapResultSetToPlat(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur récupération plats", e);
        }

        return plats;
    }

    // Récupère un plat à partir de son id
    @Override
    public Optional<Plat> getPlatById(int id) {
        String sql = "SELECT * FROM plat WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id dans la requête
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                // Si un plat est trouvé, on le retourne
                if (rs.next()) {
                    return Optional.of(mapResultSetToPlat(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur récupération plat", e);
        }

        return Optional.empty();
    }

    // Ajoute un nouveau plat dans la base de données
    @Override
    public Plat addPlat(Plat plat) {
        String sql = "INSERT INTO plat (name, description, prix, photoUrl, quantite) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Affectation des valeurs du plat
            stmt.setString(1, plat.getNom());
            stmt.setString(2, plat.getDescription());
            stmt.setFloat(3, plat.getPrix());
            stmt.setString(4, plat.getPhotoUrl());
            stmt.setInt(5, plat.getQuantite());

            // Exécution de la requête d’insertion
            int affectedRows = stmt.executeUpdate();

            // Récupération de l'id généré automatiquement
            if (affectedRows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        plat.setId(keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur ajout plat", e);
        }

        return plat;
    }

    // Met à jour un plat existant
    @Override
    public Plat updatePlat(int id, Plat platData) {
        String sql = "UPDATE plat SET name = ?, description = ?, prix = ?, photoUrl = ?, quantite = ? WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Mise à jour des valeurs du plat
            stmt.setString(1, platData.getNom());
            stmt.setString(2, platData.getDescription());
            stmt.setFloat(3, platData.getPrix());
            stmt.setString(4, platData.getPhotoUrl());
            stmt.setInt(5, platData.getQuantite());
            stmt.setInt(6, id);

            // Exécution de la requête de mise à jour
            if (stmt.executeUpdate() > 0) {
                platData.setId(id);
                return platData;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur modification plat", e);
        }
    }

    // Supprime un plat à partir de son id
    @Override
    public boolean deletePlat(int id) {
        String sql = "DELETE FROM plat WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id à supprimer
            stmt.setInt(1, id);

            // Exécution de la requête de suppression
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur suppression plat", e);
        }
    }

    // Convertit une ligne du ResultSet en objet Plat
    private Plat mapResultSetToPlat(ResultSet rs) throws SQLException {
        Plat p = new Plat();

        p.setId(rs.getInt("id"));
        p.setNom(rs.getString("nom")); // Champ SQL name ↔ attribut Java nom
        p.setDescription(rs.getString("description"));
        p.setPrix(rs.getFloat("prix"));
        p.setPhotoUrl(rs.getString("photoUrl"));
        p.setQuantite(rs.getInt("quantite"));

        return p;
    }
}

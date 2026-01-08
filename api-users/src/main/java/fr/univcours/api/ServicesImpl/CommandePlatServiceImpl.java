package fr.univcours.api.ServicesImpl;

import fr.univcours.api.code_gener.Services.CommandePlatService;
import fr.univcours.api.Database.Database;
import fr.univcours.api.code_gener.Models.Commande_Plat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implémentation du service pour la gestion des lignes de commande
public class CommandePlatServiceImpl extends CommandePlatService {

    // Récupère toutes les lignes de commande depuis la base de données
    @Override
    public List<Commande_Plat> getAllLignesCommande() {
        List<Commande_Plat> lignes = new ArrayList<>();
        String sql = "SELECT * FROM ligne_commande";

        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcours des résultats et création des objets Commande_Plat
            while (rs.next()) {
                lignes.add(mapResultSetToLigne(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des lignes de commande", e
            );
        }

        return lignes;
    }

    // Récupère une ligne de commande à partir de son id
    @Override
    public Optional<Commande_Plat> getLigneById(int id) {
        String sql = "SELECT * FROM ligne_commande WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id dans la requête
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                // Si une ligne est trouvée, on la retourne
                if (rs.next()) {
                    return Optional.of(mapResultSetToLigne(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération de la ligne de commande", e
            );
        }

        return Optional.empty();
    }

    // Ajoute une nouvelle ligne de commande dans la base de données
    @Override
    public Commande_Plat addLigne(Commande_Plat ligne) {
        String sql =
                "INSERT INTO ligne_commande (id_commande, id_plat, qualite, options_choisies) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Affectation des valeurs de la ligne de commande
            stmt.setInt(1, ligne.getId_commande());
            stmt.setInt(2, ligne.getId_plat());
            stmt.setInt(3, ligne.getQuantite());
            stmt.setString(4, ligne.getOptions_choisies());

            // Exécution de la requête d’insertion
            int rows = stmt.executeUpdate();

            // Récupération de l'id généré automatiquement
            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        ligne.setId(keys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de l'ajout de la ligne de commande", e
            );
        }

        return ligne;
    }

    // Met à jour une ligne de commande existante
    @Override
    public Commande_Plat updateLigne(int id, Commande_Plat ligneData) {
        String sql =
                "UPDATE ligne_commande " +
                        "SET id_commande = ?, id_plat = ?, qualite = ?, options_choisies = ? " +
                        "WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Mise à jour des valeurs de la ligne de commande
            stmt.setInt(1, ligneData.getId_commande());
            stmt.setInt(2, ligneData.getId_plat());
            stmt.setInt(3, ligneData.getQuantite());
            stmt.setString(4, ligneData.getOptions_choisies());
            stmt.setInt(5, id);

            // Exécution de la requête de mise à jour
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ligneData.setId(id);
                return ligneData;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la modification de la ligne de commande", e
            );
        }
    }

    // Supprime une ligne de commande à partir de son id
    @Override
    public boolean deleteLigne(int id) {
        String sql = "DELETE FROM ligne_commande WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Injection de l'id à supprimer
            stmt.setInt(1, id);

            // Exécution de la requête de suppression
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la suppression de la ligne de commande", e
            );
        }
    }

    // Convertit une ligne du ResultSet en objet Commande_Plat
    private Commande_Plat mapResultSetToLigne(ResultSet rs) throws SQLException {
        Commande_Plat l = new Commande_Plat();
        l.setId(rs.getInt("id"));
        l.setId_commande(rs.getInt("id_commande"));
        l.setId_plat(rs.getInt("id_plat"));
        l.setQuantite(rs.getInt("qualite"));
        l.setOptions_choisies(rs.getString("options_choisies"));
        return l;
    }
}

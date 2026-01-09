package fr.univcours.api.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // --- 1. LE SINGLETON ---
    private static Database instance;

    private Database() {
        // Constructeur privé
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // --- 2. LA CONNEXION (Compatible Docker & Local) ---
    public Connection getConnection() {
        String dbHost = System.getenv("DB_HOST");
        String dbPort = System.getenv("DB_PORT");

        if (dbHost == null) dbHost = "localhost";
        if (dbPort == null) dbPort = "3306"; // Ou 3307 si modifié

        String dbName = "restaurant_db";
        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ Erreur sur l'URL : " + url);
            e.printStackTrace();
            throw new RuntimeException("Impossible de se connecter à la base de données.");
        }
    }

    // --- 3. LA MÉTHODE MANQUANTE (checkConnection) ---
    public void checkConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Connexion à la base de données réussie !");
            }
        } catch (SQLException e) {
            System.err.println("❌ Échec de la connexion BDD.");
            e.printStackTrace();
            // On arrête tout si la BDD n'est pas là au démarrage
            throw new RuntimeException("Arrêt du serveur : Pas de BDD.");
        }
    }
}
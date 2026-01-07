package fr.univcours.api.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connexion à la base de données MySQL existante.
 */
public class Database {

    // URL de connexion à la base de données MySQL
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/clicknwok";

    // Identifiants de connexion à la BDD
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    // Instance unique de la classe (Singleton)
    private static Database instance;

    // Constructeur privé pour éviter plusieurs instances
    private Database() {
        try {
            // Chargement du driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trouvé !");
        }
    }

    // Méthode qui permet de récupérer l’instance unique de Database
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Fonction qui permet de récupérer une connexion à la base de données
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    /**
     * Fonction qui permet de vérifier la connexion à la BDD.
     * Si la connexion échoue, une erreur est levée et le programme s’arrête.
     * Sinon, on affiche un message de succès dans la console.
     */
    public void checkConnection() {
        try (Connection conn = getConnection()) {
            if (conn == null) {
                throw new SQLException("La connexion est nulle");
            }
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Impossible de se connecter à la base de données.");
            System.err.println("Détails : " + e.getMessage());
            throw new RuntimeException("Arrêt du script, pas de connexion à la BDD", e);
        }
    }
}
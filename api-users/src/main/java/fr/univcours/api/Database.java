package fr.univcours.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/isen";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    private static Database instance;

    // Constructeur privé pour empêcher d'instancier la classe ailleurs
    private Database() {
        try {
            // Optionnel : permet de vérifier que le driver est bien chargé
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trouvé !");
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

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
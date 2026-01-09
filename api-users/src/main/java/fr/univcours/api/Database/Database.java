package fr.univcours.api.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // --- 1. LE SINGLETON (Pour que PlatServiceImpl soit content) ---
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
        if (dbPort == null) dbPort = "3306"; // Vérifie si tu utilises 3306 ou 3307

        // Mets ici le NOUVEAU nom de ta base
        String dbName = "clicknwok";
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

    // --- 3. LE CHECK (Pour que Main.java soit content) ---
    // --- 3. LE CHECK INTELLIGENT (Avec Retry) ---
    public void checkConnection() {
        System.out.println("🔄 Démarrage du serveur backend...");

        int maxRetries = 15; // On essaie 15 fois
        int waitTime = 3000; // 3 secondes d'attente entre chaque essai

        for (int i = 0; i < maxRetries; i++) {
            try (Connection conn = getConnection()) {
                if (conn != null) {
                    System.out.println("✅ VICTOIRE : Connexion établie avec la BDD 'clicknwok' !");
                    return; // C'est gagné, on sort de la méthode et le serveur démarre
                }
            } catch (Exception e) {
                System.out.println("⏳ Tentative " + (i + 1) + "/" + maxRetries + " : La BDD n'est pas encore prête (Connexion refusée)... on patiente 3s.");
                try {
                    Thread.sleep(waitTime); // Pause de 3 secondes
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Si on arrive ici, c'est que ça a échoué 15 fois (45 secondes)
        System.err.println("❌ ÉCHEC TOTAL : La BDD ne répond toujours pas.");
        throw new RuntimeException("Arrêt du serveur : Impossible de joindre la BDD.");
    }
}
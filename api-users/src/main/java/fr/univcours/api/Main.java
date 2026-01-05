package fr.univcours.api;

import fr.univcours.api.Controller.UserController;
import fr.univcours.api.Database.Database;

import io.javalin.Javalin;
import io.javalin.plugin.bundled.CorsPluginConfig;

/**
 * Classe principale qui démarre le serveur API
 */
public class Main {
    public static void main(String[] args) {
        // Créer et configurer l'application Javalin
        Database.getInstance().checkConnection();
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(CorsPluginConfig::anyHost);
            });
        }).start(7001);

        // Message de démarrage
        System.out.println("🚀 Serveur démarré sur http://localhost:7001");
        System.out.println("📋 Essayez : http://localhost:7001/users");

        // Route GET /users - Récupère tous les utilisateurs
        app.get("/users", UserController::getAllUser);
        
        // Route GET /users/search?name="" - Rechercher un utilisateur par son prénom
        app.get("/users/search", UserController::searchByName);

        // Route GET /users/:id - Récupère un utilisateur par ID
        app.get("/users/{id}", UserController::getUserById);
        
        // Route POST /users - Ajoute un utilisateur
        app.post("/users", UserController::addUser);

        // Route POST /users/:id - Modifie un utilisateur
        app.put("/users/{id}", UserController::updateById);

        
        // Route DELETE /users/:id - Supprime un utilisateur
        app.delete("/users/{id}", UserController::deleteUser);

        // Route GET / - Page d'accueil
        app.get("/", UserController::getHome);
    }
}

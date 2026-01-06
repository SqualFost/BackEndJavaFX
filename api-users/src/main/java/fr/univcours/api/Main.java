package fr.univcours.api;

import fr.univcours.api.Controller.*;
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

        // --- Routes PAGE D'ACCUEIL ---
        app.get("/", UserController::getHome);

        // --- Routes UTILISATEURS (User) ---
        app.get("/users", UserController::getAllUser);
        app.get("/users/search", UserController::searchByName);
        app.get("/users/{id}", UserController::getUserById);
        app.post("/users", UserController::addUser);
        app.put("/users/{id}", UserController::updateById);
        app.delete("/users/{id}", UserController::deleteUser);

        // --- Routes CATEGORIES ---
        app.get("/categories", CategorieController::getAllCategories);
        app.get("/categories/{id}", CategorieController::getCategorieById);
        app.post("/categories", CategorieController::addCategorie);
        app.put("/categories/{id}", CategorieController::updateCategorie);
        app.delete("/categories/{id}", CategorieController::deleteCategorie);

        // --- Routes PLATS ---
        app.get("/plats", PlatController::getAllPlats);
        app.get("/plats/{id}", PlatController::getPlatById);
        app.post("/plats", PlatController::addPlat);
        app.put("/plats/{id}", PlatController::updatePlat);
        app.delete("/plats/{id}", PlatController::deletePlat);

        // --- Routes COMMANDES ---
        app.get("/commandes", CommandeController::getAllCommandes);
        app.get("/commandes/{id}", CommandeController::getCommandeById);
        app.post("/commandes", CommandeController::addCommande);
        app.put("/commandes/{id}", CommandeController::updateCommande);
        app.delete("/commandes/{id}", CommandeController::deleteCommande);

        // --- Routes LIGNES DE COMMANDE ---
        app.get("/lignes-commande", CommandePlatController::getAllLignes);
        app.get("/lignes-commande/{id}", CommandePlatController::getLigneById);
        app.post("/lignes-commande", CommandePlatController::addLigne);
        app.put("/lignes-commande/{id}", CommandePlatController::updateLigne);
        app.delete("/lignes-commande/{id}", CommandePlatController::deleteLigne);

        // --- Routes ASSOCIATIONS CATEGORIE-PLAT ---
        app.get("/categorie-plats", CategoriePlatController::getAll);
        app.get("/categorie-plats/{id}", CategoriePlatController::getById);
        app.post("/categorie-plats", CategoriePlatController::add);
        app.put("/categorie-plats/{id}", CategoriePlatController::update);
        app.delete("/categorie-plats/{id}", CategoriePlatController::delete);
    }
}
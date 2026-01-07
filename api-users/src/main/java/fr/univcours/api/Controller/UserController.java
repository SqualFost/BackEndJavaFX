package fr.univcours.api.Controller;


import fr.univcours.api.Main;
import fr.univcours.api.Models.User;
import fr.univcours.api.Services.UserService;

import io.javalin.http.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * Controller qui gère les routes liées aux utilisateurs.
 */
public class UserController {
    // Service utilisé pour gérer les opérations sur les utilisateurs
    private static final UserService userService = new UserService() {
    };

    // Récupère la liste de tous les utilisateurs
    public static void getAllUser(Context ctx) {
        ctx.json(userService.getAllUsers());
    }

    // Recherche des utilisateurs à partir du nom passé en paramètre
    public static void searchByName(Context ctx) {
        String name = ctx.queryParam("name");
        ctx.json(userService.searchByName(name));
    }

    // Récupère un utilisateur à partir de son id
    public static void getUserById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Recherche de l’utilisateur correspondant à l’id
        userService.getUserById(id)
                .ifPresentOrElse(
                        user -> ctx.json(user),
                        () -> ctx.status(404).result("Utilisateur non trouvé")
                );
    }

    // Ajoute un nouvel utilisateur après vérification des données
    public static void addUser(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);
        // Vérifie que le compteur de points commence bien à 0
        if (newUser.getNbPoints() != 0){
            ctx.status(404).result("Compteur de points négatif");
            return;
        }

        // Vérifie que le nom respecte le format attendu
        if (!newUser.getNom().matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\- ]{2,50}$")) {
            ctx.status(400).result("Nom invalide");
            return;
        }

        User created = userService.addUser(newUser);
        ctx.status(201).json(created);
    }

    // Supprime un utilisateur à partir de son id
    public static void deleteUser(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        // Tentative de suppression de l’utilisateur
        boolean suppr = userService.deleteUser(id);

        if (suppr) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Utilisateur non trouvé");
        }
    }

    // Met à jour les informations d’un utilisateur existant
    public static void updateById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        User donneActualisee = ctx.bodyAsClass(User.class);

        // Recherche de l’utilisateur à modifier
        userService.getUserById(id)
                .ifPresentOrElse(
                        user -> {
                            user.setNom(donneActualisee.getNom());
                            user.setNbPoints(donneActualisee.getNbPoints());
                            ctx.json(user);
                        },
                        () -> ctx.status(404).result("Utilisateur non trouvé")
                );
    }

    // Affiche la page d’accueil HTML
    public static void getHome(Context ctx) {
        ctx.html(getWelcomeHTML());
    }


    /**
     * Récupère le fichier HTML d’accueil depuis les ressources
     */
    private static String getWelcomeHTML() {
        try {
            InputStream inputStream = Main.class.getClassLoader()
                    .getResourceAsStream("welcome.html");

            if (inputStream == null) {
                return "<h1>Erreur : Page non trouvée</h1>";
            }

            return new String(inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            return "<h1>Erreur de chargement</h1>";
        }
    }
}

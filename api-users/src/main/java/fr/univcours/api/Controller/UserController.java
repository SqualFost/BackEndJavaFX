package fr.univcours.api.Controller;

import fr.univcours.api.Main;
import fr.univcours.api.code_gener.Models.User;
import fr.univcours.api.ServicesImpl.UserServiceImpl;
import io.javalin.http.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

// Controller pour gérer les endpoints liés aux utilisateurs
public class UserController {

    // Service utilisé pour les opérations sur les utilisateurs
    private static final UserServiceImpl userService = new UserServiceImpl();

    // Récupère tous les utilisateurs
    public static void getAllUser(Context ctx) {
        ctx.json(userService.getAllUsers());
    }

    // Recherche des utilisateurs par nom (query param)
    public static void searchByName(Context ctx) {
        String name = ctx.queryParam("name");
        ctx.json(userService.searchByName(name));
    }

    // Récupère un utilisateur par son id
    public static void getUserById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        userService.getUserById(id)
                .ifPresentOrElse(
                        user -> ctx.json(user), // Utilisateur trouvé → retourne le JSON
                        () -> ctx.status(404).result("Utilisateur non trouvé") // Sinon 404
                );
    }

    // Ajoute un nouvel utilisateur
    public static void addUser(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);

        // Validation du compteur de points (doit être 0 pour la création)
        if (newUser.getNbPoints() != 0) {
            ctx.status(404).result("Compteur de points négatif");
            return;
        }

        // Validation du nom (lettres, accents, espaces, tirets, longueur 2-50)
        if (!newUser.getNom().matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\- ]{2,50}$")) {
            ctx.status(400).result("Nom invalide");
            return;
        }

        // Création via le service
        User created = userService.addUser(newUser);

        // Retourne l'utilisateur créé avec code 201
        ctx.status(201).json(created);
    }

    // Supprime un utilisateur par son id
    public static void deleteUser(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean suppr = userService.deleteUser(id);

        // Si suppression réussie → 204 No Content, sinon 404
        if (suppr) ctx.status(204);
        else ctx.status(404).result("Utilisateur non trouvé");
    }

    // Met à jour un utilisateur existant par son id
    public static void updateById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        User donneActualisee = ctx.bodyAsClass(User.class);

        userService.getUserById(id)
                .ifPresentOrElse(
                        user -> {
                            // Mise à jour des champs de l'utilisateur
                            user.setNom(donneActualisee.getNom());
                            user.setNbPoints(donneActualisee.getNbPoints());

                            // Retourne l'utilisateur mis à jour
                            ctx.json(user);
                        },
                        () -> ctx.status(404).result("Utilisateur non trouvé") // Sinon 404
                );
    }

    // Retourne la page HTML d'accueil
    public static void getHome(Context ctx) {
        ctx.html(getWelcomeHTML());
    }

    /**
     * Charge la page HTML d'accueil depuis les ressources
     */
    private static String getWelcomeHTML() {
        try {
            InputStream inputStream = Main.class.getClassLoader()
                    .getResourceAsStream("welcome.html");

            if (inputStream == null) {
                return "<h1>Erreur : Page non trouvée</h1>";
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            return "<h1>Erreur de chargement</h1>";
        }
    }
}

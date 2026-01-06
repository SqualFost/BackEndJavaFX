package fr.univcours.api.Controller;


import fr.univcours.api.Main;
import fr.univcours.api.Models.User;
import fr.univcours.api.Services.UserService;

import io.javalin.http.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class UserController {
    private static final UserService userService = new UserService() {
    };

    public static void getAllUser(Context ctx) {
        ctx.json(userService.getAllUsers());
    }

    public static void searchByName(Context ctx) {
        String name = ctx.queryParam("name");
        ctx.json(userService.searchByName(name));
    }

    public static void getUserById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        userService.getUserById(id)
                .ifPresentOrElse(
                        user -> ctx.json(user),
                        () -> ctx.status(404).result("Utilisateur non trouvé")
                );
    }

    public static void addUser(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);
        if (newUser.getNbPoints() != 0){
            ctx.status(404).result("Compteur de points négatif");
            return;
        }

        if (!newUser.getNom().matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\- ]{2,50}$")) {
            ctx.status(400).result("Nom invalide");
            return;
        }

        User created = userService.addUser(newUser);
        ctx.status(201).json(created);
    }

    public static void deleteUser(Context ctx) throws SQLException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean suppr = userService.deleteUser(id);

        if (suppr) {
            ctx.status(204);
        } else {
            ctx.status(404).result("Utilisateur non trouvé");
        }
    }

    public static void updateById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        User donneActualisee = ctx.bodyAsClass(User.class);

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

            return new String(inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            return "<h1>Erreur de chargement</h1>";
        }
    }
}

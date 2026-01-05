package fr.univcours.api;

import java.util.List;
import java.util.Optional;

/**
 * Interface qui définit le CRUD pour la gestion des utilisateurs
 */

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(int id);
    User addUser(User user);
    boolean deleteUser(int id);
    List<User> searchByName(String name);
}



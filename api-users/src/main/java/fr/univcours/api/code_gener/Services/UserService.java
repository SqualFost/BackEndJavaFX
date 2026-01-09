package fr.univcours.api.code_gener.Services;

import java.util.List;
import java.util.Optional;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import fr.univcours.api.code_gener.Models.User;

@objid ("d80970ba-e252-4a51-baa7-1defb881e594")
public class UserService {
    @objid ("60fe2490-58bb-41a1-b70c-23815c2db7da")
    public List<User> getAllUsers() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("4185f26b-e5bb-4a91-9bf3-0dfd1ddbbcb9")
    public Optional<User> getUserById(int id) {
        // TODO Auto-generated return
        return null;
    }

    @objid ("2d164adb-237c-40a8-94dc-3f588bdfa08e")
    public User addUser(User user) {
        // TODO Auto-generated return
        return null;
    }

    @objid ("f6dc37fb-e890-45b7-a7b1-e2170f701a23")
    public List<User> searchByName(String nom) {
        // TODO Auto-generated return
        return null;
    }

    @objid ("da4adf4c-b6da-4ed1-ab55-aeeba7e1ba9e")
    public User updateById(int id, User userData) {
        // TODO Auto-generated return
        return null;
    }

    @objid ("b1bc9b1f-e74b-4902-bba3-df31ae596ba8")
    public boolean deleteUser(int id) {
        // TODO Auto-generated return
        return false;
    }

    @objid ("4eeef334-adb5-4857-9a99-e2c29ff85c6d")
    public User mapResultSetToUser(ResultSet rs) {
        // TODO Auto-generated return
        return null;
    }

}

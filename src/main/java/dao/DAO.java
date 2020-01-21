package dao;

import dao.models.Email;
import dao.models.User;

import java.util.List;

public interface DAO {

    void addNewUser(String name, List<Email> emails);

    User getUserByName(String name);

    void editUser(int id, String name, List<Email> emails);

    void removeUser(String name);

    List<User> getUsers();

    List<Email> getEmailAddresses();
}

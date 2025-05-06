package repository;

import entity.*;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean exists(String username) {
        return users.containsKey(username);
    }

    public Map<String, User> getAllUsers() {
        return users;
    }
}

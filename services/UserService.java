package services;

import entity.*;
import repository.UserRepository;

public class UserService {
    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean register(String username, String password, String role) {
        if (userRepo.exists(username)) return false;

        User user;
        switch (role.toUpperCase()) {
            case "ADMIN":
                user = new Admin(username, password);
                break;
            case "RESOURCE_MANAGER":
                user = new ResourceManager(username, password);
                break;
            case "REGULAR_USER":
                user = new RegularUser(username, password);
                break;
            default:
                return false;
        }

        userRepo.addUser(user);
        return true;
    }

    public User login(String username, String password) {
        User user = userRepo.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

package utils;

import entity.*;
import repository.ResourceRepository;
import repository.UserRepository;

public class DataSeeder {

    public static void seedData(UserRepository userRepo, ResourceRepository resourceRepo) {
        // Add default users
        userRepo.addUser(new Admin("admin", "adminpass"));
        userRepo.addUser(new ResourceManager("manager", "managerpass"));
        userRepo.addUser(new RegularUser("user1", "user1pass"));

        // Add some resources
        resourceRepo.addResource(new Resource("Piano", "Musical Instrument", 50.0));
        resourceRepo.addResource(new Resource("Guitar", "Musical Instrument", 30.0));
        resourceRepo.addResource(new Resource("Drum Kit", "Musical Instrument", 40.0));
    }
}

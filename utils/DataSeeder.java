package utils;

import entity.*;
import repository.ResourceRepository;
import repository.UserRepository;

public class DataSeeder {

    public static void seedData(UserRepository userRepo, ResourceRepository resourceRepo) {
        // Add default users
        userRepo.addUser(new Admin("admin", "admin"));
        userRepo.addUser(new ResourceManager("resource", "resource"));
        userRepo.addUser(new RegularUser("ayush", "ayush"));
        userRepo.addUser(new RegularUser("ish", "ish"));
        userRepo.addUser(new RegularUser("animesh", "animesh"));

        // Add some resources
        resourceRepo.addResource(new Resource("Piano", "Musical Instrument", 50.0,2));
        resourceRepo.addResource(new Resource("Guitar", "Musical Instrument", 30.0,2));
        resourceRepo.addResource(new Resource("Drum Kit", "Musical Instrument", 40.0,3));
    }
}

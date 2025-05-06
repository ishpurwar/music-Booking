package controller;

import entity.*;
import services.*;
import utils.ConsoleUI;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuController {

    private UserService userService;
    private ResourceService resourceService;
    private BookingService bookingService;
    private ReportService reportService;
    private User currentUser;

    public MenuController(UserService userService, ResourceService resourceService, BookingService bookingService, ReportService reportService) {
        this.userService = userService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
        this.reportService = reportService;
    }

    public void start() {
        while (true) {
            ConsoleUI.clearScreen();
            System.out.println("=== Music Booking System ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            
            int choice = ConsoleUI.getIntInput("Choose an option: ");
            ConsoleUI.consumeNextLine(); // Consume the newline after getIntInput
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Thank you for using the Music Booking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    ConsoleUI.waitForEnter();
            }
        }
    }
    
    private void login() {
        ConsoleUI.clearScreen();
        System.out.println("=== Login ===");
        String username = ConsoleUI.getInput("Enter Username: ");
        String password = ConsoleUI.getInput("Enter Password: ");

        currentUser = userService.login(username, password);
        if (currentUser == null) {
            ConsoleUI.displayMessage("Invalid username or password.");
            ConsoleUI.waitForEnter();
            return;
        }
        
        handleUserSession();
    }
    
    private void register() {
        ConsoleUI.clearScreen();
        System.out.println("=== Register ===");
        String username = ConsoleUI.getInput("Enter Username: ");
        String password = ConsoleUI.getInput("Enter Password: ");
        
        System.out.println("Select Role:");
        System.out.println("1. Regular User");
        System.out.println("2. Resource Manager");
        int roleChoice = ConsoleUI.getIntInput("Choose role: ");
        ConsoleUI.consumeNextLine(); // Consume the newline
        
        String role = "REGULAR_USER";
        if (roleChoice == 2) {
            role = "RESOURCE_MANAGER";
        }
        
        boolean success = userService.register(username, password, role);
        if (success) {
            ConsoleUI.displayMessage("Registration successful! You can now login.");
        } else {
            ConsoleUI.displayMessage("Username already exists. Please try again.");
        }
        ConsoleUI.waitForEnter();
    }
    
    private void handleUserSession() {
        while (currentUser != null) {
            ConsoleUI.clearScreen();
            System.out.println("Welcome, " + currentUser.getUsername() + "!");
            System.out.println();
            
            currentUser.displayMenu();
            int choice = ConsoleUI.getIntInput("Choose an option: ");
            ConsoleUI.consumeNextLine(); // Consume the newline

            switch (currentUser.getRole()) {
                case "ADMIN":
                    handleAdminMenu(choice);
                    break;
                case "RESOURCE_MANAGER":
                    handleResourceManagerMenu(choice);
                    break;
                case "REGULAR_USER":
                    handleUserMenu(choice);
                    break;
            }
            
            // Pause after each action unless user logged out
            if (currentUser != null) {
                ConsoleUI.waitForEnter();
            }
        }
    }

    private void handleAdminMenu(int choice) {
        switch (choice) {
            case 1:
                // View all bookings
                ConsoleUI.clearScreen();
                System.out.println("=== All Bookings ===");
                reportService.printBookingStats(bookingService.getAllBookings());
                break;
            case 2:
                // Generate reports (e.g., user activity)
                ConsoleUI.clearScreen();
                System.out.println("=== Reports ===");
                reportService.generateReport(bookingService.getAllBookings());
                break;
            case 3:
                // Logout
                ConsoleUI.displayMessage("Logging out...");
                currentUser = null;
                return;
            default:
                ConsoleUI.displayMessage("Invalid option. Please try again.");
        }
    }

    private void handleResourceManagerMenu(int choice) {
        switch (choice) {
            case 1:
                // Add resource
                ConsoleUI.clearScreen();
                System.out.println("=== Add New Resource ===");
                String name = ConsoleUI.getInput("Enter Resource Name: ");
                
                System.out.println("Choose Resource Type:");
                System.out.println("1. Musical Instrument");
                System.out.println("2. Studio Room");
                System.out.println("3. Audio Equipment");
                int typeChoice = ConsoleUI.getIntInput("Select type (1-3): ");
                ConsoleUI.consumeNextLine(); // Consume the newline
                
                String type;
                switch (typeChoice) {
                    case 1: type = "Musical Instrument"; break;
                    case 2: type = "Studio Room"; break;
                    case 3: type = "Audio Equipment"; break;
                    default: type = "Other";
                }
                
                double costPerHour = ConsoleUI.getDoubleInput("Enter Cost per Hour ($): ");
                resourceService.addResource(name, type, costPerHour);
                ConsoleUI.displayMessage("Resource added successfully!");
                break;
                
            case 2:
                // View resources
                ConsoleUI.clearScreen();
                System.out.println("=== Available Resources ===");
                displayResourceList(resourceService.getAllResources());
                break;
                
            case 3:
                // Logout
                ConsoleUI.displayMessage("Logging out...");
                currentUser = null;
                return;
                
            default:
                ConsoleUI.displayMessage("Invalid option. Please try again.");
        }
    }

    private void handleUserMenu(int choice) {
        switch (choice) {
            case 1:
                // View resources
                ConsoleUI.clearScreen();
                System.out.println("=== Available Resources ===");
                displayResourceList(resourceService.getAllResources());
                break;
                
            case 2:
                // Book resource
                ConsoleUI.clearScreen();
                System.out.println("=== Book a Resource ===");
                displayResourceList(resourceService.getAllResources());
                
                int resourceId = ConsoleUI.getIntInput("Enter Resource ID to book (0 to cancel): ");
                ConsoleUI.consumeNextLine(); // Consume the newline
                
                if (resourceId == 0) {
                    ConsoleUI.displayMessage("Booking canceled.");
                    return;
                }
                
                Resource resource = resourceService.getById(resourceId);
                if (resource != null) {
                    System.out.println("Selected: " + resource.getName() + " - $" + resource.getCostPerHour() + "/hr");
                    
                    System.out.println("\nEnter dates in format: yyyy-MM-dd HH:mm (e.g. 2025-05-06 14:30)");
                    String startDate = ConsoleUI.getInput("Enter start time: ");
                    String endDate = ConsoleUI.getInput("Enter end time: ");
                    
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
                        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
                        
                        if (end.isBefore(start) || end.isEqual(start)) {
                            ConsoleUI.displayMessage("End time must be after start time.");
                            return;
                        }
                        
                        DateTimeRange range = new DateTimeRange(start, end);
                        if (bookingService.book(currentUser, resource, range)) {
                            ConsoleUI.displayMessage("Booking successful!");
                        } else {
                            ConsoleUI.displayMessage("Time slot is already booked or unavailable.");
                        }
                    } catch (Exception e) {
                        ConsoleUI.displayMessage("Invalid date format. Please try again.");
                    }
                } else {
                    ConsoleUI.displayMessage("Resource not found.");
                }
                break;
                
            case 3:
                // View bookings
                ConsoleUI.clearScreen();
                System.out.println("=== My Bookings ===");
                boolean found = false;
                for (Booking booking : bookingService.getAllBookings()) {
                    if (booking.getUser().getUsername().equals(currentUser.getUsername())) {
                        System.out.println(booking);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("You have no bookings.");
                }
                break;
                
            case 4:
                // Logout
                ConsoleUI.displayMessage("Logging out...");
                currentUser = null;
                return;
                
            default:
                ConsoleUI.displayMessage("Invalid option. Please try again.");
        }
    }
    
    private void displayResourceList(List<Resource> resources) {
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
        } else {
            System.out.println("ID | Name | Type | Cost/Hour");
            System.out.println("----------------------------");
            for (Resource r : resources) {
                System.out.printf("%d | %s | %s | $%.2f\n", 
                    r.getId(), r.getName(), r.getType(), r.getCostPerHour());
            }
            System.out.println();
        }
    }
}
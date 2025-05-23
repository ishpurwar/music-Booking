package controller;

import entity.*;
import services.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuController {

    private UserService userService;
    private ResourceService resourceService;
    private BookingService bookingService;
    private ReportService reportService;
    private User currentUser;
    Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public MenuController(UserService userService, ResourceService resourceService, BookingService bookingService,
            ReportService reportService) {
        this.userService = userService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
        this.reportService = reportService;
    }

    public void start() {
        while (true) {
            System.out.println("=== Music Booking System ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int choice = getValidIntInput("Choose an option: ");
            scanner.nextLine(); // Clear buffer

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
                    waitForEnter();
            }
        }
    }

    private int getValidIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private double getValidDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void login() {
        System.out.println("=== Login ===");
        System.out.println("Enter Username:");
        String username = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        currentUser = userService.login(username, password);
        if (currentUser == null) {
            System.out.println("Invalid username or password.");
            waitForEnter();
            return;
        }

        handleUserSession();
    }

    private void register() {
        System.out.println("=== Register ===");
        System.out.println("Enter Username:");

        String username = scanner.nextLine();
        if (username.matches("\\d+")) {
            System.out.println("Username cannot be a complete number.");
            waitForEnter();
            return;
        }
        if (!username.matches("[a-zA-Z0-9_]+")) {
            System.out.println("Username can only contain letters, numbers, and underscores (_).");
            waitForEnter();
            return;
        }
        if (username.contains(" ")) {
            System.out.println("Username cannot contain spaces.");
            waitForEnter();
            return;
        }
        
        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            System.out.println("Password must be at least 8 characters long and contain a mix of letters and numbers.");
            waitForEnter();
            return;
        }
        System.out.println("Select Role:");
        System.out.println("1. Regular User");
        System.out.println("2. Resource Manager");

        int roleChoice = getValidIntInput("Choose role: ");
        scanner.nextLine();

        String role = "REGULAR_USER";
        if (roleChoice == 2) {
            role = "RESOURCE_MANAGER";
        }

        boolean success = userService.register(username, password, role);
        if (success) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Username already exists. Please try again.");
        }
        waitForEnter();
    }

    private void handleUserSession() {
        while (currentUser != null) {
            System.out.println("\nWelcome, " + currentUser.getUsername() + "!");
            System.out.println();

            currentUser.displayMenu();
            int choice = getValidIntInput("Choose an option: ");
            scanner.nextLine();

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
                waitForEnter();
            }
        }
    }

    private void handleAdminMenu(int choice) {
        switch (choice) {
            case 1:
                System.out.println("=== All Bookings ===");
                List<Booking> allBookings = bookingService.getAllBookings();
                if (allBookings.isEmpty()) {
                    System.out.println("No bookings found.");
                } else {
                    for (Booking booking : allBookings) {
                        System.out.println(booking);
                    }
                }
                break;
            case 2:
                System.out.println("=== Reports ===");
                reportService.generateReport(bookingService.getAllBookings());
                break;
            case 3:
                // Logout
                System.out.println("Logging out...");
                currentUser = null;
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void handleResourceManagerMenu(int choice) {
        switch (choice) {
            case 1:
                System.out.println("=== Add New Resource ===");
                System.out.println("Enter Resource Name:");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Resource name cannot be empty.");
                    return;
                }
                if (!name.matches("[a-zA-Z]+")) { //AlphaNumeric Only 
                    System.out.println("Resource name can only contain alphabetic characters.");
                    return;
                }
                System.out.println("Choose Resource Type:");
                System.out.println("1. Musical Instrument");
                System.out.println("2. Studio Room");
                System.out.println("3. Audio Equipment");
                int typeChoice = getValidIntInput("Select type (1-3): ");
                scanner.nextLine();

                String type;
                switch (typeChoice) {
                    case 1:
                        type = "Musical Instrument";
                        break;
                    case 2:
                        type = "Studio Room";
                        break;
                    case 3:
                        type = "Audio Equipment";
                        break;
                    default:
                        type = "Other";
                }

                double costPerHour = getValidDoubleInput("Enter Cost per Hour ($): ");
                if (costPerHour <= 0) {
                    System.out.println("Cost must be greater than zero.");
                    return;
                }

                int quantity = getValidIntInput("Enter Quantity: ");
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than zero.");
                    return;
                }
                scanner.nextLine();

                boolean isNewResource = resourceService.addResource(name, type, costPerHour, quantity);
                if (isNewResource) {
                    System.out.println("New resource added successfully! ");
                } else {
                    System.out.println("Resource already exists. Quantity and Cost has been updated!");
                }
                break;

            case 2:
                System.out.println("=== Available Resources ===");
                displayResourceList(resourceService.getAllResources());
                break;
                
            case 3:
            System.out.println("=== Remove Resource Quantity ===");
            displayResourceList(resourceService.getAllResources());
            
            int resourceId = getValidIntInput("Enter Resource ID to remove quantity (0 to cancel): ");
            scanner.nextLine();
            
            if (resourceId == 0) {
                System.out.println("Operation canceled.");
                return;
            }
            
            Resource resource = resourceService.getById(resourceId);
            if (resource == null) {
                System.out.println("Resource not found.");
                return;
            }
            
            // Calculate the maximum quantity that can be removed
            int maxRemovable = resourceService.getMaxRemovableQuantity(resourceId);
            
            System.out.println("Selected: " + resource.getName() + " - Current quantity: " + resource.getQuantity());
            System.out.println("Maximum quantity that can be removed: " + maxRemovable);
            
            if (maxRemovable == 0) {
                System.out.println("All units are currently booked. Cannot remove any units at this time.");
                return;
            }
            
            int quantityToRemove = getValidIntInput("Enter quantity to remove (1-" + maxRemovable + "): ");
            scanner.nextLine();
            
            if (quantityToRemove <= 0 || quantityToRemove > maxRemovable) {
                System.out.println("Invalid quantity. Operation canceled.");
                return;
            }
            
            boolean removed = resourceService.removeResourceQuantity(resourceId, quantityToRemove);
            if (removed) {
                System.out.println("Successfully removed " + quantityToRemove + " units of " + resource.getName());
            } else {
                System.out.println("Failed to remove quantity. This may be because:");
                System.out.println("The requested quantity exceeds the available amount");
            }
            break;

            case 4:
                // Logout
                System.out.println("Logging out...");
                currentUser = null;
                return;

            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void handleUserMenu(int choice) {
        switch (choice) {
            case 1:
                System.out.println("=== Available Resources ===");
                viewResourceList(resourceService.getAllResources());
                break;

            case 2:
                System.out.println("=== Book a Resource ===");

                // First, get the time range for booking
                System.out.println("\nEnter dates in format: yyyy-MM-dd HH:mm (e.g. 2025-05-06 14:30)");
                System.out.println("Enter start time:");
                String startDate = scanner.nextLine();
                System.out.println("Enter end time:");
                String endDate = scanner.nextLine();

                try {
                    LocalDateTime start = LocalDateTime.parse(startDate, formatter);
                    LocalDateTime end = LocalDateTime.parse(endDate, formatter);

                    if (end.isBefore(start) || end.isEqual(start)) {
                        System.out.println("End time must be after start time.");
                        return;
                    }

                    DateTimeRange range = new DateTimeRange(start, end);

                    // Get resources available during that time
                    List<Resource> availableResources = bookingService.getAvailableResources(
                            resourceService.getAllResources(), range);

                    // Display available resources
                    System.out.println("=== Available Resources for Selected Time ===");
                    if (availableResources.isEmpty()) {
                        System.out.println("No resources available for the selected time period.");
                        return;
                    }

                    displayResourceList(availableResources);

                    int resourceId = getValidIntInput("Enter Resource ID to book (0 to cancel): ");
                    scanner.nextLine();

                    if (resourceId == 0) {
                        System.out.println("Booking canceled.");
                        return;
                    }

                    Resource resource = resourceService.getById(resourceId);
                    if (resource != null) {
                        System.out.println(
                                "Selected: " + resource.getName() + " - $" + resource.getCostPerHour() + "/hr");

                        // Get resource availability during selected time
                        List<Resource> available = bookingService.getAvailableResources(List.of(resource), range);
                        if (available.isEmpty()) {
                            System.out.println("This resource is no longer available for the selected time.");
                            return;
                        }

                        int maxAvailable = available.get(0).getAvailableQuantity();
                        System.out.println("Available quantity: " + maxAvailable);

                        int bookQuantity = getValidIntInput("Enter quantity to book (max " + maxAvailable + "): ");
                        scanner.nextLine();

                        if (bookQuantity <= 0 || bookQuantity > maxAvailable) {
                            System.out.println("Invalid quantity. Booking canceled.");
                            return;
                        }

                        if (bookingService.book(currentUser, resource, range, bookQuantity)) {
                            double cost = Calculator.calculateCost(resource, range, bookQuantity);
                            System.out.printf("Booking successful! Total cost: $%.2f\n", cost);
                        } else {
                            System.out.println("Booking failed. The resource might no longer be available.");
                        }
                    } else {
                        System.out.println("Resource not found.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid date format or input. Please try again. Error: " + e.getMessage());
                }
                break;

            case 3:
                System.out.println("=== My Bookings ===");
                List<Booking> userBookings = bookingService.getUserBookings(currentUser);
                if (userBookings.isEmpty()) {
                    System.out.println("You have no bookings.");
                } else {
                    System.out.println("Index | Resource | Time Range | Cost");
                    System.out.println("------------------------------------------");
                    for (int i = 0; i < userBookings.size(); i++) {
                        Booking booking = userBookings.get(i);
                        System.out.printf("%d | %s | %s | $%.2f\n", 
                            i, booking.getResource().getName(), 
                            booking.getTimeRange(), booking.getTotalCost());
                    }
                }
                break;
                
            case 4:
                System.out.println("=== Cancel Booking ===");
                List<Booking> bookings = bookingService.getUserBookings(currentUser);
                if (bookings.isEmpty()) {
                    System.out.println("You have no bookings to cancel.");
                    return;
                }
                
                System.out.println("Your current bookings:");
                System.out.println("Index | Resource | Time Range | Cost");
                System.out.println("------------------------------------------");
                for (int i = 0; i < bookings.size(); i++) {
                    Booking booking = bookings.get(i);
                    System.out.printf("%d | %s | %s | $%.2f\n", 
                        i, booking.getResource().getName(), 
                        booking.getTimeRange(), booking.getTotalCost());
                }
                
                int bookingIndex = getValidIntInput("Enter the index of the booking to cancel (or -1 to cancel operation): ");
                scanner.nextLine();
                
                if (bookingIndex == -1) {
                    System.out.println("Operation canceled.");
                    return;
                }
                
                if (bookingService.cancelBooking(currentUser, bookingIndex)) {
                    System.out.println("Booking canceled successfully!");
                } else {
                    System.out.println("Failed to cancel booking. Please check the booking index.");
                }
                break;

            case 5:
                // Logout
                System.out.println("Logging out...");
                currentUser = null;
                return;

            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void displayResourceList(List<Resource> resources) {
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
        } else {
            System.out.println("ID | Name | Type | Cost/Hour | Available/Total");
            System.out.println("------------------------------------------");
            for (Resource r : resources) {
                // Make sure available quantity is never displayed as negative
                if (r.getQuantity()== 0) {
                    continue; // Skip resources with zero quantity
                    
                }
                int availableToDisplay = Math.max(0, r.getAvailableQuantity());
                System.out.printf("%d | %s | %s | $%.2f | %d/%d\n",
                        r.getId(), r.getName(), r.getType(), r.getCostPerHour(),
                        availableToDisplay, r.getQuantity());
            }
            System.out.println();
        }
    }
    
    private void viewResourceList(List<Resource> resources) {
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
        } else {
            System.out.println("ID | Name | Type | Cost/Hour ");
            System.out.println("------------------------------------------");
            for (Resource r : resources) {
                if (r.getQuantity()==0) {
                    continue; // Skip resources with zero quantity
                    
                }
                System.out.printf("%d | %s | %s | $%.2f\n",
                        r.getId(), r.getName(), r.getType(), r.getCostPerHour());
            }
            System.out.println();
        }
    }
}
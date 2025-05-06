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

            int choice = -1;
            while (true) {
                try {
                    System.out.print("Choose an option: ");
                    int value = scanner.nextInt();
                    choice = value;
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine(); 
                }
            }
            scanner.nextLine(); 

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
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
            }
        }
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
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }

        handleUserSession();
    }

    private void register() {

        System.out.println("=== Register ===");
        System.out.println("Enter Username:");
        String username = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            System.out.println("Password must be at least 8 characters long and contain a mix of letters and numbers.");
            return;
        }
        System.out.println("Select Role:");
        System.out.println("1. Regular User");
        System.out.println("2. Resource Manager");

        int roleChoice = -1;
        while (true) {
            try {
                System.out.print("Choose role:");
                int value = scanner.nextInt();
                roleChoice = value;
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }

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
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void handleUserSession() {
        while (currentUser != null) {
            System.out.println("Welcome, " + currentUser.getUsername() + "!");
            System.out.println();

            currentUser.displayMenu();
            int choice = -1;
            while (true) {
                try {
                    System.out.print("Choose an option: ");
                    int value = scanner.nextInt();
                    choice = value;
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine(); 
                }
            }
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
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    private void handleAdminMenu(int choice) {
        switch (choice) {
            case 1:
                System.out.println("=== All Bookings ===");
                reportService.printBookingStats(bookingService.getAllBookings());
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

                System.out.println("Choose Resource Type:");
                System.out.println("1. Musical Instrument");
                System.out.println("2. Studio Room");
                System.out.println("3. Audio Equipment");
                int typeChoice = -1;
                while (true) {
                    try {
                        System.out.print("Select type (1-3): ");
                        int value = scanner.nextInt();
                        typeChoice = value;
                        break;
                    } catch (Exception e) {
                        System.out.println("Please enter a valid number.");
                        scanner.nextLine(); 
                    }
                }
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

                double costPerHour = -1;
                while (true) {
                    try {
                        System.out.print("Enter Cost per Hour ($): ");
                        double value = scanner.nextDouble();
                        costPerHour = value;
                        break;
                    } catch (Exception e) {
                        System.out.println("Please enter a valid number.");
                        scanner.nextLine(); 
                    }
                }
                resourceService.addResource(name, type, costPerHour);
                System.out.println("Resource added successfully!");
                break;

            case 2:
                System.out.println("=== Available Resources ===");
                displayResourceList(resourceService.getAllResources());
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

    private void handleUserMenu(int choice) {
        switch (choice) {
            case 1:
                System.out.println("=== Available Resources ===");
                displayResourceList(resourceService.getAllResources());
                break;

            case 2:
                System.out.println("=== Book a Resource ===");
                displayResourceList(resourceService.getAllResources());

                int resourceId =-1;
                while (true) {
                    try {
                        System.out.print("Enter Resource ID to book (0 to cancel): ");
                        int value = scanner.nextInt();
                        resourceId = value;
                        break;
                    } catch (Exception e) {
                        System.out.println("Please enter a valid number.");
                        scanner.nextLine(); 
                    }
                }
                scanner.nextLine();

                if (resourceId == 0) {
                    System.out.println("Booking canceled.");
                    return;
                }

                Resource resource = resourceService.getById(resourceId);
                if (resource != null) {
                    System.out.println("Selected: " + resource.getName() + " - $" + resource.getCostPerHour() + "/hr");

                    System.out.println("\nEnter dates in format: yyyy-MM-dd HH:mm (e.g. 2025-05-06 14:30)");
                    System.out.println("Enter start time:");
                    String startDate = scanner.nextLine();
                    System.out.println("Enter end time:");
                    String endDate = scanner.nextLine();

                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
                        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

                        if (end.isBefore(start) || end.isEqual(start)) {
                            System.out.println("End time must be after start time.");
                            return;
                        }

                        DateTimeRange range = new DateTimeRange(start, end);
                        if (bookingService.book(currentUser, resource, range)) {
                            System.out.println("Booking successful!");
                        } else {
                            System.out.println("Time slot is already booked or unavailable.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please try again.");
                    }
                } else {
                    System.out.println("Resource not found.");
                }
                break;

            case 3:
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
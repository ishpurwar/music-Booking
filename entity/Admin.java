package entity;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "ADMIN");
    }

    @Override
    public void displayMenu() {
        System.out.println("=== Admin Menu ===");
        System.out.println("1. View All Bookings");
        System.out.println("2. Generate Reports");
        System.out.println("3. Logout");
    }
}
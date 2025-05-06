package entity;

public class RegularUser extends User {
    public RegularUser(String username, String password) {
        super(username, password, "REGULAR_USER");
    }

    @Override
    public void displayMenu() {
        System.out.println("=== User Menu ===");
        System.out.println("1. View Available Resources");
        System.out.println("2. Book Resource");
        System.out.println("3. View My Bookings");
        System.out.println("4. Logout");
    }
}
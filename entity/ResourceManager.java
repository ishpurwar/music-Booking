package entity;

public class ResourceManager extends User {
    public ResourceManager(String username, String password) {
        super(username, password, "RESOURCE_MANAGER");
    }

    @Override
    public void displayMenu() {
        System.out.println("=== Resource Manager Menu ===");
        System.out.println("1. Add New Resource");
        System.out.println("2. View All Resources");
        System.out.println("3. Logout");
    }
}
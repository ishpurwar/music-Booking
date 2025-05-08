package entity;

public class Resource {
    private static int idCounter = 1;
    private final int id;
    private String name;
    private String type;
    private double costPerHour;
    private int quantity;
    private int availableQuantity;

    public Resource(String name, String type, double costPerHour, int quantity) {
        this.id = idCounter++;
        this.name = name;
        this.type = type;
        this.costPerHour = costPerHour;
        this.quantity = quantity;
        this.availableQuantity = quantity; 
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getCostPerHour() { return costPerHour; }
    public int getQuantity() { return quantity; }
    public int getAvailableQuantity() { return availableQuantity; }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.availableQuantity = quantity;
    }
    
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    
    public void incrementQuantity(int amount) {
        this.quantity += amount;
        this.availableQuantity += amount;
    }
    public void setCostPerHour(double costPerHour) {
        this.costPerHour = costPerHour;
    }
    @Override
    public String toString() {
        return id + ". " + name + " (" + type + ") - $" + costPerHour + "/hr";
    }
}
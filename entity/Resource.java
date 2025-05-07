package entity;

public class Resource {
    private static int idCounter = 1;
    private final int id;
    private String name;
    private String type;
    private double costPerHour;
    

    public Resource(String name, String type, double costPerHour) {
        this.id = idCounter++;
        this.name = name;
        this.type = type;
        this.costPerHour = costPerHour;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getCostPerHour() { return costPerHour; }

    @Override
    public String toString() {
        return id + ". " + name + " (" + type + ") - $" + costPerHour + "/hr";
    }
}

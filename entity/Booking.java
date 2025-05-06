package entity;

public class Booking {
    private User user;
    private Resource resource;
    private DateTimeRange timeRange;
    private double totalCost;

    public Booking(User user, Resource resource, DateTimeRange timeRange, double totalCost) {
        this.user = user;
        this.resource = resource;
        this.timeRange = timeRange;
        this.totalCost = totalCost;
    }

    public User getUser() { return user; }
    public Resource getResource() { return resource; }
    public DateTimeRange getTimeRange() { return timeRange; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return "Booking: " + resource.getName() + " by " + user.getUsername() + 
               " from " + timeRange + ", Cost: $" + totalCost;
    }
}

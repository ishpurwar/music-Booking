package entity;

public class ResourceSelection {
    private Resource resource;
    private DateTimeRange timeRange;

    public ResourceSelection(Resource resource, DateTimeRange timeRange) {
        this.resource = resource;
        this.timeRange = timeRange;
    }

    public Resource getResource() { return resource; }
    public DateTimeRange getTimeRange() { return timeRange; }
}

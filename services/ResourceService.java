package services;

import entity.Resource;
import repository.ResourceRepository;

import java.util.List;

public class ResourceService {
    private ResourceRepository repo;

    public ResourceService(ResourceRepository repo) {
        this.repo = repo;
    }

    public boolean addResource(String name, String type, double costPerHour, int quantity) {
        // Check if a similar resource exists
        Resource existingResource = repo.findSimilarResource(name, type);
        
        if (existingResource != null) {
            // Increment quantity of existing resource
            existingResource.incrementQuantity(quantity);
            existingResource.setCostPerHour(costPerHour); 
            return false; // Return false to indicate no new resource was created
        } else {
            // Create new resource
            Resource resource = new Resource(name, type, costPerHour, quantity);
            repo.addResource(resource);
            return true; // Return true to indicate a new resource was created
        }
    }

    public List<Resource> getAllResources() {
        return repo.getAllResources();
    }

    public Resource getById(int id) {
        return repo.getResourceById(id);
    }
}
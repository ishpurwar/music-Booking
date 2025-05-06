package services;

import entity.Resource;
import repository.ResourceRepository;

import java.util.List;

public class ResourceService {
    private ResourceRepository repo;

    public ResourceService(ResourceRepository repo) {
        this.repo = repo;
    }

    public void addResource(String name, String type, double costPerHour) {
        Resource resource = new Resource(name, type, costPerHour);
        repo.addResource(resource);
    }

    public List<Resource> getAllResources() {
        return repo.getAllResources();
    }

    public Resource getById(int id) {
        return repo.getResourceById(id);
    }
}

package repository;

import entity.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceRepository {
    private List<Resource> resources = new ArrayList<>();

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public List<Resource> getAllResources() {
        return resources;
    }

    public Resource getResourceById(int id) {
        /* for (Resource r : resources) {
            if (r.getId() == id) return r;
        }
        return null; */
        return resources.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public Resource findSimilarResource(String name, String type) {
        /* for (Resource r : resources) {
            if (r.getName().equalsIgnoreCase(name) && r.getType().equalsIgnoreCase(type)) {
                return r;
            }
        }
        return null; */
        return resources.stream()
                .filter(r -> r.getName().equalsIgnoreCase(name) && r.getType().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}

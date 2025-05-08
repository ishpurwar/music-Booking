package services;

import entity.Booking;
import entity.DateTimeRange;
import entity.Resource;
import repository.BookingRepository;
import repository.ResourceRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceService {
    private ResourceRepository repo;
    private BookingRepository bookingRepo;

    public ResourceService(ResourceRepository repo, BookingRepository bookingRepo) {
        this.repo = repo;
        this.bookingRepo = bookingRepo;
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
    
  
    public int getMaxRemovableQuantity(int resourceId) {
        Resource resource = repo.getResourceById(resourceId);
        if (resource == null) {
            return 0;
        }
        
        List<Booking> resourceBookings = bookingRepo.getBookingsForResource(resourceId);
        if (resourceBookings.isEmpty()) {
            return resource.getQuantity();
        }
        
        List<DateTimeRange> timeRangesToCheck = new ArrayList<>();
        
        for (Booking booking : resourceBookings) {
            timeRangesToCheck.add(booking.getTimeRange());
        }
        
        int maxBookedAtAnyTime = 0;
        
        for (DateTimeRange timeRange : timeRangesToCheck) {
            int bookedInThisRange = 0;
            
            for (Booking booking : resourceBookings) {
                if (booking.getTimeRange().overlaps(timeRange)) {
                    bookedInThisRange += booking.getQuantity();
                }
            }
            
            maxBookedAtAnyTime = Math.max(maxBookedAtAnyTime, bookedInThisRange);
        }
        
        return Math.max(0, resource.getQuantity() - maxBookedAtAnyTime);
    }
    
   
    public boolean removeResourceQuantity(int resourceId, int quantityToRemove) {
        Resource resource = repo.getResourceById(resourceId);
        if (resource == null) {
            return false;
        }
        
        if (resource.getQuantity() < quantityToRemove) {
            return false;
        }
        
        int maxRemovable = getMaxRemovableQuantity(resourceId);
        if (quantityToRemove > maxRemovable) {
            return false;
        }
        
        int bookedQuantity = resource.getQuantity() - resource.getAvailableQuantity();
        
        int newTotalQuantity = resource.getQuantity() - quantityToRemove;
        resource.setQuantity(newTotalQuantity);
        
        int newAvailable = Math.max(0, newTotalQuantity - bookedQuantity);
        resource.setAvailableQuantity(newAvailable);
        
        return true;
    }
}
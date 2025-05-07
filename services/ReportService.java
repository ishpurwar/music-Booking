package services;

import entity.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    
    public void printBookingStats(List<Booking> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }
        
        System.out.println("Total bookings: " + bookings.size());
        double totalRevenue = 0;
        
        Map<String, Integer> bookingsByResourceType = new HashMap<>();
        
        for (Booking booking : bookings) {
            totalRevenue += booking.getTotalCost();
            
            String type = booking.getResource().getType();
            bookingsByResourceType.put(type, bookingsByResourceType.getOrDefault(type, 0) + 1);
        }
        
        System.out.printf("Total revenue: $%.2f\n", totalRevenue);
        System.out.println("\nBookings by resource type:");
        for (Map.Entry<String, Integer> entry : bookingsByResourceType.entrySet()) {
            System.out.printf("- %s: %d bookings\n", entry.getKey(), entry.getValue());
        }
    }
    
    public void generateReport(List<Booking> bookings) {
        printBookingStats(bookings);
        
        if (bookings.isEmpty()) return;
        
        System.out.println("\n=== Detailed Report ===");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        
        // Bookings by user
        Map<String, Integer> bookingsByUser = new HashMap<>();
        for (Booking booking : bookings) {
            String username = booking.getUser().getUsername();
            bookingsByUser.put(username, bookingsByUser.getOrDefault(username, 0) + 1);
        }
        
        System.out.println("\nBookings by user:");
        for (Map.Entry<String, Integer> entry : bookingsByUser.entrySet()) {
            System.out.printf("- %s: %d bookings\n", entry.getKey(), entry.getValue());
        }
        
        // Resource utilization
        Map<String, Integer> resourceHours = new HashMap<>();
        for (Booking booking : bookings) {
            String resourceName = booking.getResource().getName();
            int hours = (int) booking.getTimeRange().getDurationInHours();
            resourceHours.put(resourceName, resourceHours.getOrDefault(resourceName, 0) + hours);
        }
        
        System.out.println("\nResource utilization (hours):");
        for (Map.Entry<String, Integer> entry : resourceHours.entrySet()) {
            System.out.printf("- %s: %d hours\n", entry.getKey(), entry.getValue());
        }
    }
}
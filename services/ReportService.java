package services;

import entity.Booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    public void printBookingStats(List<Booking> bookings) {
        System.out.println("=== Booking Summary ===");
        Map<String, Integer> userCount = new HashMap<>();

        for (Booking b : bookings) {
            userCount.put(b.getUser().getUsername(), userCount.getOrDefault(b.getUser().getUsername(), 0) + 1);
            System.out.println(b);
        }

        System.out.println("\nBookings per user:");
        for (String user : userCount.keySet()) {
            System.out.println(user + ": " + userCount.get(user) + " bookings");
        }
    }
    public void generateReport(List<Booking> bookings) {
        System.out.println("=== Booking Report ===");
        System.out.println("Total Bookings: " + bookings.size());
        HashMap<String, Integer> instrumentCount = new HashMap<>();
        HashMap<String, Integer> resourceCount = new HashMap<>();
        for (Booking booking : bookings) {
            String instrument = booking.getResource().getName();
            String resource = booking.getResource().getName();
            instrumentCount.put(instrument, instrumentCount.getOrDefault(instrument, 0) + 1);
            resourceCount.put(resource, resourceCount.getOrDefault(resource, 0) + 1);
        }
        System.out.println("Bookings by Instrument:");
        for (Map.Entry<String, Integer> entry : instrumentCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + (entry.getValue()/bookings.size())*100 + " bookings");
        }

    }
}

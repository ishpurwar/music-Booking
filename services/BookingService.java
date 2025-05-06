package services;

import entity.*;
import repository.BookingRepository;

import java.util.List;

public class BookingService {
    private BookingRepository repo;

    public BookingService(BookingRepository repo) {
        this.repo = repo;
    }

    public boolean isOverlapping(Resource resource, DateTimeRange newRange) {
        for (Booking b : repo.getAllBookings()) {
            if (b.getResource().getId() == resource.getId()) {
                if (b.getTimeRange().overlaps(newRange)) return true;
            }
        }
        return false;
    }

    public boolean book(User user, Resource resource, DateTimeRange range) {
        if (isOverlapping(resource, range)) return false;
        double cost = Calculator.calculateCost(resource, range);
        Booking booking = new Booking(user, resource, range, cost);
        repo.addBooking(booking);
        return true;
    }

    public List<Booking> getAllBookings() {
        return repo.getAllBookings();
    }
}

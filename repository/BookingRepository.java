package repository;

import entity.Booking;
import entity.DateTimeRange;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
     public List<Booking> getBookingsInRange(DateTimeRange range) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getTimeRange().overlaps(range)) {
                result.add(booking);
            }
        }
        return result;
    }
    
    public List<Booking> getBookingsForResource(int resourceId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getResource().getId() == resourceId) {
                result.add(booking);
            }
        }
        return result;
    }
}

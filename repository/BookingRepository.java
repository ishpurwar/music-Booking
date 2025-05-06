package repository;

import entity.Booking;

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
}

package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeRange {
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public DateTimeRange(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public boolean overlaps(DateTimeRange other) {
        return (start.isBefore(other.end) && end.isAfter(other.start));
    }

    public long getDurationInHours() {
        return java.time.Duration.between(start, end).toHours();
    }

    @Override
    public String toString() {
        return start.format(formatter) + " to " + end.format(formatter);
    }

    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
}
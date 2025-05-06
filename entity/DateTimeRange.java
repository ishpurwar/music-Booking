package entity;

import java.time.LocalDateTime;

public class DateTimeRange {
    private LocalDateTime start;
    private LocalDateTime end;

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
        return start + " to " + end;
    }

    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
}

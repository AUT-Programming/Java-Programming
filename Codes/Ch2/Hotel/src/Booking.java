package Hotel.src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents a reservation for a particular guest.
 */
public class Booking {
    private String bookingId;
    private Guest guest;
    private List<Room> bookedRooms;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private BookingStatus status;

    public Booking(String bookingId, Guest guest, List<Room> rooms,
                   LocalDateTime start, LocalDateTime end, BookingStatus status) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.bookedRooms = rooms;
        this.startDateTime = start;
        this.endDateTime = end;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void confirm() {
        this.status = BookingStatus.CONFIRMED;
        for (Room room : bookedRooms) {
            room.setOccupied(true);
        }
    }

    public void cancel() {
        this.status = BookingStatus.CANCELED;
        for (Room room : bookedRooms) {
            room.setOccupied(false);
        }
    }

    public void complete() {
        this.status = BookingStatus.COMPLETED;
        for (Room room : bookedRooms) {
            room.setOccupied(false);
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Booking [ID=" + bookingId + ", Guest=" + guest.getName() + ", Status=" + status + "]";
    }
}

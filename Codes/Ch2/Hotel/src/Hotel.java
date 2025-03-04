package Hotel.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hotel that manages rooms and bookings.
 */
public class Hotel {
    private String hotelName;
    private String location;
    private List<Room> rooms;
    private List<Booking> bookings;

    public Hotel(String hotelName, String location) {
        this.hotelName = hotelName;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    @Override
    public String toString() {
        return "Hotel{name='" + hotelName + "', location='" + location
                + "', totalRooms=" + rooms.size() + ", totalBookings=" + bookings.size() + "}";
    }
}

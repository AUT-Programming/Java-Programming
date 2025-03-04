package Hotel.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class demonstrating the hotel reservation system.
 * This class initializes a hotel, adds rooms, books guests, and prints details.
 *
 * @author ApAssistants
 * @version 1.0
 * @since 2025-02-28
 */
public class HotelApp {
    public static void main(String[] args) {
        // Create a hotel
        Hotel hotel = new Hotel("Grand Plaza", "Metropolis");

        // Add rooms
        hotel.addRoom(new Room(101, RoomType.SINGLE, 79.99));
        hotel.addRoom(new Room(202, RoomType.DOUBLE, 129.99));
        hotel.addRoom(new Room(303, RoomType.SUITE, 299.99));

        // Create guests
        Guest guest1 = new Guest("Alice Wonderland", "alice@example.com");

        // Create booking
        List<Room> roomsForBooking = new ArrayList<>();
        roomsForBooking.add(hotel.getRooms().get(0));

        Booking booking = new Booking(
                HotelUtil.generateRandomBookingId("BKG"),
                guest1,
                roomsForBooking,
                HotelUtil.getRandomDateTime(),
                HotelUtil.getRandomDateTime().plusDays(2),
                BookingStatus.PENDING
        );

        booking.confirm();
        hotel.addBooking(booking);

        // Print hotel details and booking status
        System.out.println(hotel);
        System.out.println(booking);
    }
}

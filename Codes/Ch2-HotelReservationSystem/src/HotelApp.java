import java.util.ArrayList;
import java.util.List;

/**
 * Main class demonstrating the hotel reservation system.
 */
public class HotelApp {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Grand Plaza", "Metropolis");

        hotel.addRoom(new Room(101, RoomType.SINGLE, 79.99));
        hotel.addRoom(new Room(202, RoomType.DOUBLE, 129.99));
        hotel.addRoom(new Room(303, RoomType.SUITE, 299.99));

        Guest guest1 = new Guest("Alice Wonderland", "alice@example.com");

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

        System.out.println(hotel);
        System.out.println(booking);
    }
}

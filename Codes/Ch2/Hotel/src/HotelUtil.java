package Hotel.src;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Utility class for common hotel operations.
 */
public class HotelUtil {
    public static String generateRandomBookingId(String prefix) {
        Random rand = new Random();
        int uniqueNumber = rand.nextInt(99999) + 10000;
        return prefix + "-" + uniqueNumber;
    }

    public static LocalDateTime getRandomDateTime() {
        Random rand = new Random();
        LocalDateTime base = LocalDateTime.of(2025, 1, 1, 0, 0);
        return base.plusDays(rand.nextInt(366)).plusHours(rand.nextInt(24));
    }
}

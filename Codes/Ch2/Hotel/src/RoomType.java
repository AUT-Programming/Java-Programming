package Hotel.src;

/**
 * The RoomType enum represents different categories of rooms in a hotel.
 * Each type has a maximum occupancy and a recommended price.
 *
 * @author ApAssistants
 * @version 1.0
 * @since 2025-02-28
 */
public enum RoomType {
    SINGLE(1, 80.0),
    DOUBLE(2, 120.0),
    SUITE(4, 300.0);

    private final int maxOccupancy;
    private final double recommendedPrice;

    /**
     * Constructs a RoomType with a given occupancy and recommended price.
     *
     * @param maxOccupancy   Maximum number of occupants for the room type.
     * @param recommendedPrice Suggested price per night.
     */
    RoomType(int maxOccupancy, double recommendedPrice) {
        this.maxOccupancy = maxOccupancy;
        this.recommendedPrice = recommendedPrice;
    }

    /**
     * Gets the maximum occupancy allowed for this room type.
     *
     * @return Maximum number of occupants.
     */
    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    /**
     * Gets the recommended nightly price for this room type.
     *
     * @return The recommended price.
     */
    public double getRecommendedPrice() {
        return recommendedPrice;
    }

    /**
     * Provides a string representation of the RoomType.
     *
     * @return A formatted string with room type details.
     */
    @Override
    public String toString() {
        return name() + " (Max " + maxOccupancy + " occupants, Recommended $" + recommendedPrice + ")";
    }
}

/**
 * The RoomType enum represents different categories of rooms in a hotel.
 */
public enum RoomType {
    SINGLE(1, 80.0),
    DOUBLE(2, 120.0),
    SUITE(4, 300.0);

    private final int maxOccupancy;
    private final double recommendedPrice;

    RoomType(int maxOccupancy, double recommendedPrice) {
        this.maxOccupancy = maxOccupancy;
        this.recommendedPrice = recommendedPrice;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public double getRecommendedPrice() {
        return recommendedPrice;
    }

    @Override
    public String toString() {
        return name() + " (Max " + maxOccupancy + " occupants, Recommended $" + recommendedPrice + ")";
    }
}

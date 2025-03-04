package Hotel.src;

/**
 * Represents a room in the hotel.
 * Each room has a room number, type, price, and occupancy status.
 *
 * @see RoomType
 */
public class Room {
    private final int roomNumber;
    private final RoomType roomType;
    private double currentPrice;
    private boolean occupied;

    /**
     * Constructs a new room.
     *
     * @param roomNumber   The unique room number.
     * @param roomType     The type of the room (SINGLE, DOUBLE, SUITE).
     * @param currentPrice The nightly price for the room.
     */
    public Room(int roomNumber, RoomType roomType, double currentPrice) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.currentPrice = currentPrice;
        this.occupied = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double newPrice) {
        this.currentPrice = newPrice;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public String toString() {
        return "Room #" + roomNumber + " [" + roomType + "], price=$" + currentPrice
                + ", occupied=" + occupied;
    }
}

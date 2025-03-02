/**
 * Represents a hotel room.
 */
public class Room {
    private final int roomNumber;
    private final RoomType roomType;
    private double currentPrice;
    private boolean occupied;

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

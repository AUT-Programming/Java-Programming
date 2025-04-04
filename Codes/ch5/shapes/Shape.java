abstract class Shape implements ShapeAreaCalculator {
    String shapeType;

    abstract void displayInfo();

    protected boolean validateDimension(double value) {
        if (value <= 0) {
            System.out.println("Invalid dimension value: " + value + ". Must be positive.");
            return false;
        }
        return true;
    }
}

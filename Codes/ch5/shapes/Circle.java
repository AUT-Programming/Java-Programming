class Circle extends Shape {
    double radius;

    public Circle(double radius) {
        if (validateDimension(radius)) {
            this.radius = radius;
            this.shapeType = "Circle";
        } else {
            this.shapeType = "Invalid Shape";
        }
    }

    @Override
    public double calculateArea() {
        if (this.shapeType.equals("Circle")) {
            return Math.PI * radius * radius;
        }
        return 0;
    }

    @Override
    void displayInfo() {
        if (this.shapeType.equals("Circle")) {
            System.out.println("Shape Type: " + shapeType);
            System.out.println("Radius: " + radius);
        } else {
            System.out.println("Invalid shape, cannot display info.");
        }
    }
}

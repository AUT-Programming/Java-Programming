class Triangle extends Shape {
    double base;
    double height;

    public Triangle(double base, double height) {
        if (validateDimension(base) && validateDimension(height)) {
            this.base = base;
            this.height = height;
            this.shapeType = "Triangle";
        } else {
            this.shapeType = "Invalid Shape";
        }
    }

    @Override
    public double calculateArea() {
        if (this.shapeType.equals("Triangle")) {
            return 0.5 * base * height;
        }
        return 0;
    }

    @Override
    void displayInfo() {
        if (this.shapeType.equals("Triangle")) {
            System.out.println("Shape Type: " + shapeType);
            System.out.println("Base: " + base + ", Height: " + height);
        } else {
            System.out.println("Invalid shape, cannot display info.");
        }
    }
}

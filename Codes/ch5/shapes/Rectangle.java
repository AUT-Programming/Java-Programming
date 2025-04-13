class Rectangle extends Shape {
    double width;
    double height;

    public Rectangle(double width, double height) {
        if (validateDimension(width) && validateDimension(height)) {
            this.width = width;
            this.height = height;
            this.shapeType = "Rectangle";
        } else {
            this.shapeType = "Invalid Shape";
        }
    }

    @Override
    public double calculateArea() {
        if (this.shapeType.equals("Rectangle")) {
            return width * height;
        }
        return 0;
    }

    @Override
    void displayInfo() {
        if (this.shapeType.equals("Rectangle")) {
            System.out.println("Shape Type: " + shapeType);
            System.out.println("Width: " + width + ", Height: " + height);
        } else {
            System.out.println("Invalid shape, cannot display info.");
        }
    }
}

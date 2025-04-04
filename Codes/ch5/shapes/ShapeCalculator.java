public class ShapeCalculator {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        Shape invalidCircle = new Circle(-3);
        Shape rectangle = new Rectangle(4, 6);
        Shape invalidRectangle = new Rectangle(0, 6);
        Shape triangle = new Triangle(4, 7);
        Shape invalidTriangle = new Triangle(4, -7);

        displayShapeInfo(circle);
        displayShapeInfo(invalidCircle);
        displayShapeInfo(rectangle);
        displayShapeInfo(invalidRectangle);
        displayShapeInfo(triangle);
        displayShapeInfo(invalidTriangle);
    }

    static void displayShapeInfo(Shape shape) {
        shape.displayInfo();
        double area = shape.calculateArea();
        if (area != 0) {
            System.out.println("Area: " + area + "\n");
        } else {
            System.out.println("Cannot calculate area due to invalid shape.\n");
        }
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Custom exception
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Generic utility class
class Validator {
    public static <T> void printList(List<T> list) {
        for (T item : list) {
            System.out.println("Data: " + item);
        }
    }
}

public class AgeValidatorApp {
    public static void main(String[] args) {
        String filePath = "ages.txt";
        List<Integer> validAges = new ArrayList<>();

        // File reading with try-with-resources
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                int age = Integer.parseInt(line.trim());

                try {
                    checkAge(age); // Custom exception logic
                    validAges.add(age);
                } catch (InvalidAgeException e) {
                    System.out.println("Invalid age found: " + e.getMessage());
                }
            }

            System.out.println("\n✅ Valid Ages:");
            Validator.printList(validAges); // Using generic method

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.out.println("Make sure that ages.txt is in the project root directory.");
        }
        
    }

    // Method that throws custom exception
    public static void checkAge(int age) throws InvalidAgeException {
        if (age < 18 || age > 120) {
            throw new InvalidAgeException("Age " + age + " is not allowed.");
        }
    }
}

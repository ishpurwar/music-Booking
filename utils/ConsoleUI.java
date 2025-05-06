package utils;

import java.util.Scanner;

public class ConsoleUI {
    private static Scanner scanner = new Scanner(System.in);

    public static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                return value;
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                return value;
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        // Alternative for some systems:
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
    }
    
    public static void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public static void consumeNextLine() {
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Ignore if there's nothing to consume
        }
    }
}
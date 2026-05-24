package com.skytrail.console;

import com.skytrail.enums.*;
import com.skytrail.model.Booking;
import com.skytrail.model.Passenger;
import com.skytrail.model.TripDetails;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive console entry point.
 *
 * <pre>
 *   Compile:  javac -d out $(find src -name "*.java")
 *   Run:      java -cp out com.skytrail.console.Main
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== SkyTrail Travel & Tourism Booking System ===");

        TripDetails    trip       = collectTripDetails(scanner);
        List<Passenger> passengers = collectPassengers(scanner);
        PaymentMethod  payment    = chooseOption(scanner, PaymentMethod.values(), "Choose payment method");

        Booking booking = new Booking(trip, passengers, payment);
        booking.printSummary();

        scanner.close();
    }

    // ── Trip ────────────────────────────────────────────────────────

    private static TripDetails collectTripDetails(Scanner sc) {
        String origin      = readString(sc, "Enter origin city: ");
        String destination = readString(sc, "Enter destination city: ");
        LocalDate date     = readDate(sc, "Enter travel date (YYYY-MM-DD): ");

        TransportMode mode = chooseOption(sc, TransportMode.values(), "Choose transport mode");

        SeatChoice seat;
        if (mode == TransportMode.TRAIN) {
            System.out.println("\nChoose berth position:");
            System.out.println("  1. Lower  2. Middle  3. Upper");
            System.out.print("Enter choice (1-3): ");
            int pos = readIntInRange(sc, 1, 3);
            seat = SeatChoice.values()[pos - 1];
            int coach = readPositiveInt(sc, "Enter coach number (1-20): ");
            System.out.println("Selected: " + seat + " berth in coach " + coach);
        } else {
            seat = chooseOption(sc, SeatChoice.values(), "Choose seat preference");
        }

        FoodPreference food = chooseOption(sc, FoodPreference.values(), "Choose food preference");
        return new TripDetails(origin, destination, date, mode, seat, food);
    }

    // ── Passengers ──────────────────────────────────────────────────

    private static List<Passenger> collectPassengers(Scanner sc) {
        int count = readPositiveInt(sc, "Number of passengers: ");
        List<Passenger> list = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            System.out.println("\n--- Passenger " + i + " ---");
            String name    = readString(sc, "Name: ");
            String contact = readString(sc, "Contact number: ");
            String govId   = readString(sc, "Government ID: ");
            int age        = readPositiveInt(sc, "Age: ");
            Gender gender  = chooseOption(sc, Gender.values(), "Gender");
            list.add(new Passenger(name, contact, govId, age, gender));
        }
        return list;
    }

    // ── Input helpers ────────────────────────────────────────────────

    private static String readString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("  Cannot be empty. Try again.");
        }
    }

    private static int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v > 0) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("  Enter a positive integer.");
        }
    }

    private static int readIntInRange(Scanner sc, int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("  Enter a number between " + min + " and " + max + ".");
        }
    }

    private static LocalDate readDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                LocalDate d = LocalDate.parse(sc.nextLine().trim());
                if (!d.isBefore(LocalDate.now())) return d;
                System.out.println("  Date cannot be in the past.");
            } catch (DateTimeParseException e) {
                System.out.println("  Use format YYYY-MM-DD.");
            }
        }
    }

    private static <T extends Enum<T>> T chooseOption(Scanner sc, T[] options, String title) {
        while (true) {
            System.out.println("\n" + title + ":");
            for (int i = 0; i < options.length; i++)
                System.out.println("  " + (i + 1) + ". " + options[i]);
            System.out.print("Choice: ");
            try {
                int c = Integer.parseInt(sc.nextLine().trim());
                if (c >= 1 && c <= options.length) return options[c - 1];
            } catch (NumberFormatException ignored) {}
            System.out.println("  Invalid choice.");
        }
    }
}

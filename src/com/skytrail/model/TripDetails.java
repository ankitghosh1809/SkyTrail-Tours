package com.skytrail.model;

import com.skytrail.enums.FoodPreference;
import com.skytrail.enums.SeatChoice;
import com.skytrail.enums.TransportMode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Immutable value object representing trip-level details
 * shared by all passengers in one booking.
 */
public class TripDetails {

    private final String origin;
    private final String destination;
    private final LocalDate travelDate;
    private final int days;
    private final TransportMode transportMode;
    private final SeatChoice seatChoice;
    private final FoodPreference foodPreference;
    private final int coachNumber;

    /** Constructor used by the console app (Main.java). */
    public TripDetails(String origin, String destination, LocalDate travelDate,
                       TransportMode transportMode, SeatChoice seatChoice,
                       FoodPreference foodPreference) {
        this(origin, destination, travelDate, 1, transportMode, seatChoice, foodPreference, 0);
    }

    /** Constructor used by the web API (BookingHandler). Accepts ISO date string. */
    public TripDetails(String origin, String destination, String dateStr, int days,
                       TransportMode transportMode, SeatChoice seatChoice,
                       FoodPreference foodPreference, int coachNumber) {
        this(origin, destination, parseDate(dateStr), days,
                transportMode, seatChoice, foodPreference, coachNumber);
    }

    /** Full canonical constructor. */
    public TripDetails(String origin, String destination, LocalDate travelDate, int days,
                       TransportMode transportMode, SeatChoice seatChoice,
                       FoodPreference foodPreference, int coachNumber) {
        this.origin        = origin;
        this.destination   = destination;
        this.travelDate    = travelDate;
        this.days          = days;
        this.transportMode = transportMode;
        this.seatChoice    = seatChoice;
        this.foodPreference = foodPreference;
        this.coachNumber   = coachNumber;
    }

    private static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return LocalDate.now();
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }

    public String getOrigin()                  { return origin; }
    public String getDestination()             { return destination; }
    public LocalDate getTravelDate()           { return travelDate; }
    public int getDays()                       { return days; }
    public TransportMode getTransportMode()    { return transportMode; }
    public SeatChoice getSeatChoice()          { return seatChoice; }
    public FoodPreference getFoodPreference()  { return foodPreference; }
    public int getCoachNumber()                { return coachNumber; }
}

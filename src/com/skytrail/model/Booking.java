package com.skytrail.model;

import com.skytrail.enums.PaymentMethod;
import com.skytrail.service.TransportService;
import com.skytrail.service.TransportServiceFactory;

import java.util.List;
import java.util.UUID;

/**
 * Represents a completed booking.
 * Computes fare via the appropriate TransportService and assigns a unique booking ID.
 */
public class Booking {

    private final String bookingId;
    private final TripDetails tripDetails;
    private final List<Passenger> passengers;
    private final PaymentMethod paymentMethod;
    private final TransportService transportService;

    public Booking(TripDetails tripDetails, List<Passenger> passengers, PaymentMethod paymentMethod) {
        this.bookingId        = "SKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.tripDetails      = tripDetails;
        this.passengers       = passengers;
        this.paymentMethod    = paymentMethod;
        this.transportService = TransportServiceFactory.createService(tripDetails.getTransportMode());
    }

    public String getBookingId() { return bookingId; }

    public double getTotalFare() {
        return transportService.calculateFare(tripDetails, passengers.size());
    }

    /** Prints a formatted booking summary to stdout (used by the console app). */
    public void printSummary() {
        double totalFare = getTotalFare();
        System.out.println("\n====== BOOKING CONFIRMATION ======");
        System.out.println("Booking ID : " + bookingId);
        System.out.println("From       : " + tripDetails.getOrigin());
        System.out.println("To         : " + tripDetails.getDestination());
        System.out.println("Date       : " + tripDetails.getTravelDate());
        System.out.println("Transport  : " + tripDetails.getTransportMode());
        System.out.println("Service    : " + transportService.getServiceName());
        System.out.println("Seat       : " + tripDetails.getSeatChoice());
        System.out.println("Food       : " + tripDetails.getFoodPreference());
        System.out.println("Payment    : " + paymentMethod);
        System.out.println("Passengers : " + passengers.size());
        System.out.printf( "Total Fare : %.2f%n", totalFare);
        System.out.println("Policy     : " + transportService.getPolicyNote(tripDetails));
        for (int i = 0; i < passengers.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + passengers.get(i));
        }
        System.out.println("==================================");
    }
}

package com.skytrail.service;

import com.skytrail.model.TripDetails;

public class FlightTransportService extends BaseTransportService {

    private static final double AIRPORT_FEE = 300.0;

    public FlightTransportService() {
        super("SkyConnect Flights", 2200.0);
    }

    @Override
    public double calculateFare(TripDetails trip, int passengerCount) {
        double perPax = getBaseFarePerPassenger() * getSeatMultiplier(trip.getSeatChoice())
                + getFoodSurchargePerPassenger(trip.getFoodPreference())
                + AIRPORT_FEE;
        return perPax * passengerCount;
    }

    @Override
    public String getPolicyNote(TripDetails trip) {
        return "Web check-in opens 48 hours before departure.";
    }
}

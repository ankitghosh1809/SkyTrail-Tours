package com.skytrail.service;

import com.skytrail.model.TripDetails;

public class BusTransportService extends BaseTransportService {

    public BusTransportService() {
        super("Road Comfort Bus Service", 450.0);
    }

    @Override
    public double calculateFare(TripDetails trip, int passengerCount) {
        double perPax = getBaseFarePerPassenger()
                + getFoodSurchargePerPassenger(trip.getFoodPreference());
        return perPax * passengerCount;
    }

    @Override
    public String getPolicyNote(TripDetails trip) {
        return "Bus boarding closes 10 minutes before departure.";
    }
}

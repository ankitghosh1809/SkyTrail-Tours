package com.skytrail.service;

import com.skytrail.model.TripDetails;

public class TrainTransportService extends BaseTransportService {

    public TrainTransportService() {
        super("Express Rail Service", 650.0);
    }

    @Override
    public double calculateFare(TripDetails trip, int passengerCount) {
        double perPax = getBaseFarePerPassenger() * getSeatMultiplier(trip.getSeatChoice())
                + getFoodSurchargePerPassenger(trip.getFoodPreference());
        return perPax * passengerCount;
    }

    @Override
    public String getPolicyNote(TripDetails trip) {
        return "Platform changes can happen up to 20 minutes before departure.";
    }
}

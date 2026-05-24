package com.skytrail.service;

import com.skytrail.enums.FoodPreference;
import com.skytrail.enums.SeatChoice;
import com.skytrail.model.TripDetails;

/**
 * Provides shared fare-calculation helpers for all transport services.
 */
public abstract class BaseTransportService implements TransportService {

    private final String serviceName;
    private final double baseFarePerPassenger;

    protected BaseTransportService(String serviceName, double baseFarePerPassenger) {
        this.serviceName          = serviceName;
        this.baseFarePerPassenger = baseFarePerPassenger;
    }

    @Override
    public String getServiceName() { return serviceName; }

    protected double getBaseFarePerPassenger() { return baseFarePerPassenger; }

    protected double getSeatMultiplier(SeatChoice seat) {
        switch (seat) {
            case WINDOW: return 1.10;
            case AISLE:  return 1.05;
            default:     return 1.00;
        }
    }

    protected double getFoodSurchargePerPassenger(FoodPreference food) {
        return food == FoodPreference.NON_VEG ? 120.0 : 80.0;
    }
}

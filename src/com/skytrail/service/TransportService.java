package com.skytrail.service;

import com.skytrail.model.TripDetails;

/**
 * Contract for all transport service implementations.
 */
public interface TransportService {
    String getServiceName();
    double calculateFare(TripDetails tripDetails, int passengerCount);
    String getPolicyNote(TripDetails tripDetails);
}

package com.skytrail.service;

import com.skytrail.enums.TransportMode;

/**
 * Factory that returns the correct TransportService implementation
 * for a given TransportMode.
 */
public final class TransportServiceFactory {

    private TransportServiceFactory() { /* utility class */ }

    public static TransportService createService(TransportMode mode) {
        switch (mode) {
            case BUS:    return new BusTransportService();
            case TRAIN:  return new TrainTransportService();
            case FLIGHT: return new FlightTransportService();
            default:     throw new IllegalArgumentException("Unsupported mode: " + mode);
        }
    }
}

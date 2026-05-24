package com.skytrail.server;

import com.skytrail.enums.*;
import com.skytrail.model.Booking;
import com.skytrail.model.Passenger;
import com.skytrail.model.TripDetails;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles POST /api/book
 *
 * Parses a JSON body sent by the frontend, wires the fields into
 * the existing OOP booking classes, and returns a JSON confirmation.
 */
public class BookingHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // CORS — allow browser requests during local development
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin",  "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            String body = new String(
                exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

            String name         = field(body, "name");
            String email        = field(body, "email");
            String origin       = field(body, "origin");
            String destination  = field(body, "destination");
            String date         = field(body, "date");
            String mode         = field(body, "mode");
            String seat         = field(body, "seat");
            String food         = field(body, "food");
            String payment      = field(body, "payment");
            String daysStr      = field(body, "days");
            String extraPax     = field(body, "passengers");

            if (name.isEmpty() || destination.isEmpty() || origin.isEmpty()
                    || date.isEmpty() || mode.isEmpty()) {
                sendJson(exchange, 400, "{\"error\":\"Missing required fields\"}");
                return;
            }

            int days = 1;
            try { days = Math.max(1, Integer.parseInt(daysStr)); } catch (Exception ignored) {}

            TransportMode transportMode;
            try {
                transportMode = TransportMode.valueOf(mode.toUpperCase());
            } catch (IllegalArgumentException e) {
                sendJson(exchange, 400, "{\"error\":\"Invalid transport mode: " + mode + "\"}");
                return;
            }

            SeatChoice seatChoice = SeatChoice.WINDOW;
            try { if (!seat.isEmpty()) seatChoice = SeatChoice.valueOf(seat.toUpperCase()); }
            catch (IllegalArgumentException ignored) {}

            FoodPreference foodPref = FoodPreference.NONE;
            try { if (!food.isEmpty()) foodPref = FoodPreference.valueOf(food.toUpperCase()); }
            catch (IllegalArgumentException ignored) {}

            PaymentMethod paymentMethod = PaymentMethod.UPI;
            try { if (!payment.isEmpty()) paymentMethod = PaymentMethod.valueOf(payment.toUpperCase()); }
            catch (IllegalArgumentException ignored) {}

            int coachNumber = (transportMode == TransportMode.TRAIN) ? 1 : 0;

            TripDetails trip = new TripDetails(
                origin, destination, date, days,
                transportMode, seatChoice, foodPref, coachNumber
            );

            // Build passenger list — primary passenger first, extras from the field
            List<Passenger> passengers = new ArrayList<>();
            String[] names = extraPax.isEmpty() ? new String[]{name} : extraPax.split(",");
            for (int i = 0; i < names.length; i++) {
                String pName = names[i].trim();
                if (pName.isEmpty()) continue;
                String contact = (i == 0) ? email : "N/A";
                String govId   = "ID-" + String.format("%04d", (int)(Math.random() * 9000) + 1000);
                passengers.add(new Passenger(pName, contact, govId, 25, Gender.MALE));
            }

            if (passengers.isEmpty()) {
                sendJson(exchange, 400, "{\"error\":\"At least one passenger required\"}");
                return;
            }

            Booking booking = new Booking(trip, passengers, paymentMethod);
            sendJson(exchange, 200,
                buildResponse(booking.getBookingId(), name, email, origin, destination,
                    date, days, mode, seat, food, payment, passengers, booking.getTotalFare()));

        } catch (Exception e) {
            e.printStackTrace();
            sendJson(exchange, 500, "{\"error\":\"Server error: " + escape(e.getMessage()) + "\"}");
        }
    }

    // ── JSON helpers ────────────────────────────────────────────────

    /** Minimal string-field extractor — avoids pulling in a JSON library. */
    private String field(String json, String key) {
        String token = "\"" + key + "\"";
        int i = json.indexOf(token);
        if (i < 0) return "";
        int colon = json.indexOf(":", i + token.length());
        if (colon < 0) return "";
        int start = json.indexOf("\"", colon + 1);
        if (start < 0) return "";
        int end = json.indexOf("\"", start + 1);
        if (end < 0) return "";
        return json.substring(start + 1, end);
    }

    private String buildResponse(String bookingId, String name, String email,
            String origin, String destination, String date, int days,
            String mode, String seat, String food, String payment,
            List<Passenger> passengers, double totalFare) {

        StringBuilder paxArray = new StringBuilder("[");
        for (int i = 0; i < passengers.size(); i++) {
            if (i > 0) paxArray.append(",");
            paxArray.append("\"").append(escape(passengers.get(i).getName())).append("\"");
        }
        paxArray.append("]");

        return "{"
            + "\"bookingId\":\""  + escape(bookingId)   + "\","
            + "\"name\":\""       + escape(name)         + "\","
            + "\"email\":\""      + escape(email)        + "\","
            + "\"origin\":\""     + escape(origin)       + "\","
            + "\"destination\":\"" + escape(destination) + "\","
            + "\"date\":\""       + escape(date)         + "\","
            + "\"days\":"         + days                 + ","
            + "\"mode\":\""       + escape(mode)         + "\","
            + "\"seat\":\""       + escape(seat)         + "\","
            + "\"food\":\""       + escape(food)         + "\","
            + "\"payment\":\""    + escape(payment)      + "\","
            + "\"passengers\":"   + paxArray             + ","
            + "\"totalFare\":"    + String.format("%.2f", totalFare)
            + "}";
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void sendJson(HttpExchange ex, int code, String json) throws IOException {
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        ex.sendResponseHeaders(code, body.length);
        try (OutputStream out = ex.getResponseBody()) { out.write(body); }
    }
}

# SkyTrail Tours

🌐 **Live Demo:** [https://skytrail-tours.onrender.com](https://skytrail-tours.onrender.com)

A travel booking web app I built as part of my OOP coursework at NMIMS. The idea was to take the console-based booking system we made in class and turn it into something that actually runs in a browser.

The backend is pure Java with no frameworks — just the standard library's built-in HTTP server. Frontend is plain HTML, CSS, and JS.

---

## What it does

- Browse destinations across India, filter by region and budget
- Generate a rough day-by-day itinerary based on your travel style
- Book a trip — choose transport, seat, food preference, payment method
- The booking goes to the Java backend which calculates fare and returns a confirmation with a unique booking ID

---

## Project structure

```
SkyTrail/
├── src/com/skytrail/
│   ├── enums/          → TransportMode, SeatChoice, FoodPreference, PaymentMethod, Gender
│   ├── model/          → TripDetails, Passenger, Booking
│   ├── service/        → TransportService interface, Bus/Train/Flight implementations
│   ├── server/         → HTTP server, static file handler, booking API handler
│   └── console/        → original terminal-based booking app
├── web/                → index.html, style.css, script.js
└── docs/api.md         → API request/response format
```

---

## Running it

You need JDK 11 or above. Run everything from inside the project folder.

**Compile:**
```
javac -d out $(find src -name "*.java")
```

**Start the web server:**
```
java -cp out com.skytrail.server.Server
```

Then open `http://localhost:8080`

**Or run the original console version:**
```
java -cp out com.skytrail.console.Main
```

---

## How the booking works

The frontend sends a POST request to `/api/book` with the trip details as JSON. The Java backend picks up the request, creates the model objects, runs fare calculation through the appropriate transport service (bus/train/flight), and sends back a confirmation JSON with a booking ID.

The fare logic uses inheritance — `BaseTransportService` has the shared calculation helpers, and each transport type overrides `calculateFare()` with its own pricing rules.

---

## Tech

- Java 11, `com.sun.net.httpserver` (no Spring, no Maven)
- HTML5 + CSS3 + vanilla JS
- No database — bookings are processed in memory

---

## OOP concepts used

This was mainly a way to apply what we covered in class:

- **Encapsulation** — all model fields are private
- **Inheritance** — `BaseTransportService` → `BusTransportService`, `TrainTransportService`, `FlightTransportService`
- **Polymorphism** — `TransportService` interface, factory pattern
- **Abstraction** — the handler doesn't know which service it's using, just calls `calculateFare()`
- **Enums** — type-safe options for transport mode, seat choice, food, payment, gender

---

## API

See [`docs/api.md`](docs/api.md) for the full request/response format.

Quick example:

```json
POST /api/book

{
  "name": "Ankit Sharma",
  "origin": "Mumbai",
  "destination": "Jaipur, Rajasthan",
  "date": "2025-12-15",
  "days": "4",
  "mode": "TRAIN",
  "seat": "WINDOW",
  "food": "VEG",
  "payment": "UPI"
}
```

Response includes a booking ID like `SKT-3F9A12BC` and the calculated total fare.

---

Made this for the OOP project submission. Figured it would be more interesting to build a full working app instead of just a console program.

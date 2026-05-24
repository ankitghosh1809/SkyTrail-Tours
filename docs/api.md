# SkyTrail Tours — API Reference

## Base URL
```
http://localhost:8080
```

---

## POST /api/book

Creates a new booking and returns a confirmation with a unique Booking ID and total fare.

### Request

**Content-Type:** `application/json`

| Field        | Type   | Required | Description                                   |
|--------------|--------|----------|-----------------------------------------------|
| `name`       | string | ✅       | Primary passenger's full name                 |
| `email`      | string | ✅       | Primary passenger's email address             |
| `origin`     | string | ✅       | Departure city                                |
| `destination`| string | ✅       | Destination name (must match frontend list)   |
| `date`       | string | ✅       | Travel date in `YYYY-MM-DD` format            |
| `days`       | string | ✅       | Number of days (positive integer as string)   |
| `mode`       | string | ✅       | Transport mode: `BUS`, `TRAIN`, or `FLIGHT`   |
| `seat`       | string | ❌       | `WINDOW`, `MIDDLE`, or `AISLE` (default: `WINDOW`) |
| `food`       | string | ❌       | `VEG`, `NON_VEG`, `VEGAN`, or `NONE`         |
| `payment`    | string | ❌       | `UPI`, `CARD`, `NET_BANKING`, or `CASH`       |
| `passengers` | string | ❌       | Comma-separated additional passenger names    |
| `message`    | string | ❌       | Optional note (stored, not yet processed)     |

**Example request body:**
```json
{
  "name": "Ankit Sharma",
  "email": "ankit@example.com",
  "origin": "Mumbai",
  "destination": "Jaipur, Rajasthan",
  "date": "2025-12-15",
  "days": "4",
  "mode": "TRAIN",
  "seat": "WINDOW",
  "food": "VEG",
  "payment": "UPI",
  "passengers": "Priya Sharma"
}
```

### Response `200 OK`

```json
{
  "bookingId": "SKT-3F9A12BC",
  "name": "Ankit Sharma",
  "email": "ankit@example.com",
  "origin": "Mumbai",
  "destination": "Jaipur, Rajasthan",
  "date": "2025-12-15",
  "days": 4,
  "mode": "TRAIN",
  "seat": "WINDOW",
  "food": "VEG",
  "payment": "UPI",
  "passengers": ["Ankit Sharma", "Priya Sharma"],
  "totalFare": 1516.00
}
```

### Error Responses

| Status | Meaning                                 |
|--------|-----------------------------------------|
| `400`  | Missing required fields or invalid enum |
| `405`  | Wrong HTTP method                       |
| `500`  | Unexpected server error                 |

---

## Fare Calculation

| Mode     | Base/pax | Seat multiplier        | Food surcharge/pax | Extra     |
|----------|----------|------------------------|--------------------|-----------|
| Bus      | ₹450     | None                   | ₹80 / ₹120        | —         |
| Train    | ₹650     | Window 1.10, Aisle 1.05 | ₹80 / ₹120       | —         |
| Flight   | ₹2200    | Window 1.10, Aisle 1.05 | ₹80 / ₹120       | ₹300 airport fee |

Food surcharge: ₹120 for NON_VEG, ₹80 for all others.

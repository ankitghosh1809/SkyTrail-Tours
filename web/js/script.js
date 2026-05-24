/* ==========================================
   SkyTrail Tours - JS (Integrated with Java backend)
   India Edition
   ========================================== */

// 1) Destination data (Indian cities only)
const destinations = [
  {
    id: 1, name: "Jaipur, Rajasthan", region: "north", budget: "mid", price: 18999,
    rating: 4.8, popularity: 96, bestTime: "October to March",
    famousFor: "Forts, palaces, and local markets",
    image: "https://images.unsplash.com/photo-1477587458883-47145ed94245?auto=format&fit=crop&w=1000&q=80",
    description: "The Pink City with rich heritage and colorful culture.",
    dayPlans: ["Amber Fort and light show in the evening.", "City Palace, Jantar Mantar, and Hawa Mahal.",
               "Local bazaar shopping and cultural dinner show.", "Nahargarh Fort sunrise and heritage walk."]
  },
  {
    id: 2, name: "Manali, Himachal Pradesh", region: "north", budget: "budget", price: 14999,
    rating: 4.7, popularity: 95, bestTime: "March to June, December to February",
    famousFor: "Snow points, cafes, and adventure sports",
    image: "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?auto=format&fit=crop&w=1000&q=80",
    description: "A mountain destination for nature and adventure lovers.",
    dayPlans: ["Hadimba Temple visit and old Manali cafes.", "Solang Valley activities and ropeway.",
               "Atal Tunnel drive and Sissu valley views.", "River-side walk and local market evening."]
  },
  {
    id: 3, name: "Kochi, Kerala", region: "south", budget: "mid", price: 20999,
    rating: 4.6, popularity: 91, bestTime: "September to March",
    famousFor: "Backwaters, seafood, and colonial streets",
    image: "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?auto=format&fit=crop&w=1000&q=80",
    description: "A coastal city that blends history with relaxing waterscapes.",
    dayPlans: ["Fort Kochi walk and Chinese fishing nets.", "Mattancherry Palace and Jew Town visit.",
               "Backwater cruise near Alleppey.", "Kathakali performance and local cuisine tour."]
  },
  {
    id: 4, name: "Darjeeling, West Bengal", region: "east", budget: "budget", price: 15999,
    rating: 4.7, popularity: 89, bestTime: "April to June, October to December",
    famousFor: "Tea gardens and mountain train",
    image: "https://images.pexels.com/photos/1658967/pexels-photo-1658967.jpeg?auto=compress&cs=tinysrgb&w=1200",
    description: "Cool weather, tea estates, and Himalayan views.",
    dayPlans: ["Tiger Hill sunrise and Batasia Loop.", "Darjeeling Himalayan Railway ride.",
               "Tea estate tour and tasting session.", "Monastery visit and local shopping."]
  },
  {
    id: 5, name: "Udaipur, Rajasthan", region: "west", budget: "luxury", price: 23999,
    rating: 4.9, popularity: 93, bestTime: "October to March",
    famousFor: "Lakes, palaces, and romantic boat rides",
    image: "https://images.pexels.com/photos/3581916/pexels-photo-3581916.jpeg?auto=compress&cs=tinysrgb&w=1200",
    description: "The City of Lakes with premium heritage stays.",
    dayPlans: ["City Palace and Jagdish Temple tour.", "Lake Pichola boat ride at sunset.",
               "Sajjangarh fort and cultural evening.", "Shopping for handicrafts and local art."]
  },
  {
    id: 6, name: "Gangtok, Sikkim", region: "northeast", budget: "mid", price: 21999,
    rating: 4.8, popularity: 90, bestTime: "March to June, September to November",
    famousFor: "Monasteries, mountain passes, and clean streets",
    image: "https://images.unsplash.com/photo-1595815771614-ade9d652a65d?auto=format&fit=crop&w=1000&q=80",
    description: "A peaceful hill city with stunning Himalayan landscapes.",
    dayPlans: ["MG Marg walk and local food tasting.", "Tsomgo Lake day trip.",
               "Rumtek Monastery and viewpoints.", "Nathula pass (permit based) and return."]
  }
];

// 2) Traveler stories
const stories = [
  { quote: "Our Jaipur trip was perfectly managed, from hotel check-in to city tours.", author: "Aditi and Mohit, Jaipur Plan" },
  { quote: "The day-by-day Manali schedule was very easy to follow.", author: "Raghav, Manali Explorer" },
  { quote: "I liked that costs were transparent and everything stayed within budget.", author: "Pooja, Kochi Trip" }
];

// 3) DOM elements
const searchInput        = document.getElementById("searchInput");
const regionFilter       = document.getElementById("regionFilter");
const budgetFilter       = document.getElementById("budgetFilter");
const sortFilter         = document.getElementById("sortFilter");
const resetFiltersButton = document.getElementById("resetFilters");
const destinationsGrid   = document.getElementById("destinationsGrid");
const resultText         = document.getElementById("resultText");

const daysInput          = document.getElementById("daysInput");
const daysValue          = document.getElementById("daysValue");
const plannerForm        = document.getElementById("plannerForm");
const styleInput         = document.getElementById("styleInput");
const interestInput      = document.getElementById("interestInput");
const itineraryOutput    = document.getElementById("itineraryOutput");

const menuToggle         = document.getElementById("menuToggle");
const navLinks           = document.getElementById("navLinks");

const storyQuote         = document.getElementById("storyQuote");
const storyAuthor        = document.getElementById("storyAuthor");
const prevStoryButton    = document.getElementById("prevStory");
const nextStoryButton    = document.getElementById("nextStory");

const bookingModal          = document.getElementById("bookingModal");
const openBookingModalButton= document.getElementById("openBookingModal");
const closeBookingModalButton=document.getElementById("closeBookingModal");
const closeConfirmBtn       = document.getElementById("closeConfirmBtn");
const bookingForm           = document.getElementById("bookingForm");
const destinationInput      = document.getElementById("destinationInput");
const bookingDaysInput      = document.getElementById("bookingDaysInput");
const bookingDetailsOutput  = document.getElementById("bookingDetailsOutput");
const submitBookingBtn      = document.getElementById("submitBookingBtn");
const bookingConfirmation   = document.getElementById("bookingConfirmation");
const confirmationDetails   = document.getElementById("confirmationDetails");

// New modal fields
const originInput     = document.getElementById("originInput");
const travelDateInput = document.getElementById("travelDateInput");
const modeInput       = document.getElementById("modeInput");
const seatInput       = document.getElementById("seatInput");
const foodInput       = document.getElementById("foodInput");
const paymentInput    = document.getElementById("paymentInput");
const passengersInput = document.getElementById("passengersInput");

let currentStoryIndex = 0;
const fallbackImage = "https://images.pexels.com/photos/3951355/pexels-photo-3951355.jpeg?auto=compress&cs=tinysrgb&w=1200";

function formatINR(price) {
  return `\u20B9${Number(price).toLocaleString("en-IN")}`;
}

function getDestinationByName(name) {
  return destinations.find((item) => item.name === name);
}

// 4) Destination cards
function renderDestinationCards(data) {
  destinationsGrid.innerHTML = "";
  if (data.length === 0) {
    destinationsGrid.innerHTML = "<p>No destinations match your filters.</p>";
    resultText.textContent = "0 destinations found";
    return;
  }
  data.forEach((item) => {
    const card = document.createElement("article");
    card.className = "destination-card";
    card.innerHTML = `
      <img src="${item.image}" alt="${item.name}" loading="lazy" onerror="this.onerror=null;this.src='${fallbackImage}';">
      <div class="card-content">
        <h3>${item.name}</h3>
        <p>${item.description}</p>
        <div class="tags">
          <span class="tag">${item.region}</span>
          <span class="tag">${item.budget}</span>
          <span class="tag">★ ${item.rating}</span>
        </div>
        <div class="meta-row">
          <span class="price">From ${formatINR(item.price)}</span>
          <span>🔥 ${item.popularity}% popular</span>
        </div>
        <button class="btn btn-primary book-now-btn" data-destination="${item.name}">Book This Tour</button>
      </div>`;
    destinationsGrid.appendChild(card);
  });
  resultText.textContent = `${data.length} destination${data.length !== 1 ? "s" : ""} found`;
}

function getFilteredAndSortedDestinations() {
  const searchText     = searchInput.value.trim().toLowerCase();
  const selectedRegion = regionFilter.value;
  const selectedBudget = budgetFilter.value;
  const sortType       = sortFilter.value;

  let result = destinations.filter((item) => {
    const nameMatch   = item.name.toLowerCase().includes(searchText);
    const descMatch   = item.description.toLowerCase().includes(searchText);
    const regionMatch = selectedRegion === "all" || item.region === selectedRegion;
    const budgetMatch = selectedBudget === "all" || item.budget === selectedBudget;
    return (nameMatch || descMatch) && regionMatch && budgetMatch;
  });

  if      (sortType === "low-high") result.sort((a, b) => a.price - b.price);
  else if (sortType === "high-low") result.sort((a, b) => b.price - a.price);
  else if (sortType === "rating")   result.sort((a, b) => b.rating - a.rating);
  else                              result.sort((a, b) => b.popularity - a.popularity);

  return result;
}

function updateDestinations() {
  renderDestinationCards(getFilteredAndSortedDestinations());
}

// 5) Planner
function generateItinerary(style, days, interest) {
  const styleMap = {
    relaxed:   "Start late and keep activities light",
    balanced:  "Mix sightseeing and free time",
    adventure: "Pack your day with active experiences"
  };
  const interestMap = {
    culture:   "visit heritage spots and local museums",
    food:      "explore local street food and restaurants",
    nature:    "cover scenic points and nature trails",
    nightlife: "keep evenings for events and local music"
  };
  const plan = [];
  for (let day = 1; day <= days; day++) {
    plan.push(`Day ${day}: ${styleMap[style]}, and ${interestMap[interest]}.`);
  }
  return plan;
}

function showItinerary(plan) {
  itineraryOutput.innerHTML = `<h3>Your Suggested Day Plan</h3><ul>${plan.map(i => `<li>${i}</li>`).join("")}</ul>`;
}

// 6) Story slider
function renderStory(index) {
  storyQuote.textContent  = `"${stories[index].quote}"`;
  storyAuthor.textContent = stories[index].author;
}
function nextStory() { currentStoryIndex = (currentStoryIndex + 1) % stories.length; renderStory(currentStoryIndex); }
function prevStory() { currentStoryIndex = (currentStoryIndex - 1 + stories.length) % stories.length; renderStory(currentStoryIndex); }

// 7) Booking modal helpers
function populateDestinationDropdown() {
  destinationInput.innerHTML = destinations.map(
    (item) => `<option value="${item.name}">${item.name}</option>`
  ).join("");
}

function getDayWisePlan(destination, days) {
  const result = [];
  for (let day = 1; day <= days; day++) {
    result.push(`Day ${day}: ${destination.dayPlans[(day - 1) % destination.dayPlans.length]}`);
  }
  return result;
}

function renderBookingDetails() {
  const selectedName = destinationInput.value;
  const selectedDays = Math.max(1, Number(bookingDaysInput.value) || 4);
  const dest         = getDestinationByName(selectedName);
  if (!dest) { bookingDetailsOutput.innerHTML = "<p>Select a valid destination.</p>"; return; }

  const totalEstimate = dest.price * selectedDays;
  const dayList       = getDayWisePlan(dest, selectedDays).map((l) => `<li>${l}</li>`).join("");

  bookingDetailsOutput.innerHTML = `
    <h4>${dest.name}</h4>
    <p><strong>Best time:</strong> ${dest.bestTime}</p>
    <p><strong>Famous for:</strong> ${dest.famousFor}</p>
    <p><strong>Starting price per day:</strong> ${formatINR(dest.price)}</p>
    <p><strong>Estimated total for ${selectedDays} day(s):</strong> ${formatINR(totalEstimate)}</p>
    <p><strong>Day wise plan:</strong></p>
    <ul>${dayList}</ul>`;
}

function openBookingModal(destinationName) {
  bookingModal.classList.add("show");
  bookingForm.style.display          = "grid";
  bookingConfirmation.style.display  = "none";
  if (destinationName) destinationInput.value = destinationName;
  // Default date to today
  if (!travelDateInput.value) {
    travelDateInput.value = new Date().toISOString().split("T")[0];
  }
  renderBookingDetails();
}

function closeBookingModal() {
  bookingModal.classList.remove("show");
}

// 8) Submit booking to Java backend
bookingForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const payload = {
    name:        document.getElementById("nameInput").value.trim(),
    email:       document.getElementById("emailInput").value.trim(),
    origin:      originInput.value.trim(),
    destination: destinationInput.value,
    date:        travelDateInput.value,
    days:        bookingDaysInput.value,
    mode:        modeInput.value,
    seat:        seatInput.value,
    food:        foodInput.value,
    payment:     paymentInput.value,
    passengers:  passengersInput.value.trim(),
    message:     document.getElementById("messageInput").value.trim()
  };

  submitBookingBtn.disabled    = true;
  submitBookingBtn.textContent = "Confirming...";

  try {
    const response = await fetch("/api/book", {
      method:  "POST",
      headers: { "Content-Type": "application/json" },
      body:    JSON.stringify(payload)
    });

    const data = await response.json();

    if (!response.ok) {
      alert("Booking failed: " + (data.error || "Unknown error"));
      return;
    }

    // Show confirmation screen
    const dest    = getDestinationByName(data.destination);
    const dayList = dest
      ? getDayWisePlan(dest, Number(data.days)).map((l) => `<li>${l}</li>`).join("")
      : "";

    confirmationDetails.innerHTML = `
      <p><strong>Booking ID:</strong> ${data.bookingId}</p>
      <p><strong>Passenger:</strong> ${data.name} (${data.email})</p>
      <p><strong>Route:</strong> ${data.origin} → ${data.destination}</p>
      <p><strong>Date:</strong> ${data.date} &nbsp;|&nbsp; <strong>Days:</strong> ${data.days}</p>
      <p><strong>Transport:</strong> ${data.mode} &nbsp;|&nbsp; <strong>Seat:</strong> ${data.seat}</p>
      <p><strong>Food:</strong> ${data.food} &nbsp;|&nbsp; <strong>Payment:</strong> ${data.payment}</p>
      <p><strong>Total Fare:</strong> <span style="color:var(--primary);font-size:1.1rem;font-weight:800;">${formatINR(data.totalFare)}</span></p>
      ${dayList ? `<p style="margin-top:0.5rem"><strong>Your Day Plan:</strong></p><ul>${dayList}</ul>` : ""}`;

    bookingForm.style.display         = "none";
    bookingConfirmation.style.display = "block";
    bookingForm.reset();
    bookingDaysInput.value = 4;

  } catch (err) {
    alert("Could not reach the server. Make sure the Java backend is running.\n\n" + err.message);
  } finally {
    submitBookingBtn.disabled    = false;
    submitBookingBtn.textContent = "Confirm Booking";
  }
});

// 9) Event listeners
searchInput.addEventListener("input",  updateDestinations);
regionFilter.addEventListener("change", updateDestinations);
budgetFilter.addEventListener("change", updateDestinations);
sortFilter.addEventListener("change",   updateDestinations);

resetFiltersButton.addEventListener("click", () => {
  searchInput.value  = "";
  regionFilter.value = "all";
  budgetFilter.value = "all";
  sortFilter.value   = "popular";
  updateDestinations();
});

daysInput.addEventListener("input", () => { daysValue.textContent = `${daysInput.value} days`; });

plannerForm.addEventListener("submit", (event) => {
  event.preventDefault();
  showItinerary(generateItinerary(styleInput.value, Number(daysInput.value), interestInput.value));
});

menuToggle.addEventListener("click", () => { navLinks.classList.toggle("show"); });

nextStoryButton.addEventListener("click", nextStory);
prevStoryButton.addEventListener("click", prevStory);

openBookingModalButton.addEventListener("click", () => { openBookingModal(null); });
closeBookingModalButton.addEventListener("click", closeBookingModal);
closeConfirmBtn.addEventListener("click", closeBookingModal);

bookingModal.addEventListener("click", (event) => {
  if (event.target === bookingModal) closeBookingModal();
});

destinationInput.addEventListener("change", renderBookingDetails);
bookingDaysInput.addEventListener("input",  renderBookingDetails);

destinationsGrid.addEventListener("click", (event) => {
  if (event.target.classList.contains("book-now-btn")) {
    openBookingModal(event.target.dataset.destination);
  }
});

setInterval(nextStory, 6000);

// 10) Initial setup
populateDestinationDropdown();
renderDestinationCards(destinations);
renderStory(currentStoryIndex);
renderBookingDetails();

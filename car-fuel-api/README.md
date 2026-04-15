# Car Fuel API 🚗

A Spring Boot REST API that accepts a car model name and returns its average fuel consumption in **L/100 km** — powered by Claude AI (free tier).

---

## Prerequisites

| Tool | Version |
|------|---------|
| Java | 17+ |
| Maven | 3.8+ |
| Anthropic API key | Free at [console.anthropic.com](https://console.anthropic.com) |

---

## Getting your FREE API key

1. Sign up at **https://console.anthropic.com**
2. Go to **API Keys** → **Create Key**
3. Copy the key (starts with `sk-ant-...`)

> The free tier includes enough credits to run this project without a credit card.

---

## Running the app

### Option A — environment variable (recommended)

```bash
export ANTHROPIC_API_KEY=sk-ant-your-key-here
mvn spring-boot:run
```

### Option B — edit application.properties

Replace `your-api-key-here` in `src/main/resources/application.properties`.

---

## API Usage

### POST /api/fuel

```bash
curl -X POST http://localhost:8080/api/fuel \
  -H "Content-Type: application/json" \
  -d '{"carModel": "Toyota Corolla 2020 1.8L"}'
```

**Response:** `7.1`

---

### GET /api/fuel?model=...

```bash
curl "http://localhost:8080/api/fuel?model=BMW+320d+2022"
```

**Response:** `5.8`

---

## Project Structure

```
src/main/java/com/fuelapi/
├── CarFuelApiApplication.java      # Entry point
├── controller/
│   └── FuelController.java         # POST & GET endpoints
├── service/
│   └── FuelLookupService.java      # Calls Claude API, parses number
└── model/
    ├── FuelRequest.java            # Inbound DTO
    ├── ClaudeRequest.java          # Claude API request DTO
    └── ClaudeResponse.java         # Claude API response DTO
```

---

## How it works

1. Client sends a car model string.
2. The service builds a strict prompt asking Claude for **only a number**.
3. Claude (`claude-haiku-4-5`) responds (cheapest model, minimises token usage).
4. The raw response is sanitised with a regex and parsed to `double`.
5. The number is returned directly to the caller.

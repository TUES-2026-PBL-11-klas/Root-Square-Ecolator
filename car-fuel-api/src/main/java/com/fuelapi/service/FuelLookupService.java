package com.fuelapi.service;

import com.fuelapi.model.GroqRequest;
import com.fuelapi.model.GroqRequest.Message;
import com.fuelapi.model.GroqResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FuelLookupService {

    private static final Logger log = LoggerFactory.getLogger(FuelLookupService.class);

    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model:llama-3.3-70b-versatile}")
    private String model;

    private final WebClient webClient;

    public FuelLookupService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(GROQ_API_URL).build();
    }

    public double getFuelConsumption(String carModel) {
        // * Build a strict prompt so the model returns a parseable numeric value.
        String prompt = buildPrompt(carModel);

        // * Keep token output tiny; we only need one short numeric answer.
        GroqRequest request = new GroqRequest(
            model,
                16,
                List.of(new Message("user", prompt))
        );

        log.info("Sending request to Groq API for car model: {} using model: {}", carModel, model);

        // * Call Groq Chat Completions and map HTTP failures to rich runtime errors.
        GroqResponse response = webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                    clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                        // ! Include status + body in logs to diagnose prompt/auth/quota issues quickly.
                        log.error("Groq API error - Status: {}, Body: {}", clientResponse.statusCode(), errorBody);
                        return Mono.error(new RuntimeException("Groq API error " + clientResponse.statusCode() + ": " + errorBody));
                    })
                )
                .bodyToMono(GroqResponse.class)
                .block();

        if (response == null) {
            // ! Defensive guard: block() may return null if no body is provided.
            throw new RuntimeException("No response from Groq API");
        }

        // * Parse the first assistant message as the fuel consumption value.
        String rawText = response.firstText().trim();
        log.info("Groq raw response: {}", rawText);
        return parseNumber(rawText, carModel);
    }

    private String buildPrompt(String carModel) {
        return """
                What is the average combined fuel consumption (city + highway) of the %s in litres per 100 kilometres (L/100 km)?
                
                Rules:
                - Reply with ONLY the numeric value, nothing else (e.g. 7.2).
                - Use the combined/mixed cycle figure.
                - If the exact model year is unknown, use the most common/recent variant.
                - If the car is electric or hydrogen, reply with 0.
                - Never add units, words, or punctuation — just the bare number.
                """.formatted(carModel);
    }

    private double parseNumber(String text, String carModel) {
        // * Strip everything except digits and decimal separators.
        String cleaned = text.replaceAll("[^0-9.,]", "").replace(",", ".");
        if (cleaned.chars().filter(c -> c == '.').count() > 1) {
            // ! If the model returns multiple decimals, keep the first numeric segment only.
            int secondDot = cleaned.indexOf('.', cleaned.indexOf('.') + 1);
            cleaned = cleaned.substring(0, secondDot);
        }
        if (cleaned.isEmpty()) {
            throw new RuntimeException(
                    "AI returned an unparseable response for '%s': %s".formatted(carModel, text));
        }
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            throw new RuntimeException(
                    "Could not parse fuel value '%s' for model '%s'".formatted(cleaned, carModel));
        }
    }
}

package com.fuelapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroqRequest {

    // * Chat model identifier, e.g. llama-3.3-70b-versatile.
    private String model;

    // * Groq/OpenAI-compatible field name required by the API contract.
    @JsonProperty("max_tokens")
    private int maxTokens;

    // * Conversation payload; only one user message is currently sent.
    private List<Message> messages;

    public static class Message {
        // * Expected values are typically user, assistant, or system.
        private String role;
        // * Raw natural-language prompt body.
        private String content;

        public Message() {
        }

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public GroqRequest() {
    }

    public GroqRequest(String model, int maxTokens, List<Message> messages) {
        this.model = model;
        this.maxTokens = maxTokens;
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

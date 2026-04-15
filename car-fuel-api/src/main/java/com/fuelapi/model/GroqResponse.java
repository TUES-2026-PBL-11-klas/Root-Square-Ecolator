package com.fuelapi.model;

import java.util.List;

public class GroqResponse {

    private List<Choice> choices;

    public static class Choice {
        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    public static class Message {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public String firstText() {
        if (choices == null || choices.isEmpty()) return "";
        Choice choice = choices.get(0);
        if (choice.getMessage() == null) return "";
        return choice.getMessage().getContent();
    }
}

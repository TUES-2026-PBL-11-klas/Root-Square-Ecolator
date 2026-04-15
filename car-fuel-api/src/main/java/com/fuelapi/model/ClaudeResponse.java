package com.fuelapi.model;

import java.util.List;

public class ClaudeResponse {

    private List<ContentBlock> content;

    public static class ContentBlock {
        private String type;
        private String text;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public List<ContentBlock> getContent() {
        return content;
    }

    public void setContent(List<ContentBlock> content) {
        this.content = content;
    }

    /** Convenience: pull out the first text block */
    public String firstText() {
        if (content == null || content.isEmpty()) return "";
        return content.stream()
                .filter(b -> "text".equals(b.getType()))
                .map(ContentBlock::getText)
                .findFirst()
                .orElse("");
    }
}

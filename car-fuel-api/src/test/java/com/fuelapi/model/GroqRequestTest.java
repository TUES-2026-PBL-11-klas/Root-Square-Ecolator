package com.fuelapi.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GroqRequestTest {

    @Test
    void shouldBuildMessageWithConstructorAndSetters() {
        GroqRequest.Message message = new GroqRequest.Message("user", "Prompt");

        assertThat(message.getRole()).isEqualTo("user");
        assertThat(message.getContent()).isEqualTo("Prompt");

        message.setRole("assistant");
        message.setContent("7.2");

        assertThat(message.getRole()).isEqualTo("assistant");
        assertThat(message.getContent()).isEqualTo("7.2");
    }

    @Test
    void shouldSetAndGetRequestFields() {
        GroqRequest.Message message = new GroqRequest.Message("user", "What is fuel usage?");
        GroqRequest request = new GroqRequest("llama-3", 16, List.of(message));

        assertThat(request.getModel()).isEqualTo("llama-3");
        assertThat(request.getMaxTokens()).isEqualTo(16);
        assertThat(request.getMessages()).hasSize(1);
        assertThat(request.getMessages().get(0).getContent()).isEqualTo("What is fuel usage?");

        request.setModel("llama-3.3-70b-versatile");
        request.setMaxTokens(8);
        request.setMessages(List.of(new GroqRequest.Message("system", "Only number")));

        assertThat(request.getModel()).isEqualTo("llama-3.3-70b-versatile");
        assertThat(request.getMaxTokens()).isEqualTo(8);
        assertThat(request.getMessages()).hasSize(1);
        assertThat(request.getMessages().get(0).getRole()).isEqualTo("system");
    }
}

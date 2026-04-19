package com.fuelapi.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GroqResponseTest {

    @Test
    void firstTextShouldReturnEmptyWhenChoicesAreNull() {
        GroqResponse response = new GroqResponse();

        assertThat(response.firstText()).isEmpty();
    }

    @Test
    void firstTextShouldReturnEmptyWhenChoicesAreEmpty() {
        GroqResponse response = new GroqResponse();
        response.setChoices(List.of());

        assertThat(response.firstText()).isEmpty();
    }

    @Test
    void firstTextShouldReturnEmptyWhenFirstChoiceMessageIsNull() {
        GroqResponse.Choice choice = new GroqResponse.Choice();
        GroqResponse response = new GroqResponse();
        response.setChoices(List.of(choice));

        assertThat(response.firstText()).isEmpty();
    }

    @Test
    void firstTextShouldReturnContentWhenPresent() {
        GroqResponse.Message message = new GroqResponse.Message();
        message.setContent("7.1");

        GroqResponse.Choice choice = new GroqResponse.Choice();
        choice.setMessage(message);

        GroqResponse response = new GroqResponse();
        response.setChoices(List.of(choice));

        assertThat(response.getChoices()).hasSize(1);
        assertThat(response.getChoices().get(0).getMessage().getContent()).isEqualTo("7.1");
        assertThat(response.firstText()).isEqualTo("7.1");
    }
}

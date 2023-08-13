package org.llm4j.palm;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.EnvironmentConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.llm4j.api.ChatHistory;
import org.llm4j.api.LanguageModel;

import static com.google.common.truth.Truth.assertWithMessage;

public class PaLMLanguageModelTest {

    @Test
    @DisplayName("Should generate text")
    public void should_process_text_generation_request() throws ConfigurationException {

        Configuration config = new Configurations().properties("llm4j.properties");

        LanguageModel llm = new PaLMLanguageModel.Builder()
                .getLanguageModel(config);

        String answer = llm.process("In what country is El Outed located?");

        assertWithMessage("Answer should contain right answer").
                that(answer.toLowerCase()).contains("algeria");
    }

    @Test
    @DisplayName("Should process chat request")
    public void should_process_chat_request() throws ConfigurationException {

        Configuration config = new Configurations().properties("llm4j.properties");

        LanguageModel llm = new PaLMLanguageModel.Builder()
                .getLanguageModel(config);

        ChatHistory history = new ChatHistory()
                .setContext("Respond to all questions with a rhyming poem.")
                .addExample(
                "What is the capital of Algeria?",
                "If the capital of Algeria is what you seek, Algiers is where you ought to peek.")
                .addMessage("How tall is Makam Echahid?");

        String answer = llm.process(history);

        assertWithMessage("Answer should contain right answer").
                that(answer.toLowerCase()).contains("algeria");
    }
}

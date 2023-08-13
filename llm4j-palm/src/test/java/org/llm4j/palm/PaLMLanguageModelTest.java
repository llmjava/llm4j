package org.llm4j.palm;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.EnvironmentConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.llm4j.api.LanguageModel;

import static com.google.common.truth.Truth.assertWithMessage;

public class PaLMLanguageModelTest {

    @Test
    @DisplayName("Should check all LLM respond")
    public void should_send_messages_and_receive_response() throws ConfigurationException {

        Configuration config = new Configurations().properties("llm4j.properties");

        LanguageModel llm = new PaLMLanguageModel.Builder()
                .getLanguageModel(config);

        String answer = llm.process("In what country is El Outed located?");

        assertWithMessage("Answer should contain right answer").
                that(answer.toLowerCase()).contains("algeria");
    }
}

package org.llm4j.palm;

import com.google.ai.generativelanguage.v1beta2.GenerateTextRequest;
import com.google.ai.generativelanguage.v1beta2.GenerateTextResponse;
import com.google.ai.generativelanguage.v1beta2.TextCompletion;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.api.LanguageModel;
import org.llm4j.api.LanguageModelFactory;

import com.google.ai.generativelanguage.v1beta2.TextPrompt;

public class PaLMLanguageModel implements LanguageModel {

    private Configuration config;
    private PaLMClient client;

    public PaLMLanguageModel(Builder builder) {
        this.config = builder.config;
        this.client = builder.client;
    }

    @Override
    public String process(String text) {
        GenerateTextRequest request = new GenerateTextRequestFactory()
                .withInputs(text)
                .withConfig(config)
                .build();

        TextCompletion response = client.generate(request);

        return response.getOutput();
    }

    public static final class Builder implements LanguageModelFactory {
        private Configuration config;
        private PaLMClient client;

        public LanguageModel getLanguageModel(Configuration config) {
            this.config = config;
            this.client = new PaLMClient.Builder().withConfig(config).build();
            return new PaLMLanguageModel(this);
        }
    }
}

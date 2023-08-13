package org.llm4j.huggingface;

import org.apache.commons.configuration2.Configuration;
import org.llm4j.api.ChatHistory;
import org.llm4j.api.LanguageModel;
import org.llm4j.api.LanguageModelFactory;
import org.llm4j.huggingface.request.TextGenerationRequest;
import org.llm4j.huggingface.request.TextGenerationResponse;

public class HFLanguageModel implements LanguageModel {

    private Configuration config;
    private HFApiClient client;
    public HFLanguageModel(Builder builder) {
        this.config = builder.config;
        this.client = builder.client;
    }

    public String process(String text) {
        TextGenerationRequest request = new TextGenerationRequest.Builder()
                .withInputs(text)
                .withConfig(this.config)
                .build();

        TextGenerationResponse response = client.generate(request);

        return response.getGeneratedText();
    }

    @Override
    public String process(ChatHistory history) {
        return null;
    }

    public static final class Builder implements LanguageModelFactory {
        private Configuration config;
        private HFApiClient client;

        public LanguageModel getLanguageModel(Configuration config) {
            this.config = config;
            this.client = new HFApiClient.Builder().withConfig(config).build();
            return new HFLanguageModel(this);
        }
    }
}

package org.llm4j.openai.request;

import dev.ai4j.openai4j.completion.CompletionRequest;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.openai.OpenAIConfig;


public class CompletionRequestFactory {

    private String text;
    private String modelId;
    private Double temperature;


    public CompletionRequestFactory withText(String text) {
        this.text = text;
        return this;
    }

    public CompletionRequestFactory withConfig(OpenAIConfig config) {
        this.modelId = config.getModelId();
        this.temperature = config.getTemperature();
        return this;
    }

    public CompletionRequest build() {
        CompletionRequest request = CompletionRequest.builder()
                .model(modelId)
                .prompt(text)
                .temperature(temperature)
                .build();
        return request;
    }
}

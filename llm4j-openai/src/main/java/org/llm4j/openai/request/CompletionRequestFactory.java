package org.llm4j.openai.request;

import dev.ai4j.openai4j.completion.CompletionRequest;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.openai.OpenAIConfig;


public class CompletionRequestFactory {

    private String text;
    private String modelId;
    private Double temperature;
    private Double topP;
    private Integer maxTokens;
    private Double presencePenalty;
    private Double frequencyPenalty;


    public CompletionRequestFactory withText(String text) {
        this.text = text;
        return this;
    }

    public CompletionRequestFactory withConfig(OpenAIConfig config) {
        this.modelId = config.getModelId();
        this.temperature = config.getTemperature();
        this.topP = config.getTopP();
        this.maxTokens = config.getMaxTokens();
        this.presencePenalty = config.getPresencePenalty();
        this.frequencyPenalty = config.getFrequencyPenalty();
        return this;
    }

    public CompletionRequest build() {
        CompletionRequest request = CompletionRequest.builder()
                .model(modelId)
                .prompt(text)
                .temperature(temperature)
                .topP(topP)
                .maxTokens(maxTokens)
                .presencePenalty(presencePenalty)
                .frequencyPenalty(frequencyPenalty)
                .build();
        return request;
    }
}

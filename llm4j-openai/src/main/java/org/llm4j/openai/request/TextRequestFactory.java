package org.llm4j.openai.request;

import dev.ai4j.openai4j.completion.CompletionRequest;


public class TextRequestFactory extends RequestFactory<CompletionRequest> {

    private String text;


    public TextRequestFactory withText(String text) {
        this.text = text;
        return this;
    }
    @Override
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

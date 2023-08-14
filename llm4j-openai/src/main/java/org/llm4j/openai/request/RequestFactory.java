package org.llm4j.openai.request;

import org.llm4j.openai.OpenAIConfig;


public abstract class RequestFactory<T> {
    protected String modelId;
    protected Double temperature;
    protected Double topP;
    protected Integer maxTokens;
    protected Double presencePenalty;
    protected Double frequencyPenalty;

    public RequestFactory<T> withConfig(OpenAIConfig config) {
        this.modelId = config.getModelId();
        this.temperature = config.getTemperature();
        this.topP = config.getTopP();
        this.maxTokens = config.getMaxTokens();
        this.presencePenalty = config.getPresencePenalty();
        this.frequencyPenalty = config.getFrequencyPenalty();
        return this;
    }

    public abstract T build();
}

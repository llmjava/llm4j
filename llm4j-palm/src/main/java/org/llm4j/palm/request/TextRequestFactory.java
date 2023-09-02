package org.llm4j.palm.request;

import com.google.ai.generativelanguage.v1beta2.GenerateTextRequest;
import com.google.ai.generativelanguage.v1beta2.TextPrompt;
import org.llm4j.palm.PaLMConfig;

public class TextRequestFactory {

    /**
     * which model to use to generate the result
     */
    private String modelId;
    private TextPrompt prompt;
    private PaLMRequestParameters parameters;

    public TextRequestFactory withInputs(String inputs) {
        this.prompt = TextPrompt.newBuilder()
                .setText(inputs)
                .build();
        return this;
    }

    public TextRequestFactory withConfig(PaLMConfig configs) {
        this.modelId = configs.getTextModelId();
        this.parameters = PaLMRequestParameters.builder()
                        .withConfig(configs)
                        .build();
        return this;
    }

    public GenerateTextRequest build() {
        GenerateTextRequest request = GenerateTextRequest.newBuilder()
                .setModel(modelId)
                .setPrompt(prompt)
                .setTemperature(parameters.temperature.floatValue())
                .setCandidateCount(parameters.candidateCount)
                .setMaxOutputTokens(parameters.maxNewTokens)
                .setTopK(parameters.topK)
                .setTopP(parameters.topP.floatValue())
                .build();
        return request;
    }
}

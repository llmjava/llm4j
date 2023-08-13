package org.llm4j.palm;

import com.google.ai.generativelanguage.v1beta2.GenerateTextRequest;
import com.google.ai.generativelanguage.v1beta2.TextPrompt;
import org.apache.commons.configuration2.Configuration;

public class GenerateTextRequestFactory {

    static String CHAT_MODEL_ID = "chat-bison-001";
    static String TEXT_MODEL_ID = "text-bison-001";

    /**
     * which model to use to generate the result
     */
    private String modelId;
    private TextPrompt prompt;
    private PaLMRequestParameters parameters;

    public GenerateTextRequestFactory withInputs(String inputs) {
        this.prompt = TextPrompt.newBuilder()
                .setText(inputs)
                .build();
        return this;
    }

    public GenerateTextRequestFactory withConfig(Configuration configs) {
        this.modelId = configs.getString("modelId", TEXT_MODEL_ID);
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

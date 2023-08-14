package org.llm4j.openai.request;

import dev.ai4j.openai4j.moderation.ModerationRequest;

import java.util.ArrayList;
import java.util.List;


public class ModerationRequestFactory extends RequestFactory<ModerationRequest> {

    private List<String> inputs = new ArrayList<>();

    public ModerationRequestFactory withInput(String input) {
        this.inputs.add(input);
        return this;
    }

    public ModerationRequestFactory withInputs(List<String> inputs) {
        this.inputs.addAll(inputs);
        return this;
    }
    @Override
    public ModerationRequest build() {
        ModerationRequest request = ModerationRequest.builder()
                .model(modelId)
                .input(inputs)
                .build();
        return request;
    }
}

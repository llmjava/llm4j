package org.llm4j.palm.request;

import com.google.ai.generativelanguage.v1beta2.EmbedTextRequest;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.palm.PaLMConfig;

public class EmbedRequestFactory {

    /**
     * which model to use to generate the result
     */
    private String modelId;
    private String text;
    private PaLMRequestParameters parameters;

    public EmbedRequestFactory withText(String text) {
        this.text = text;
        return this;
    }

    public EmbedRequestFactory withConfig(PaLMConfig configs) {
        this.modelId = configs.getEmbedModelId();
        return this;
    }

    public EmbedTextRequest build() {
        EmbedTextRequest request = EmbedTextRequest.newBuilder()
                .setModel(modelId)
                .setText(text)
                .build();
        return request;
    }
}

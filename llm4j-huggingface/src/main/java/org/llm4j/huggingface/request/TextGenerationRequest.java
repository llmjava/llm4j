package org.llm4j.huggingface.request;

import org.apache.commons.configuration2.Configuration;

public class TextGenerationRequest {
    private final String inputs;
    private final HFRequestParameters parameters;
    private final HFRequestOptions options;

    TextGenerationRequest(Builder builder) {
        this.inputs = builder.inputs;
        this.parameters = builder.parameters;
        this.options = builder.options;
    }
    public static final class Builder {

        private String inputs;
        private HFRequestParameters parameters;
        private HFRequestOptions options;

        public Builder withInputs(String inputs) {
            this.inputs = inputs;
            return this;
        }

        public Builder withParameters(HFRequestParameters parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder withOptions(HFRequestOptions options) {
            this.options = options;
            return this;
        }

        public Builder withConfig(Configuration configs) {
            this
                    .withParameters(HFRequestParameters.builder()
                            .withConfig(configs)
                            .build())
                    .withOptions(new HFRequestOptions.Builder()
                            .withConfig(configs)
                            .build());
            return this;
        }

        public TextGenerationRequest build() {
            return new TextGenerationRequest(this);
        }
    }
}

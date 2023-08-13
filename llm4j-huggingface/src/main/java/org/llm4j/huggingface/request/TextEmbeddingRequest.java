package org.llm4j.huggingface.request;

import org.apache.commons.configuration2.Configuration;

import java.util.List;

public class TextEmbeddingRequest {
    private final List<String> inputs;
    private final HFRequestOptions options;

    public TextEmbeddingRequest(Builder builder) {
        this.inputs = builder.inputs;
        this.options = builder.options;
    }

    public static final class Builder {

        private List<String> inputs;
        private HFRequestOptions options;


        public Builder withInputs(List<String> inputs) {
            this.inputs = inputs;
            return this;
        }

        public Builder withOptions(HFRequestOptions options) {
            this.options = options;
            return this;
        }

        public Builder withConfig(Configuration configs) {
            this
                    .withOptions(new HFRequestOptions.Builder()
                            .withConfig(configs)
                            .build());
            return this;
        }

        public TextEmbeddingRequest build() {
            return new TextEmbeddingRequest(this);
        }
    }
}

package org.llm4j.palm.request;

import org.apache.commons.configuration2.Configuration;

public class PaLMRequestParameters {

    final Integer topK;
    final Double topP;
    /**
     * controls the randomness of the output
     */
    final Double temperature;
    final Integer maxNewTokens;
    final Integer maxOutputTokens;
    /**
     * the number of generated texts to return
     */
    final Integer candidateCount;

    public PaLMRequestParameters(Builder builder) {
        this.topK = builder.topK;
        this.topP = builder.topP;
        this.temperature = builder.temperature;
        this.maxNewTokens = builder.maxNewTokens;
        this.maxOutputTokens = builder.maxOutputTokens;
        this.candidateCount = builder.candidateCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Integer topK;
        /**
         * `top_p` must be > 0.0 and < 1.0"
         */
        private Double topP;
        private Double temperature;
        private Integer maxNewTokens;
        private Integer maxOutputTokens;
        private Integer candidateCount;

        public Builder withConfig(Configuration configs) {
            this.topK = configs.getInteger("topK", 1);
            this.topP = configs.getDouble("topP", 0.9);
            this.temperature = configs.getDouble("temperature", 1.0);
            this.maxNewTokens = configs.getInteger("maxNewTokens", 10);
            this.maxOutputTokens = configs.getInteger("maxOutputTokens", 1000);
            this.candidateCount = configs.getInteger("candidateCount", 1);
            return this;
        }
        public PaLMRequestParameters build() {
            return new PaLMRequestParameters(this);
        }
    }
}

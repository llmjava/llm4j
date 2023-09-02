package org.llm4j.palm.request;

import org.apache.commons.configuration2.Configuration;
import org.llm4j.palm.PaLMConfig;

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

        public Builder withConfig(PaLMConfig configs) {
            this.topK = configs.getTopK();
            this.topP = configs.getTopP();
            this.temperature = configs.getTemperature();
            this.maxNewTokens = configs.getMaxNewTokens();
            this.maxOutputTokens = configs.getMaxOutputTokens();
            this.candidateCount = configs.getCandidateCount();
            return this;
        }
        public PaLMRequestParameters build() {
            return new PaLMRequestParameters(this);
        }
    }
}

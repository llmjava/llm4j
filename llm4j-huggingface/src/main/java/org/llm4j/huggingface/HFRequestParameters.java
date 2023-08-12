package org.llm4j.huggingface;

import org.apache.commons.configuration2.Configuration;

public class HFRequestParameters {

    private final Integer topK;
    private final Double topP;
    private final Double temperature;
    private final Double repetitionPenalty;
    private final Integer maxNewTokens;
    private final Double maxTime;
    private final Boolean returnFullText;
    private final Integer numReturnSequences;
    private final Boolean doSample;

    public HFRequestParameters(Builder builder) {
        this.topK = builder.topK;
        this.topP = builder.topP;
        this.temperature = builder.temperature;
        this.repetitionPenalty = builder.repetitionPenalty;
        this.maxNewTokens = builder.maxNewTokens;
        this.maxTime = builder.maxTime;
        this.returnFullText = builder.returnFullText;
        this.numReturnSequences = builder.numReturnSequences;
        this.doSample = builder.doSample;
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
        private Double repetitionPenalty;
        private Integer maxNewTokens;
        private Double maxTime;
        private Boolean returnFullText;
        private Integer numReturnSequences;
        private Boolean doSample;

        public Builder withConfig(Configuration configs) {
            this.topK = configs.getInteger("topK", 1);
            this.topP = configs.getDouble("topP", 0.9);
            this.temperature = configs.getDouble("temperature", 1.0);
            this.repetitionPenalty = configs.getDouble("repetitionPenalty", 1.0);
            this.maxNewTokens = configs.getInteger("maxNewTokens", 10);
            this.maxTime = configs.getDouble("maxTime", 10);
            this.returnFullText = configs.getBoolean("returnFullText", true);
            this.numReturnSequences = configs.getInteger("numReturnSequences", 1);
            this.doSample = configs.getBoolean("doSample", true);
            return this;
        }
        public HFRequestParameters build() {
            return new HFRequestParameters(this);
        }
    }
}

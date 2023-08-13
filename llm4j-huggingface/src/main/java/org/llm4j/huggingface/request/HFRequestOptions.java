package org.llm4j.huggingface.request;

import org.apache.commons.configuration2.Configuration;

public class HFRequestOptions {
    private final Boolean waitForModel;
    private final Boolean useCache;

    public HFRequestOptions(Builder builder) {
        this.waitForModel = builder.waitForModel;
        this.useCache = builder.useCache;
    }

    public static final class Builder {

        private Boolean waitForModel;
        private Boolean useCache;

        public Builder withConfig(Configuration configs) {
            this.waitForModel = configs.getBoolean("hf.waitForModel", true);
            this.useCache = configs.getBoolean("hf.useCache", false);
            return this;
        }

        public HFRequestOptions build() {
            return new HFRequestOptions(this);
        }
    }

}
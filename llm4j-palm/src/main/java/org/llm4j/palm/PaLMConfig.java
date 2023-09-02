package org.llm4j.palm;

import org.apache.commons.configuration2.Configuration;

public class PaLMConfig {
    public static String MODEL_ID_KEY = "palm.modelId";
    public static String CHAT_MODEL_ID = "models/chat-bison-001";
    public static String EMBED_MODEL_ID = "models/embedding-gecko-001";
    public static String TEXT_MODEL_ID = "models/text-bison-001";

    private final Configuration configs;
    public PaLMConfig(Configuration configs) {
        this.configs = configs;
    }

    public String getChatModelId() {
        return configs.getString(PaLMConfig.MODEL_ID_KEY, CHAT_MODEL_ID);
    }

    public String getEmbedModelId() {
        return configs.getString(PaLMConfig.MODEL_ID_KEY, EMBED_MODEL_ID);
    }

    public String getTextModelId() {
        return configs.getString(PaLMConfig.MODEL_ID_KEY, TEXT_MODEL_ID);
    }

    public Integer getTopK() {
        return configs.getInteger("topK", 1);
    }

    public Double getTopP() {
        return configs.getDouble("topP", 0.9);
    }

    public Double getTemperature() {
        return configs.getDouble("temperature", 1.0);
    }

    public Integer getMaxNewTokens() {
        return configs.getInteger("maxNewTokens", 10);
    }

    public Integer getMaxOutputTokens() {
        return configs.getInteger("maxOutputTokens", 1000);
    }

    public Integer getCandidateCount() {
        return configs.getInteger("candidateCount", 1);
    }
}

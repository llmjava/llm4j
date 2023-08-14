package org.llm4j.openai;

import org.apache.commons.configuration2.Configuration;

import java.net.Proxy;
import java.time.Duration;

import static dev.ai4j.openai4j.Model.GPT_3_5_TURBO;

public class OpenAIConfig {

    public static final String PROXY_TYPE = "openai.proxy.type";
    public static final String PROXY_IP = "openai.proxy.ip";
    public static final String PROXY_PORT = "openai.proxy.port";

    public static final String MODEL_ID = "openai.modelId";

    public static final String MODEL_ID_DEFAULT = GPT_3_5_TURBO.stringValue();

    public static final String API_URL = "openai.url";
    public static final String API_URL_DEFAULT = "https://api.openai.com/";
    public static final String API_KEY = "openai.apiKey";

    public static final String TEMPERATURE = "temperature";
    public static final double TEMPERATURE_DEFAULT = 0.9;

    public static final String TOP_P = "topP";
    public static final double TOP_P_DEFAULT = 0.9;

    public static final String MAX_TOKENS = "maxTokens";
    public static final int MAX_TOKENS_DEFAULT = 128;

    public static final String PRESENCE_PENALTY = "presencePenalty";
    public static final double PRESENCE_PENALTY_DEFAULT = 0.9;

    public static final String FREQUENCY_PENALTY = "frequencyPenalty";
    public static final double FREQUENCY_PENALTY_DEFAULT = 0.9;
    public static final String TIMEOUT = "timeout";

    public static final long DEFAULT_TIMEOUT_MILLIS = 15 * 1000L;

    private final Configuration config;

    public OpenAIConfig(Configuration config) {
        this.config = config;
    }

    public String getUrl() {
        return config.getString(OpenAIConfig.API_URL, OpenAIConfig.API_URL_DEFAULT);
    }

    public String getApiKey() {
        return config.getString(OpenAIConfig.API_KEY);
    }

    public String getModelId() {
        return config.getString(OpenAIConfig.MODEL_ID, OpenAIConfig.MODEL_ID_DEFAULT);
    }

    public Double getTemperature() {
        return config.getDouble(OpenAIConfig.TEMPERATURE, TEMPERATURE_DEFAULT);
    }

    public Double getTopP() {
        return config.getDouble(OpenAIConfig.TOP_P, TOP_P_DEFAULT);
    }
    public Integer getMaxTokens() {
        return config.getInteger(OpenAIConfig.MAX_TOKENS, MAX_TOKENS_DEFAULT);
    }
    public Double getPresencePenalty() {
        return config.getDouble(OpenAIConfig.PRESENCE_PENALTY, PRESENCE_PENALTY_DEFAULT);
    }
    public Double getFrequencyPenalty() {
        return config.getDouble(OpenAIConfig.FREQUENCY_PENALTY, FREQUENCY_PENALTY_DEFAULT);
    }

    public Duration getTimeout() {
        return Duration.ofMillis(config.getLong(OpenAIConfig.TIMEOUT, OpenAIConfig.DEFAULT_TIMEOUT_MILLIS));
    }

    public boolean hasProxy() {
        return !config.getString(OpenAIConfig.PROXY_TYPE, "").isEmpty();
    }

    public Proxy.Type getProxy() {
        String proxyTypeName = config.getString(OpenAIConfig.PROXY_TYPE);
        Proxy.Type proxyType = Proxy.Type.valueOf(proxyTypeName);
        return proxyType;
    }

    public String getProxyIp() {
        return config.getString(OpenAIConfig.PROXY_IP);
    }

    public Integer getProxyPort() {
        return config.getInt(OpenAIConfig.PROXY_PORT);
    }
}

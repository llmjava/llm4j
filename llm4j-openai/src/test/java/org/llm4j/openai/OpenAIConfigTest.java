package org.llm4j.openai;

import org.apache.commons.configuration2.MapConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class OpenAIConfigTest {

    @Test
    @DisplayName("Should read configuration")
    public void should_read_configuration() {
        Map<String, Object> map = new HashMap<>();
        map.put(OpenAIConfig.API_KEY, "key");
        OpenAIConfig config = new OpenAIConfig(new MapConfiguration(map));
        assertWithMessage("Empty config has no proxy")
                .that(config.hasProxy()).isFalse();
        assertThat(config.getApiKey()).isEqualTo("key");
        assertThat(config.getUrl()).isEqualTo(OpenAIConfig.API_URL_DEFAULT);
        assertThat(config.getModelId()).isEqualTo(OpenAIConfig.MODEL_ID_DEFAULT);
        assertThat(config.getTimeout()).isEqualTo(Duration.ofMillis(OpenAIConfig.DEFAULT_TIMEOUT_MILLIS));
        assertThat(config.getTemperature()).isEqualTo(OpenAIConfig.TEMPERATURE_DEFAULT);
    }
}

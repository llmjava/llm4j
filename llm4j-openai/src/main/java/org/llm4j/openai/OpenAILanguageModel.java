package org.llm4j.openai;

import dev.ai4j.openai4j.chat.ChatCompletionRequest;
import dev.ai4j.openai4j.chat.ChatCompletionResponse;
import dev.ai4j.openai4j.completion.CompletionRequest;
import dev.ai4j.openai4j.completion.CompletionResponse;
import dev.ai4j.openai4j.embedding.EmbeddingRequest;
import dev.ai4j.openai4j.embedding.EmbeddingResponse;
import dev.ai4j.openai4j.moderation.ModerationRequest;
import dev.ai4j.openai4j.moderation.ModerationResponse;
import dev.ai4j.openai4j.moderation.ModerationResult;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.api.ChatHistory;
import org.llm4j.api.LanguageModel;
import org.llm4j.api.LanguageModelFactory;
import org.llm4j.openai.request.ChatRequestFactory;
import org.llm4j.openai.request.EmbeddingRequestFactory;
import org.llm4j.openai.request.ModerationRequestFactory;
import org.llm4j.openai.request.TextRequestFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OpenAILanguageModel implements LanguageModel {

    private final OpenAIClient client;
    private final OpenAIConfig config;
    OpenAILanguageModel(Builder builder) {
        this.client = builder.client;
        this.config = builder.config;
    }
    @Override
    public String process(String text) {
        CompletionRequest request = new TextRequestFactory()
                .withText(text)
                .withConfig(config)
                .build();

        CompletionResponse response = client.generate(request);

        return response.text();
    }

    @Override
    public String process(ChatHistory history) {
        ChatCompletionRequest request = new ChatRequestFactory()
                .withChat(history)
                .withConfig(config)
                .build();

        ChatCompletionResponse response = client.generate(request);
        return response.content();
    }

    @Override
    public List<Float> embed(String text) {

        EmbeddingRequest request = new EmbeddingRequestFactory()
                .withText(text)
                .withConfig(config)
                .build();

        EmbeddingResponse response = client.embed(request);

        return response.embedding();
    }

    public List<ModerationResult> moderate(String input) {
        return moderate(Collections.singletonList(input));
    }
    public List<ModerationResult> moderate(List<String> inputs) {

        ModerationRequest request = new ModerationRequestFactory()
                .withInputs(inputs)
                .withConfig(config)
                .build();

        ModerationResponse response = client.moderate(request);

        return response.results();
    }

    public static final class Builder implements LanguageModelFactory {
        private OpenAIClient client;
        private OpenAIConfig config;

        public LanguageModel getLanguageModel(Configuration config) {
            this.config = new OpenAIConfig(config);
            this.client = new OpenAIClient.Builder()
                    .withConfig(this.config)
                    .build();
            return new OpenAILanguageModel(this);
        }
    }
}

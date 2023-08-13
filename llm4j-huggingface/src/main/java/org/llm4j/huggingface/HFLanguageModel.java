package org.llm4j.huggingface;

import org.apache.commons.configuration2.Configuration;
import org.llm4j.api.ChatHistory;
import org.llm4j.api.LanguageModel;
import org.llm4j.api.LanguageModelFactory;
import org.llm4j.huggingface.request.TextEmbeddingRequest;
import org.llm4j.huggingface.request.TextGenerationRequest;
import org.llm4j.huggingface.request.TextGenerationResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HFLanguageModel implements LanguageModel {

    private Configuration config;
    private HFApi api;
    public HFLanguageModel(Builder builder) {
        this.config = builder.config;
        this.api = builder.api;
    }

    public String process(String text) {
        TextGenerationRequest request = new TextGenerationRequest.Builder()
                .withInputs(text)
                .withConfig(config)
                .build();
        HFApiClient client = new HFApiClient.Builder()
                .withApi(api)
                .withTextModel(config)
                .build();
        TextGenerationResponse response = client.generate(request);

        return response.getGeneratedText();
    }

    @Override
    public String process(ChatHistory history) {
        List<String> lines = new ArrayList<>();
        // Add context
        if(history.getContext()!=null) lines.add(history.getContext());
        // Add examples
        for(Map.Entry<ChatHistory.Message, ChatHistory.Message> pair: history.getExampleList()) {
            lines.add(pair.getKey().toString());
            lines.add(pair.getValue().toString());
        }
        // Add conversations
        for(ChatHistory.Message message: history.getMessageList()) {
            lines.add(message.toString());
        }
        // submit
        String text = String.join("\n", lines);

        return process(text);
    }

    @Override
    public List<Float> embed(String text) {
        List<String> segments = new ArrayList<>();
        segments.add(text);
        TextEmbeddingRequest request = new TextEmbeddingRequest.Builder()
                .withInputs(segments)
                .withConfig(config)
                .build();
        HFApiClient client = new HFApiClient.Builder()
                .withApi(api)
                .withEmbedModel(config)
                .build();
        float[] arr = client.embed(request).get(0);
        List<Float> embeddings = new ArrayList<>();
        for(float f: arr) {
            embeddings.add(f);
        }
        return embeddings;
    }

    public static final class Builder implements LanguageModelFactory {
        private Configuration config;
        private HFApi api;

        public LanguageModel getLanguageModel(Configuration config) {
            this.config = config;
            this.api = new HFApiFactory().build(config);
            return new HFLanguageModel(this);
        }
    }
}

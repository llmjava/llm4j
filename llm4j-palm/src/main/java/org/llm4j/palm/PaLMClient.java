package org.llm4j.palm;

import com.google.ai.generativelanguage.v1beta2.*;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;
import java.util.HashMap;

public class PaLMClient {

    private final TextServiceClient textClient;
    private final DiscussServiceClient chatClient;

    PaLMClient(Builder builder) {
        this.chatClient = builder.chatClient;
        this.textClient = builder.textClient;
    }

    //TODO handle the case where no response is returned response.getCandidatesList() is empty
    public TextCompletion generate(GenerateTextRequest request) {
        GenerateTextResponse response = textClient.generateText(request);

        TextCompletion returnedText = response.getCandidatesList().get(0);
        return returnedText;
    }

    public Message chat(GenerateMessageRequest request) {
        GenerateMessageResponse response = chatClient.generateMessage(request);

        Message returnedText = response.getCandidatesList().get(0);
        return returnedText;
    }


    static class Builder {
        TextServiceClient textClient;
        DiscussServiceClient chatClient;

        Builder withConfig(Configuration config) {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("x-goog-api-key", config.getString("palm.apiKey"));
            this.textClient = buildTextServiceClient(headers);
            this.chatClient = buildDiscussServiceClient(headers);
            return this;
        }

        TextServiceClient buildTextServiceClient(HashMap<String, String> headers) {
            TextServiceClient client = null;
            try {
                TransportChannelProvider provider = InstantiatingGrpcChannelProvider.newBuilder()
                        .setHeaderProvider(FixedHeaderProvider.create(headers))
                        .build();

                TextServiceSettings settings = TextServiceSettings.newBuilder()
                        .setTransportChannelProvider(provider)
                        .setCredentialsProvider(FixedCredentialsProvider.create(null))
                        .build();

                client = TextServiceClient.create(settings);
            } catch (IOException e) {
                System.err.println("Failed to create a PaLM Text client");
            }
            return client;
        }

        DiscussServiceClient buildDiscussServiceClient(HashMap<String, String> headers) {
            DiscussServiceClient client = null;
            try {
                TransportChannelProvider provider = InstantiatingGrpcChannelProvider.newBuilder()
                        .setHeaderProvider(FixedHeaderProvider.create(headers))
                        .build();

                DiscussServiceSettings settings = DiscussServiceSettings.newBuilder()
                        .setTransportChannelProvider(provider)
                        .setCredentialsProvider(FixedCredentialsProvider.create(null))
                        .build();

                client = DiscussServiceClient.create(settings);
            } catch (IOException e) {
                System.err.println("Failed to create a PaLM Text client");
            }
            return client;
        }

        public PaLMClient build() {
            return new PaLMClient(this);
        }
    }
}

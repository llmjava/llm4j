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

    PaLMClient(Builder builder) {
        this.textClient = builder.textClient;
    }

    //TODO handle the case where no response is returned response.getCandidatesList() is empty
    public TextCompletion generate(GenerateTextRequest request) {
        GenerateTextResponse response = textClient.generateText(request);

        TextCompletion returnedText = response.getCandidatesList().get(0);
        return returnedText;
    }

    static class Builder {
        TextServiceClient textClient;

        Builder withConfig(Configuration config) {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("x-goog-api-key", config.getString("palm.apiKey"));
            this.textClient = buildTextServiceClient(headers);
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

        public PaLMClient build() {
            return new PaLMClient(this);
        }
    }
}

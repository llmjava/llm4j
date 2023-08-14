package org.llm4j.openai;

import dev.ai4j.openai4j.OpenAiClient;
import dev.ai4j.openai4j.completion.CompletionRequest;
import dev.ai4j.openai4j.completion.CompletionResponse;
import org.llm4j.openai.request.GenerationCallback;
import org.llm4j.openai.request.StreamingCallback;

import java.net.Proxy;
import java.time.Duration;
import java.util.function.Consumer;

public class OpenAIClient {

    private final OpenAiClient client;
    OpenAIClient(Builder builder) {
        this.client = builder.client;
    }

    public CompletionResponse generate(CompletionRequest request) {
        return client.completion(request).execute();
    }

    public void generateAsync(CompletionRequest request, GenerationCallback<CompletionResponse> callback) {
        client.completion(request)
                .onResponse(completionResponse -> callback.onSuccess(completionResponse))
	            .onError(throwable -> callback.onFailure(throwable))
                .execute();
    }

    public void generateStream(CompletionRequest request, StreamingCallback<CompletionResponse> callback) {
        client.completion(request)
                .onPartialResponse(response -> callback.onPart(response))
                .onComplete(() -> callback.onComplete())
	            .onError(throwable -> callback.onFailure(throwable))
                .execute();
    }

    static class Builder {

        OpenAiClient client;
        Builder withConfig(OpenAIConfig config) {
            OpenAiClient.Builder builder = OpenAiClient.builder()
                    .url(config.getUrl())
                    .apiKey(config.getApiKey());
            // set timeout
            Duration timeout = config.getTimeout();
            builder.callTimeout(timeout)
                    .connectTimeout(timeout)
                    .readTimeout(timeout)
                    .writeTimeout(timeout);
            // set proxy
            if(config.hasProxy()) {
                Proxy.Type proxyType = config.getProxy();
                String proxyIp = config.getProxyIp();
                int proxyPort = config.getProxyPort();
                builder.proxy(proxyType, proxyIp, proxyPort);
            }
            client = builder
                    .logRequests()
                    .logResponses()
                    .logStreamingResponses()
                    .build();
            return this;
        }

        public OpenAIClient build() {
            return new OpenAIClient(this);
        }
    }

}

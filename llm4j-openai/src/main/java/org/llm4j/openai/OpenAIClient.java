package org.llm4j.openai;

import dev.ai4j.openai4j.OpenAiClient;
import dev.ai4j.openai4j.SyncOrAsync;
import dev.ai4j.openai4j.SyncOrAsyncOrStreaming;
import dev.ai4j.openai4j.chat.ChatCompletionRequest;
import dev.ai4j.openai4j.chat.ChatCompletionResponse;
import dev.ai4j.openai4j.completion.CompletionRequest;
import dev.ai4j.openai4j.completion.CompletionResponse;
import dev.ai4j.openai4j.embedding.EmbeddingRequest;
import dev.ai4j.openai4j.embedding.EmbeddingResponse;
import dev.ai4j.openai4j.moderation.ModerationRequest;
import dev.ai4j.openai4j.moderation.ModerationResponse;
import org.llm4j.openai.request.TaskCallback;
import org.llm4j.openai.request.StreamingCallback;

import java.net.Proxy;
import java.time.Duration;

public class OpenAIClient {

    private final OpenAiClient client;
    OpenAIClient(Builder builder) {
        this.client = builder.client;
    }

    public CompletionResponse generate(CompletionRequest request) {
        return client.completion(request).execute();
    }

    public void generateAsync(CompletionRequest request, TaskCallback<CompletionResponse> callback) {
        execute(client.completion(request), callback);
    }

    public void generateStream(CompletionRequest request, StreamingCallback<CompletionResponse> callback) {
        execute(client.completion(request), callback);
    }

    public ChatCompletionResponse generate(ChatCompletionRequest request) {
        return client.chatCompletion(request).execute();
    }

    public void generateAsync(ChatCompletionRequest request, TaskCallback<ChatCompletionResponse> callback) {
        execute(client.chatCompletion(request), callback);
    }

    public void generateStream(ChatCompletionRequest request, StreamingCallback<ChatCompletionResponse> callback) {
        execute(client.chatCompletion(request), callback);
    }

    public EmbeddingResponse embed(EmbeddingRequest request) {
        return client.embedding(request).execute();
    }

    public void embedAsync(EmbeddingRequest request, TaskCallback<EmbeddingResponse> callback) {
        execute(client.embedding(request), callback);
    }

    public ModerationResponse moderate(ModerationRequest request) {
        return client.moderation(request).execute();
    }

    public void moderateAsync(ModerationRequest request, TaskCallback<ModerationResponse> callback) {
        execute(client.moderation(request), callback);
    }

    private <T> void execute(SyncOrAsync<T> task, TaskCallback<T> callback) {
        task
                .onResponse(completionResponse -> callback.onSuccess(completionResponse))
                .onError(throwable -> callback.onFailure(throwable))
                .execute();
    }

    private <T> void execute(SyncOrAsyncOrStreaming<T> task, StreamingCallback<T> callback) {
        task
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

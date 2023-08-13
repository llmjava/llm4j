package org.llm4j.huggingface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.huggingface.request.TextEmbeddingRequest;
import org.llm4j.huggingface.request.TextGenerationRequest;
import org.llm4j.huggingface.request.TextGenerationResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.google.gson.FieldNamingPolicy;

class HFApiClient {

    private final HFApi api;
    private final String modelId;

    HFApiClient(Builder builder) {
        this.api = builder.api;
        this.modelId = builder.modelId;
    }

    TextGenerationResponse chat(TextGenerationRequest request) {
        return generate(request);
    }

    TextGenerationResponse generate(TextGenerationRequest request) {
        try {
            retrofit2.Response<List<TextGenerationResponse>> retrofitResponse
                    = api.generate(request, modelId).execute();

            if (retrofitResponse.isSuccessful()) {
                List<TextGenerationResponse> responses = retrofitResponse.body();
                if (responses != null && responses.size() == 1) {
                    return responses.get(0);
                } else {
                    int size = responses == null ? 0 : responses.size();
                    String message = "Expected only one generated_text, but was: " + size;
                    throw new RuntimeException(message);
                }
            } else {
                throw newException(retrofitResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    List<float[]> embed(TextEmbeddingRequest request) {
        try {
            retrofit2.Response<List<float[]>> retrofitResponse = api.embed(request, modelId).execute();
            if (retrofitResponse.isSuccessful()) {
                return retrofitResponse.body();
            } else {
                throw newException(retrofitResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO parse errors
     * code: 400; body: {"error":"Authorization header is correct, but the token seems invalid"}
     * code: 422; body: {"error":"Input validation error: `top_p` must be > 0.0 and < 1.0","error_type":"validation"}
     * code: 503; body: {"error":"Model tiiuae/falcon-7b-instruct is currently loading","estimated_time":577.3751831054688}
     */
    private static RuntimeException newException(retrofit2.Response<?> response) throws IOException {

        int code = response.code();
        String body = response.errorBody().string();
        String errorMessage = String.format("status code: %s; body: %s", code, body);
        return new RuntimeException(errorMessage);
    }

    public static class Builder {

        HFApi api;
        String modelId;

        public Builder withApi(HFApi api) {
            this.api = api;
            return this;
        }

        public Builder withEmbedModel(Configuration config) {
            this.modelId = config.getString(HFConfig.MODEL_ID, HFConfig.DEFAULT_EMBEDDING_MODEL);
            return this;
        }

        public Builder withTextModel(Configuration config) {
            this.modelId = config.getString(HFConfig.MODEL_ID, HFConfig.DEFAULT_TEXT_MODEL);
            return this;
        }

        public HFApiClient build() {
            return new HFApiClient(this);
        }
    }
}

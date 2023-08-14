package org.llm4j.huggingface;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.apache.commons.configuration2.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.Duration;

public class HFApiFactory {

    public HFApi build(Configuration config) {
        String apiKey = config.getString(HFConfig.API_KEY);
        Duration timeout = Duration.ofMillis(config.getLong(HFConfig.TIMEOUT, HFConfig.DEFAULT_TIMEOUT_MILLIS));
        HFApi api = buildApi(apiKey, timeout);
        return api;
    }

    HFApi buildApi(String apiKey, Duration timeout) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HFApiAuthorizationInterceptor(apiKey))
                .callTimeout(timeout)
                .connectTimeout(timeout)
                .readTimeout(timeout)
                .writeTimeout(timeout)
                .build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-inference.huggingface.co")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(HFApi.class);
    }
}

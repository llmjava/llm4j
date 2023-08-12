package org.llm4j.huggingface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

/**
 * Reference API https://huggingface.co/docs/api-inference/quicktour
 */
interface HFApi {

    @POST("/models/{modelId}")
    @Headers({"Content-Type: application/json"})
    Call<List<TextGenerationResponse>> generate(@Body TextGenerationRequest request, @Path("modelId") String modelId);

    @POST("/pipeline/feature-extraction/{modelId}")
    @Headers({"Content-Type: application/json"})
    Call<List<float[]>> embed(@Body TextEmbeddingRequest request, @Path("modelId") String modelId);
}


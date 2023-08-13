package org.llm4j.huggingface;

public class HFConfig {
    public static String MODEL_ID = "hf.modelId";

    public static final String DEFAULT_TEXT_MODEL = "tiiuae/falcon-7b-instruct";

    public static final String DEFAULT_EMBEDDING_MODEL = "sentence-transformers/all-MiniLM-L6-v2";

    public static String API_KEY = "hf.apiKey";
    public static String WIAT_FOR_MODEL = "hf.waitForModel";
    public static String USE_CAHE = "hf.useCache";
    public static String TIMEOUT = "timeout";

}

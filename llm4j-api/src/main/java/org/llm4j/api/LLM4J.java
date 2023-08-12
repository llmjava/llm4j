package org.llm4j.api;

import org.apache.commons.configuration2.Configuration;
import org.llm4j.spi.LLM4JServiceProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class LLM4J {

    private LLM4J(){}

    public static LanguageModel getLanguageModel(Configuration config) {
        return getLanguageModel(config, getLanguageModelFactory());
    }

    public static LanguageModel getLanguageModel(Configuration config, LanguageModelFactory factory) {
        return factory.getLanguageModel(config);
    }

    public static LanguageModelFactory getLanguageModelFactory() {
        return getProvider().getLLMFactory();
    }

    static LLM4JServiceProvider getProvider() {
        try {
            List<LLM4JServiceProvider> providersList = findServiceProviders();
            // Prints a warning message on the console if multiple providers were found
            if(providersList.size() > 1) {
                System.err.println("Class path contains multiple LLM4J providers.");
                for (LLM4JServiceProvider provider : providersList) {
                    System.err.println("Found provider [" + provider + "]");
                }
            }
            // pick first provider if there are any otherwise throw exception
            if (providersList != null && !providersList.isEmpty()) {
                LLM4JServiceProvider provider = providersList.get(0);
                System.err.println("Actual provider is of type [" + provider + "]");
                return provider;
            } else {
                throw new IllegalStateException("No LLM4J providers were found.");
            }
        } catch (Exception e) {
            System.err.println("Failed to instantiate LLM4J LoggerFactory: " + e);
            throw new IllegalStateException("Unexpected initialization failure", e);
        }
    }

    static List<LLM4JServiceProvider> findServiceProviders() {
        final ClassLoader classLoaderOfLoggerFactory = LLM4J.class.getClassLoader();
        ServiceLoader<LLM4JServiceProvider> serviceLoader = ServiceLoader.load(LLM4JServiceProvider.class, classLoaderOfLoggerFactory);;
        List<LLM4JServiceProvider> providerList = new ArrayList<>();
        Iterator<LLM4JServiceProvider> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            LLM4JServiceProvider provider = iterator.next();
            providerList.add(provider);
        }
        return providerList;
    }
}

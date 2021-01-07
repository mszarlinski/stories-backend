package com.github.mszarlinski.stories.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Configuration
@EnableConfigurationProperties(AsyncProperties.class)
class AsyncConfiguration extends AsyncConfigurerSupport {

    private final AsyncProperties asyncProperties;

    AsyncConfiguration(AsyncProperties asyncProperties) {
        this.asyncProperties = asyncProperties;
    }

    @Override
    public Executor getAsyncExecutor() {
        return new ThreadPoolExecutor(
                asyncProperties.getThreads(),
                asyncProperties.getThreads(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(asyncProperties.getQueueSize()));
    }
}

@ConstructorBinding
@ConfigurationProperties("async")
class AsyncProperties {
    private final int threads;
    private final int queueSize;

    AsyncProperties(int threads, int queueSize) {
        this.threads = threads;
        this.queueSize = queueSize;
    }

    int getThreads() {
        return threads;
    }

    int getQueueSize() {
        return queueSize;
    }
}

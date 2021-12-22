package com.example.webfluxtutorial.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Slf4j
public class HeaderInterceptor implements ReactiveHttpRequestInterceptor {
    @Value("${spring.application.name}")
    private String appName;

    @Override
    public Mono<ReactiveHttpRequest> apply(ReactiveHttpRequest reactiveHttpRequest) {
        return Mono.just(reactiveHttpRequest)
                .flatMap(request ->
                        Mono.deferContextual(ctx -> {
                            Map<String, List<String>> headers = request.headers();
                            setHeaderIfNonBlank("log_tracing_id", ctx, headers);
                            setHeaderIfNonBlank("jwt_user_header", ctx, headers);

                            return Mono.just(request);
                        })
                );
    }

    private void setHeaderIfNonBlank(String key, ContextView ctx, Map<String, List<String>> headers) {
        String requestId = ctx.getOrDefault(key,"");
        if (!StringUtils.isEmpty(requestId)) {
            headers.put(key, Arrays.asList(requestId));
        }
    }
}

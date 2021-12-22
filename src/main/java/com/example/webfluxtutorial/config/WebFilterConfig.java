package com.example.webfluxtutorial.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WebFilterConfig implements WebFilter {
    private static final String[] contextKeys = new String[]{
            "log_tracing_id",
            "jwt_user_header",
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String jwtUser = getHeaderValue(exchange, "jwt_user_header");
        exchange.getAttributes().put("jwt_user_attribute", jwtUser); // put jwtUser into request attribute, you can use @RequestAttribute("jwt_user_attribute") in Controller to get it

        return chain.filter(exchange)
                .contextWrite(context -> initWebFluxContext(context, exchange)); //put request header into Mono Context
    }

    private Context initWebFluxContext(Context context, ServerWebExchange exchange) {
        Map newContext = context.stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        for (String key : contextKeys) {
            String value = getHeaderValue(exchange, key);
            if (!StringUtils.isEmpty(value)) {
                newContext.put(key, value);
            }
        }
        return Context.of(newContext);
    }

    private String getHeaderValue(ServerWebExchange exchange, String header) {
        List<String> headers = exchange.getRequest().getHeaders().get(header);
        if (headers == null || headers.isEmpty()) {
            return null;
        }
        return headers.get(0);
    }
}

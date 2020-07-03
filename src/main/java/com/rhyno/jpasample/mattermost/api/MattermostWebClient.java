package com.rhyno.jpasample.mattermost.api;

import com.rhyno.jpasample.mattermost.exception.MattermostApiException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

@Component
public class MattermostWebClient {
    private WebClient webClient;

    public MattermostWebClient() {
        this.webClient = WebClient.builder().build();
    }

    public String post(String url,
                       Object requestBody,
                       Function<? super Throwable, ? extends Mono<? extends String>> fallback) {
        return webClient.post()
                .uri(URI.create(url))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(fallback)
                //.onErrorResume(fallback)
                .block();
    }

    public <T> T post(String url,
                      Object requestBody,
                      Class<T> clazz,
                      Function<? super Throwable, ? extends Mono<? extends T>> fallback) {
        return webClient.post()
                .uri(URI.create(url))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(clazz)
                .onErrorResume(fallback)
                //.onErrorResume(fallback)
                .block();
    }
}

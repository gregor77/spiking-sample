package com.rhyno.jpasample.mattermost.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhyno.jpasample.mattermost.exception.MattermostApiException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

@Component
public class MattermostChannelApi {
    private static final String PUBLIC_CHANNEL = "O";

    private MattermostWebClient webClient;

    public MattermostChannelApi(MattermostWebClient webClient) {
        this.webClient = webClient;
    }

    public String createAndInviteChannel(String userId) {
        return Optional.ofNullable(this.createNewChannel())
                .map(channel -> {
                    this.invite(channel.getId(),
                            userId,
                            e -> {
                                //TODO. DB에 invite error
//                                this.removeChannel(channel.getId());
                                return Mono.error(new MattermostApiException("Invite channel is error"));
                            });
                    return channel.getId();
                })
                .orElseThrow(() -> new RuntimeException("Created Channel does not exist"));
    }


    // TODO. 내일목표 -> onErrorResume(ErrorType, fallback) -> gson 제거
    public ChannelResponse createNewChannel() {
        String jsonResponse = webClient.post("http://localhost:8080/channels",
                CreateChannelRequest.builder()
                        .teamId("team1")
                        .name("channel")
                        .displayName("display-channel")
                        .type(PUBLIC_CHANNEL)
                        .build(),
                (e) -> {
//                    // 그전에 business db rollback 위해서 RuntimeException
//                    // fallback api 호출은 X
                    return Mono.error(new MattermostApiException("Create channel error"));
                }
        );

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, ChannelResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public ChannelResponse invite(String channelId,
                                  String userId,
                                  Function<? super Throwable, ? extends Mono<? extends String>> fallback) {
        String jsonResponse = webClient.post("http://localhost:8080/channels" + channelId + "/members",
                InviteChannelRequest.builder()
                        .userId(userId)
                        .build(),
                fallback);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, ChannelResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

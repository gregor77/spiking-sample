package com.rhyno.jpasample.mattermost.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteChannelRequest {
    @JsonProperty("user_id")
    private String userId;
}

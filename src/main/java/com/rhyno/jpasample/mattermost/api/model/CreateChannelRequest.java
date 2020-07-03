package com.rhyno.jpasample.mattermost.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChannelRequest {
    @JsonProperty("team_id")
    private String teamId;

    private String name;

    @JsonProperty("display_name")
    private String displayName;

    private String purpose;

    private String header;

    private String type;
}

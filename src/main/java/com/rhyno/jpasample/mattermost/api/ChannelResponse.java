package com.rhyno.jpasample.mattermost.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelResponse {
    private String id;

    @JsonProperty("create_at")
    private Date createAt;

    @JsonProperty("update_at")
    private Date updateAt;

    @JsonProperty("delete_at")
    private Date deleteAt;

    @JsonProperty("team_id")
    private String teamId;

    private String type;

    @JsonProperty("display_name")
    private String displayName;

    private String name;
    private String header;
    private String purpose;

    @JsonProperty("last_post_at")
    private Date lastPostAt;

    @JsonProperty("total_msg_count")
    private Long totalMsgCount;

    @JsonProperty("extra_update_at")
    private Date extraUpdateAt;

    @JsonProperty("creator_id")
    private String creatorId;
}

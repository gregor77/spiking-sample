package com.rhyno.jpasample.mattermost.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private HttpStatus status;
    private Object body; //정상 Response , 에러 Exception
}

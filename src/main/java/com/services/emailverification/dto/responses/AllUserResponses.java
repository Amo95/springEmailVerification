package com.services.emailverification.dto.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllUserResponses {
    private Long userId;
    private String username;
    private String email;
}

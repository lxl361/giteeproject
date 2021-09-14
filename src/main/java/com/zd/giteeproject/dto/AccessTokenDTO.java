package com.zd.giteeproject.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccessTokenDTO {
    private String clientId;
    private String redirectUri;
    private String code;
    private String clientSecret;
    private String grant_type;
}

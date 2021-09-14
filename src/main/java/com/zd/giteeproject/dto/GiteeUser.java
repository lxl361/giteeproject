package com.zd.giteeproject.dto;
import lombok.*;
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GiteeUser {
    private Long id;
    private String name;
    private String bio;
}

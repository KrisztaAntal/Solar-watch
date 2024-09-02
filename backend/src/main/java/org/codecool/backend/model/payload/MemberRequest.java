package org.codecool.backend.model.payload;

import lombok.Data;

@Data
public class MemberRequest {
    private String username;
    private String password;
}

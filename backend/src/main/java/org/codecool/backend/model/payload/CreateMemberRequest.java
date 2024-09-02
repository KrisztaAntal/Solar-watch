package org.codecool.backend.model.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateMemberRequest {
    @Column(unique = true)
    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 64)
    private String password;

    public CreateMemberRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public CreateMemberRequest() {
    }
}

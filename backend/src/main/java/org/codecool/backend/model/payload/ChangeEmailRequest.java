package org.codecool.backend.model.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeEmailRequest {

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String newEmail;

    public ChangeEmailRequest(String newEmail) {
        this.newEmail = newEmail;
    }

    public ChangeEmailRequest() {
    }
}

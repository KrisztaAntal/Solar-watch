package org.codecool.backend.model.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotNull
    @NotEmpty
    @Size(min = 8, max = 64)
    private String password;
}

package org.codecool.backend.model.payload;

import java.util.Set;

public record JwtResponse(String jwt, String userName, Set<String> roles) {
}

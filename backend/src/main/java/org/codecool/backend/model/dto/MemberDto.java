package org.codecool.backend.model.dto;

import java.util.Set;
import java.util.UUID;

public record MemberDto(UUID userId, String name, String email, Set<RoleDto> roles) {
}

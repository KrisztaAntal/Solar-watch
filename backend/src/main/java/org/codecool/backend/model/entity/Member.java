package org.codecool.backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private UUID memberId;

    @Column(unique = true)
    @NotNull(message = "Email cannot be empty")
    @NotEmpty(message = "Email cannot be empty")
    private String name;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 64)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public Member() {
    }

    public Member(String name, String email, String password) {
        this.memberId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = new HashSet<>();
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}

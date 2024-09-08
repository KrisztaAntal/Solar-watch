package org.codecool.backend.controller.member;

import org.codecool.backend.controller.exception.UnauthorizedChangeException;
import org.codecool.backend.model.dto.MemberDto;
import org.codecool.backend.model.payload.*;
import org.codecool.backend.security.jwt.JwtUtils;
import org.codecool.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class MemberController {
    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    @Autowired
    public MemberController(MemberService memberService, JwtUtils jwtUtils) {
        this.memberService = memberService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateMemberRequest signUpRequest) {
        memberService.createNewMember(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody MemberRequest loginRequest) {
        return memberService.authenticateMember(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PatchMapping("/user/change-username")
    public JwtResponse changeUsername(@RequestHeader String authorization, @RequestBody ChangeUsernameRequest request) {
        String username = checkIfInitiatedByLoggedInMember(authorization);
        return memberService.changeUsername(username, request);
    }

    @PatchMapping("/user/change-password")
    public JwtResponse changePassword(@RequestHeader String authorization, @RequestBody ChangePasswordRequest request) {
        String username = checkIfInitiatedByLoggedInMember(authorization);
        return memberService.changeMemberPassword(username, request);
    }

    @PatchMapping("/user/change-email")
    public MemberDto changeEmail(@RequestHeader String authorization, @RequestBody ChangeEmailRequest request) {
        String username = checkIfInitiatedByLoggedInMember(authorization);
        return memberService.changeMemberEmail(username, request);
    }

    @DeleteMapping("/user/delete-me")
    public ResponseEntity<Void> deleteUser(@RequestHeader String authorization) {
        String username = checkIfInitiatedByLoggedInMember(authorization);
        memberService.deleteMe(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/auth/me")
    public MemberDto getCurrentUser(@RequestHeader String authorization) {
        String username = checkIfInitiatedByLoggedInMember(authorization);
        return memberService.getCurrentUserInfo(username);
    }

    private String checkIfInitiatedByLoggedInMember(String authorization) {
        String token = authorization.substring(7);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(loggedInUsername)) {
            throw new UnauthorizedChangeException();
        }
        return username;
    }
}

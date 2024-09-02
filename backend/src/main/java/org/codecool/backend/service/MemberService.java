package org.codecool.backend.service;

import org.codecool.backend.controller.exception.ExistingUsernameException;
import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.MemberDto;
import org.codecool.backend.model.entity.Member;
import org.codecool.backend.model.entity.Role;
import org.codecool.backend.model.payload.*;
import org.codecool.backend.repository.MemberRepository;
import org.codecool.backend.repository.RoleRepository;
import org.codecool.backend.security.jwt.JwtUtils;
import org.codecool.backend.controller.exception.MemberNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public MemberService(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public void createNewMember(CreateMemberRequest signUpRequest) {
        Member member = new Member(signUpRequest.getUsername(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        if (memberRepository.findByName(member.getName()).isEmpty()) {
            Role memberRole = roleRepository.findByName("ROLE_USER").orElseThrow();
            member.addRole(memberRole);
            memberRepository.save(member);
        } else throw new ExistingUsernameException();
    }

    public MemberDto getCurrentUserInfo(String username) {
        Member currentMember = memberRepository.findByName(username).orElseThrow(MemberNotFoundException::new);
        return DtoMapper.toMemberDto(currentMember);
    }

    public JwtResponse authenticateMember(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return new JwtResponse(jwt, userDetails.getUsername(), roles);
    }

    public JwtResponse changeUsername(String username, ChangeUsernameRequest request) {
        Member currentMember = memberRepository.findByName(username).orElseThrow(MemberNotFoundException::new);
        currentMember.setName(request.getNewUsername());
        memberRepository.save(currentMember);
        String jwt = jwtUtils.generateNewJwtTokenOnNameOrPasswordChange(currentMember.getName());
        List<String> roles = currentMember.getRoles().stream().map(Role::getName).toList();
        return new JwtResponse(jwt, currentMember.getName(), roles);
    }

    public JwtResponse changeMemberPassword(String username, ChangePasswordRequest request) {
        Member currentMember = memberRepository.findByName(username).orElseThrow(MemberNotFoundException::new);
        currentMember.setPassword(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(currentMember);
        String jwt = jwtUtils.generateNewJwtTokenOnNameOrPasswordChange(currentMember.getName());
        List<String> roles = currentMember.getRoles().stream().map(Role::getName).toList();
        return new JwtResponse(jwt, currentMember.getName(), roles);
    }

    public MemberDto changeMemberEmail(String username, ChangeEmailRequest request) {
        Member currentMember = memberRepository.findByName(username).orElseThrow(MemberNotFoundException::new);
        currentMember.setEmail(request.getNewEmail());
        memberRepository.save(currentMember);
        return DtoMapper.toMemberDto(currentMember);
    }

    public void addAdminRole(UUID memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);
        Role role = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        member.getRoles().add(role);
        memberRepository.save(member);
    }

    public void deleteMe(String username) {
        Member currentMember = memberRepository.findByName(username).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(currentMember);
    }

    public void deleteMemberById(UUID memberId) {
        Member currentMember = memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(currentMember);
    }
}

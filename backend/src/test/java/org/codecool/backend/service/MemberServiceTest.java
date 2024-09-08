package org.codecool.backend.service;

import org.codecool.backend.controller.exception.ExistingUsernameException;
import org.codecool.backend.controller.exception.MemberNotFoundException;
import org.codecool.backend.controller.exception.NoSuchEntityInDBException;
import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.MemberDto;
import org.codecool.backend.model.entity.Member;
import org.codecool.backend.model.entity.Role;
import org.codecool.backend.model.payload.ChangeEmailRequest;
import org.codecool.backend.model.payload.CreateMemberRequest;
import org.codecool.backend.repository.MemberRepository;
import org.codecool.backend.repository.RoleRepository;
import org.codecool.backend.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemberServiceTest {

    private MemberRepository memberRepositoryMock;
    private RoleRepository roleRepositoryMock;
    private PasswordEncoder passwordEncoderMock;
    private AuthenticationManager authenticationManagerMock;
    private JwtUtils jwtUtilsMock;

    @BeforeEach
    public void setup() {
        memberRepositoryMock = Mockito.mock(MemberRepository.class);
        roleRepositoryMock = Mockito.mock(RoleRepository.class);
        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        authenticationManagerMock = Mockito.mock(AuthenticationManager.class);
        jwtUtilsMock = Mockito.mock(JwtUtils.class);
    }

    @Test
    public void testCreateNewMemberIfMemberIsNotInDB() {
        MemberService memberService = new MemberService(memberRepositoryMock, roleRepositoryMock, passwordEncoderMock, authenticationManagerMock, jwtUtilsMock);
        CreateMemberRequest request = new CreateMemberRequest("Eric", "eric@gmail.com", "456");
        Mockito.when(passwordEncoderMock.encode(request.getPassword())).thenReturn("$2a$10$OpnicZ8bxvn76ubI5Zhmz.F/pWU/fnie6Vo3GV93cT1K7mypoVq/.");
        String username = "Eric";
        Role roleToHave = new Role("ROLE_USER");
        Mockito.when(memberRepositoryMock.findByName(username)).thenReturn(Optional.empty());
        Mockito.when(roleRepositoryMock.findByName("ROLE_USER")).thenReturn(Optional.of(roleToHave));

        memberService.createNewMember(request);

        Mockito.verify(roleRepositoryMock, Mockito.times(1)).findByName("ROLE_USER");
    }

    @Test
    public void testCreateNewMemberIfMemberIsInDBAlready() {
        MemberService memberService = new MemberService(memberRepositoryMock, roleRepositoryMock, passwordEncoderMock, authenticationManagerMock, jwtUtilsMock);
        CreateMemberRequest request = new CreateMemberRequest("Eric", "eric@gmail.com", "456");
        Mockito.when(passwordEncoderMock.encode(request.getPassword())).thenReturn("$2a$10$OpnicZ8bxvn76ubI5Zhmz.F/pWU/fnie6Vo3GV93cT1K7mypoVq/.");
        Member newMember = new Member("Eric", "eric@gmail.com", "$2a$10$OpnicZ8bxvn76ubI5Zhmz.F/pWU/fnie6Vo3GV93cT1K7mypoVq/.");
        String username = "Eric";
        Mockito.when(memberRepositoryMock.findByName(username)).thenReturn(Optional.of(newMember));

        assertThrows(ExistingUsernameException.class, () -> memberService.createNewMember(request));
    }

    @Test
    public void testGetCurrentUserInfoIfThereIsUserInDB() {
        MemberService memberService = new MemberService(memberRepositoryMock, roleRepositoryMock, passwordEncoderMock, authenticationManagerMock, jwtUtilsMock);
        Member member = new Member("Eric", "eric@gmail.com", "$2a$10$OpnicZ8bxvn76ubI5Zhmz.F/pWU/fnie6Vo3GV93cT1K7mypoVq/.");
        String username = "Eric";
        Role roleToHave = new Role("ROLE_USER");
        member.addRole(roleToHave);
        Mockito.when(memberRepositoryMock.findByName(username)).thenReturn(Optional.of(member));
        MemberDto expected = new MemberDto(member.getMemberId(), member.getName(), member.getEmail(), DtoMapper.toRoleDtoSet(member.getRoles()));
        MemberDto actual = memberService.getCurrentUserInfo(username);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentUserInfoIfNoSuchMemberInDB() {
        MemberService memberService = new MemberService(memberRepositoryMock, roleRepositoryMock, passwordEncoderMock, authenticationManagerMock, jwtUtilsMock);
        String username = "Eric";
        Mockito.when(memberRepositoryMock.findByName(username)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityInDBException.class, () -> memberService.getCurrentUserInfo(username));
    }

    @Test
    public void testChangeUserEmailIfNoSuchUserInDB() {
        MemberService memberService = new MemberService(memberRepositoryMock, roleRepositoryMock, passwordEncoderMock, authenticationManagerMock, jwtUtilsMock);
        String username = "someone";
        ChangeEmailRequest request = new ChangeEmailRequest("someone@gmail.com");
        Mockito.when(memberRepositoryMock.findByName(username)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityInDBException.class, () -> memberService.changeMemberEmail(username, request));
    }
}

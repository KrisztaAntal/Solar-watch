package org.codecool.backend.security.service;

import org.codecool.backend.model.entity.Member;
import org.codecool.backend.model.entity.Role;
import org.codecool.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (Role role : member.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(member.getName(), member.getPassword(), roles);
    }
}


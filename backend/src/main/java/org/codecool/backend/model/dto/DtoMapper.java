package org.codecool.backend.model.dto;

import org.codecool.backend.model.entity.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DtoMapper {
    public static SunriseDto toSunriseDto(Sunrise sunrise) {
        return new SunriseDto(sunrise.getDate(), sunrise.getTimeOfSunrise(), toCityDto(sunrise.getCity()));
    }

    public static SunsetDto toSunsetDto(Sunset sunset) {
        return new SunsetDto(sunset.getDate(), sunset.getTimeOfSunset(), toCityDto(sunset.getCity()));
    }

    public static CityDto toCityDto(City city) {
        return new CityDto(city.getName(), city.getLatitude(), city.getLongitude(), city.getCountry(), city.getState());
    }

    public static MemberDto toMemberDto(Member member) {
        return new MemberDto(member.getMemberId(), member.getName(), member.getEmail(), toRoleDtoSet(member.getRoles()));
    }

    public static Set<MemberDto> toMemberDtoSet(Set<Member> members) {
        return members.stream().map(DtoMapper::toMemberDto).collect(Collectors.toSet());
    }

    public static Set<RoleDto> toRoleDtoSet(Set<Role> memberRoles) {
        return memberRoles.stream().map(DtoMapper::toRoleDto).collect(Collectors.toSet());
    }

    public static RoleDto toRoleDto(Role role) {
        return new RoleDto(role.getName());
    }
}

package com.example.cascde.querydsl;

import com.example.cascde.dto.MemberDto;
import com.example.cascde.dto.TeamDto;
import com.example.cascde.entity.Member;
import com.example.cascde.entity.Team;
import java.util.List;

public interface MemberRepository {
    Member getMember(Long no);

    MemberDto.Create getMemberByDto(Long no);

    MemberDto.Create getMemberByDtoWihList(Long no);

    Member getMemberWithTeam(Long no);

    List<Team> getmemberFromTeam(Long no);

}

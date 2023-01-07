package com.example.cascde.controller;

import com.example.cascde.dto.MemberDto;
import com.example.cascde.dto.TeamDto;
import com.example.cascde.dto.TeamDto.Create;
import com.example.cascde.entity.Member;
import com.example.cascde.entity.Team;
import com.example.cascde.querydsl.MemberRepository;
import com.example.cascde.repo.TeamRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final MemberRepository memberRepository;
    private final TeamRepo teamRepo;

    //멤버로 팀까지 불러오기
    @GetMapping("/get/{no}")
    public Member get(
        @PathVariable Long no
    ){

        //양방향,단반향 연습
        MemberDto.Create memberByDtoWihList = memberRepository.getMemberByDtoWihList(no);// member만

        //team to member 양방향이여야 호출 가능
        //List<Team> teams = memberRepository.getmemberFromTeam(no);

        return null;

    }


    @PostMapping("/test")
    public void create(@RequestBody TeamDto.Create createDto){

        Team team = Create.toEntity(createDto);

        //TODO 이 로직밖에 없는건가 방법이?
        List<Member> members = team.getMembers();
        for (Member member : members) {
            //team.addMember(member);
            member.setTeam(team);
        }

        teamRepo.save(team);
    }

    @DeleteMapping("/remove/{no}")
    public void deleteTest(@PathVariable Long no){
        teamRepo.deleteById(no);
    }
}

package com.example.cascde.querydsl.impl;

import static com.example.cascde.entity.QMember.member;
import static com.example.cascde.entity.QTeam.team;

import com.example.cascde.dto.MemberDto;
import com.example.cascde.entity.Member;
import com.example.cascde.entity.Team;
import com.example.cascde.querydsl.MemberRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //멤버만 불러보기
    //Lazy 아니면 이렇게 해도 team까지 다불러옴
    @Override
    public Member getMember(Long no) {
        return queryFactory
            .select(member)
            .from(member)
            .where(member.id.eq(no))
            .fetchOne();
    }


    //TODO Projections 을 사용해 DTO 반환시 위에처럼 객체타입 가지고는 안돼고 하나하나 입력해야됌
    /*@Override
    public MemberDto.Create getMemberByDto(Long no) {
        return queryFactory
            .select(Projections.bean(MemberDto.Create.class,
                member
            ))
            .from(member)
            .where(member.id.eq(no))
            .fetchOne();
    }*/
    @Override
    public MemberDto.Create getMemberByDto(Long no) {
        return queryFactory
            .select(Projections.bean(MemberDto.Create.class,
                member.name,
                member.age
            ))
            .from(member)
            .where(member.id.eq(no))
            .fetchOne();
    }

    //DTO 필드 타입에 정확히 맞아떨어져야함
    //name, age 라고 member로 select하면 안됌
    @Override
    public MemberDto.Create getMemberByDtoWihList(Long no) {
        return queryFactory
            .select(Projections.bean(MemberDto.Create.class,
                member.name,
                member.age,
                team
            ))
            .from(member)
            .where(member.id.eq(no))
            .fetchOne();
    }

    @Override
    public Member getMemberWithTeam(Long no) {
        return queryFactory
            .select(member)
            .from(member)
            .leftJoin(team).on(member.team.id.eq(team.id))
            .where(member.id.eq(no))
            .fetchOne();
    }

    @Override
    public List<Team> getmemberFromTeam(Long no) {
        return queryFactory
            .select(team)
            .from(team)
            .where(team.id.eq(no))
            .fetch();
    }

}

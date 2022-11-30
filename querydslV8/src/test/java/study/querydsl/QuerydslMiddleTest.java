package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

import static study.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslMiddleTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @PersistenceUnit
    EntityManagerFactory emf;


    /**
     * setterProjection
     */
        List<MemberDto> setterProjection = queryFactory
                .select(Projections.bean(MemberDto.class,
                        member.username,
                        member.age))
            .from(member)
            .fetch();

    /**
     * fieldProjection
     */
        List<MemberDto> fieldProjection = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.username,
                        member.age))
            .from(member)
            .fetch();


    /**
     * constructorProjection
     */
        List<MemberDto> constructorProjection = queryFactory
                .select(Projections.constructor(MemberDto.class,
                member.username,
                member.age))
            .from(member)
            .fetch();

    /**
     * @QueryProjection
     */
    List<MemberDto> result = queryFactory
            .select(new QMemberDto(member.username, member.age))
            .from(member)
            .fetch();

    @Test
    public void BooleanBuilder() throws Exception {


        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMemberV1(usernameParam, ageParam);

        assertThat(result.size()).isEqualTo(1);
    }

        private List<Member> searchMemberV1(String usernameCond, Integer ageCond) {

            BooleanBuilder builder = new BooleanBuilder();

            if (usernameCond != null) {
                builder.and(member.username.eq(usernameCond));
            }

            if (ageCond != null) {
                builder.and(member.age.eq(ageCond));
            }

            return queryFactory
                    .selectFrom(member)
                    .where(builder)
                    .fetch();
        }

    @Test
    public void WhereParam() throws Exception { String usernameParam = "member1";
        Integer ageParam = 10;
        List<Member> result = searchMember2(usernameParam, ageParam);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;

    }
    //SQL function 호출하기
    //member -> M 으로 변경하는 replace함수 사용
    private void sqlFunction(){
   String resultV2 = queryFactory
            .select(Expressions.stringTemplate("function('replace', {0}, {1},{2})",
                    member.username, "member", "M"))
            .from(member)
            .fetchFirst();
    }

    //소문자로 변경해서 비교
    @Test
    private void sqlFunctionV2(){
        queryFactory
                .select(member.username)
                .from(member)
                //.where(member.username.eq(Expressions.stringTemplate("function('lower', {0})", member.username)));
                .where(member.username.eq(member.username.lower()));
    }



    }


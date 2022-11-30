package study.querydsl.dto;


import lombok.Data;

//검색조건 dto로 한번에 받기
@Data
public class MemberSearchCondition {
    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
}

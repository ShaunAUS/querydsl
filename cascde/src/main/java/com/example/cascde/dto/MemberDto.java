package com.example.cascde.dto;

import com.example.cascde.entity.Member;
import com.example.cascde.entity.Team;
import com.example.cascde.utils.MapperUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
public class MemberDto {
    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        private String name;

        private Integer age;

        private Team team;

        public static Member toMemberEntity(MemberDto.Create createMember) {

            return MapperUtils.getMapper()
                .typeMap(MemberDto.Create.class, Member.class)
                .map(createMember);
        }
    }

}

package com.example.cascde.dto;

import com.example.cascde.entity.Member;
import com.example.cascde.entity.Team;
import com.example.cascde.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.Converter;

@RequiredArgsConstructor
public class TeamDto {



    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        private String name;

        private List<MemberDto.Create> members;

        public static Team toEntity(TeamDto.Create create) {
            return MapperUtils.getMapper()
                .typeMap(TeamDto.Create.class, Team.class)
                .addMappings(mapper->{
                    mapper.using(CAREER_LIST_CONVERTER)
                        .map(TeamDto.Create::getMembers,Team::setMembers);
                })
                .map(create);
        }


        public static final Converter<List<MemberDto.Create>, List<Member>> CAREER_LIST_CONVERTER =
            context -> context.getSource() == null ? null : context.getSource().stream()
                .map(MemberDto.Create::toMemberEntity)
                .collect(Collectors.toList());
    }

}

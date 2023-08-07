package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.model.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberDTOToMember(MemberDTO memberDTO);

    MemberDTO memberToMemberDTO(Member member);

    List<MemberDTO> toDtoList(List<Member> members);
}

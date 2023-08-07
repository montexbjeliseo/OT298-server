package com.alkemy.ong.dto;

import com.alkemy.ong.model.Member;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberPaginationDTO {
    private List<Member> members;
    private Map<String, String> links;
}

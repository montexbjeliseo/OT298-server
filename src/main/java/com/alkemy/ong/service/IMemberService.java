package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberPaginationDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IMemberService {

    MemberDTO save(MemberDTO dto);
    
    List<MemberDTO> getAll();

    MemberDTO update(Long id,MemberDTO dto);
    
    MemberDTO delete(Long id);

    MemberPaginationDTO getPaginated(Pageable pageable,
                                     HttpServletRequest request,
                                     UriComponentsBuilder uriBuilder);
}

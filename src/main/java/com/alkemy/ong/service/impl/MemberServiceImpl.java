package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberPaginationDTO;
import com.alkemy.ong.exception.InvalidPaginationParamsException;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.exception.EntityNotSavedException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;
import com.alkemy.ong.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

import java.util.Locale;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MessageSource message;

    @Transactional
    public MemberDTO save(MemberDTO dto) {
        Member member = memberMapper.memberDTOToMember(dto);
        Member savedMember = memberRepository.save(member);
        return memberMapper.memberToMemberDTO(savedMember);
    }
    public List<MemberDTO> getAll(){
        List<Member> list = memberRepository.findAll();
        if (list.isEmpty()){
            throw new ResourceNotFoundException(message.getMessage("member.empty_list", null, Locale.US));
        }
        return memberMapper.toDtoList(list);
    }

    @Override
    public MemberDTO update(Long id, MemberDTO dto) {
            Member member = getMemberEntityById(id);
        try {
            verifiedNullData(dto, member);
            memberRepository.save(member);
        } catch (EntityNotSavedException ense) {
            throw new EntityNotSavedException(message.getMessage("member.notSaved", null, Locale.US));
        }
        return memberMapper.memberToMemberDTO(member);
    }

    private static void verifiedNullData(MemberDTO dto, Member member) {
        if(!StringUtils.hasLength(dto.getName())){
            member.setName(member.getName());
        }else{
            member.setName(dto.getName());
        }

        if(!StringUtils.hasLength(dto.getImage())){
            member.setImage(member.getImage());
        }else{
            member.setImage(dto.getImage());
        }

        if(!StringUtils.hasLength(dto.getDescription())){
            member.setDescription(member.getDescription());
        }else{
            member.setDescription(dto.getDescription());
        }

        if(!StringUtils.hasLength(dto.getFacebookUrl())){
            member.setFacebookUrl(member.getFacebookUrl());
        }else{
            member.setFacebookUrl(dto.getFacebookUrl());
        }

        if(!StringUtils.hasLength(dto.getInstagramUrl())){
            member.setInstagramUrl(member.getInstagramUrl());
        }else{
            member.setInstagramUrl(dto.getInstagramUrl());
        }

        if(!StringUtils.hasLength(dto.getLinkedinUrl())){
            member.setLinkedinUrl(member.getLinkedinUrl());
        }else{
            member.setLinkedinUrl(dto.getLinkedinUrl());
        }
    }

    public MemberDTO delete(Long id){
        if (!memberRepository.existsById(id)){
            throw new ResourceNotFoundException(message.getMessage("member.notFound", null, Locale.US));
        }
        Member member = memberRepository.getById(id);
        memberRepository.deleteById(id);
        return memberMapper.memberToMemberDTO(member);
    }
    
    private Member getMemberEntityById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (!member.isPresent()) {
            throw new EntityNotFoundException(message.getMessage("member.notFound", null, Locale.US));
        }
        return member.get();
    }

    @Override
    public MemberPaginationDTO getPaginated(Pageable pageable,
                                            HttpServletRequest request,
                                            UriComponentsBuilder uriBuilder) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Page<Member> resultPage = memberRepository.findAll(pageable);
        int totalPages = resultPage.getTotalPages();

        if (pageNumber < 0 || pageNumber >= totalPages) {
            throw new InvalidPaginationParamsException(message.getMessage("pagination.invalidArgs", null, Locale.US));
        }
        uriBuilder.path(request.getRequestURI());
        Map<String, String> links = new LinkedHashMap<>();

        if (resultPage.hasPrevious()) {
            links.put(message.getMessage("pagination.previous", null, Locale.US),
                    Pagination.constructPreviousPageUri(uriBuilder, pageNumber, pageSize));
        }
        if (resultPage.hasNext()) {
            links.put(message.getMessage("pagination.next", null, Locale.US),
                    Pagination.constructNextPageUri(uriBuilder, pageNumber, pageSize));
        }
        return new MemberPaginationDTO(resultPage.getContent(), links);
    }
}

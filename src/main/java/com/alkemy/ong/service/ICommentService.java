package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.CommentGetDto;
import com.alkemy.ong.dto.CommentPostDto;

import org.springframework.security.core.Authentication;


import java.util.List;

public interface ICommentService {

    CommentBasicDTO save(CommentPostDto commentDto, Authentication authentication);

    List<CommentBasicDTO> getAllComments();


    void delete(Long id, Authentication authentication) ;

    CommentGetDto updateComment(Long id, CommentBasicDTO dto);

}

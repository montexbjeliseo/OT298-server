package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.*;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.security.model.User;
import com.alkemy.ong.security.service.IUserService;
import com.alkemy.ong.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.*;

import static com.alkemy.ong.util.Constants.ROLE_ADMIN;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private IUserService userService;

    @Override
    public CommentDto save(CommentDto commentDto) {
        try {
            Comment commentEntity = commentMapper.commentDtoToEntity(commentDto);
            Comment savedEntity = commentRepository.save(commentEntity);
            return commentMapper.commentEntityToDto(savedEntity);
        } catch (EntityNotSavedException ense) {
            throw new EntityNotSavedException(messageSource.getMessage("comment.notAdded", null, Locale.US));
        }
    }

    @Override
    public List<CommentBasicDTO> getAllComments() {
        List<Comment> comments = commentRepository.getAllByOrderByCreationDateDesc();
        if (!comments.isEmpty()){
            List<CommentBasicDTO> dtoList = new ArrayList<>();
            for(Comment comment : comments){
                dtoList.add(commentMapper.commentBodyToCommentBasicDTO(comment));
            }
            return dtoList;
        } else {
            throw new ResourceNotFoundException(messageSource.getMessage("comments.notFound", null, Locale.US));
        }
    }





    @Override
    public void delete(Long id,Authentication authentication)  {

        Comment comment = getCommentEntityById(id);

        if ((authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals(ROLE_ADMIN)))) {

            commentRepository.deleteById(id);

        }else if (!CommentDeletedByUser(id, authentication, comment)) {


            throw new NotOriginalUserException(messageSource.getMessage("comment.notDeleted", null, Locale.US));

        }

    }

    private boolean CommentDeletedByUser(Long id, Authentication authentication, Comment comment) {
        User user= userService.getUserAuthenticated(authentication);


        if(comment.getUser().equals(user) ) {
            commentRepository.deleteById(id);

            return true;

        }
        return false;
    }

    private Comment getCommentEntityById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new EntityNotFoundException(messageSource.getMessage("comment.notFound", null, Locale.US));
        }

        return comment.get();
    }



}

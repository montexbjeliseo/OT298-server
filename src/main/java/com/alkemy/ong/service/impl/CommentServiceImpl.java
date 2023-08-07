package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.CommentGetDto;
import com.alkemy.ong.dto.CommentPostDto;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static com.alkemy.ong.util.Constants.ROLE_ADMIN;

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private MessageSource message;

    @Autowired
    private IUserService userService;

    @Override
    public  CommentBasicDTO save(CommentPostDto commentDto,Authentication authentication) {

        User user = userService.getUserAuthenticated(authentication);

        try {
            Comment commentEntity = commentMapper.commentDtoToEntity(commentDto,authentication);
            Comment savedEntity = commentRepository.save(commentEntity);
            CommentBasicDTO dto=commentMapper.commentBodyToCommentBasicDTO(savedEntity);
            return dto;
        } catch (EntityNotSavedException ense) {
            throw new EntityNotSavedException(message.getMessage("comment.notAdded", null, Locale.US));
        }
    }

    @Override
    public List<CommentBasicDTO> getAllComments() {
        List<Comment> comments = commentRepository.getAllByOrderByCreationDateDesc();
        if (!comments.isEmpty()) {
            List<CommentBasicDTO> dtoList = new ArrayList<>();
            for (Comment comment : comments) {
                dtoList.add(commentMapper.commentBodyToCommentBasicDTO(comment));
            }
            return dtoList;
        } else {
            throw new ResourceNotFoundException(message.getMessage("comments.notFound", null, Locale.US));
        }
    }

    @Override
    public CommentGetDto updateComment(Long commentId, CommentBasicDTO dto) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException(message.getMessage("comment.notFound", null, Locale.US));
        }
        if (dto.getBody() == null) {
            throw new EntityNullException(message.getMessage("request.body", null, Locale.US));
        }
        Comment comment = commentRepository.getById(commentId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (comment.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN))) {
            Comment updatedComment = commentMapper.updateCommentBody(dto, comment);
            Comment savedComment = commentRepository.save(updatedComment);
            return commentMapper.commentEntityToDto(savedComment);
        } else {
            throw new PermissionDeniedException(message.getMessage("permissionDenied.comment", null, Locale.US));
        }
    }

    @Override
    public void delete(Long id, Authentication authentication) {

        Comment comment = getCommentEntityById(id);

        if ((authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                .equals(ROLE_ADMIN)))) {

            commentRepository.deleteById(id);

        } else if (!commentDeletedByUser(id, authentication, comment)) {

            throw new NotOriginalUserException(message.getMessage("comment.notDeleted", null, Locale.US));

        }

    }

    private boolean commentDeletedByUser(Long id, Authentication authentication, Comment comment) {
        User user = userService.getUserAuthenticated(authentication);

        if (comment.getUser().equals(user)) {
            commentRepository.deleteById(id);

            return true;

        }
        return false;
    }

    private Comment getCommentEntityById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new EntityNotFoundException(message.getMessage("comment.notFound", null, Locale.US));
        }

        return comment.get();
    }

}

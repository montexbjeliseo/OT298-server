package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.CommentGetDto;
import com.alkemy.ong.dto.CommentPostDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.security.model.User;
import com.alkemy.ong.security.repository.UserRepository;
import com.alkemy.ong.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private MessageSource message;

    public CommentGetDto commentEntityToDto(Comment comment) {

        CommentGetDto commentDto = new CommentGetDto();
        commentDto.setId(comment.getId());
        commentDto.setBody( comment.getBody() );
        commentDto.setNewsId(comment.getNews().getId() );

        return commentDto;
    }

    public Comment commentDtoToEntity(CommentPostDto commentDto, Authentication authentication) {


        User user = userService.getUserAuthenticated(authentication);


        News news = newsRepository.findById(commentDto.getNewsId()).orElseThrow(()-> new UsernameNotFoundException(message.getMessage("news.notFound",null, Locale.US)));

        Comment comment = new Comment();

        comment.setBody( commentDto.getBody() );
        comment.setUser( user );
        comment.setNews( news );
        news.getComments().add(comment);


        return comment;
    }

    public CommentBasicDTO commentBodyToCommentBasicDTO(Comment comment){
        CommentBasicDTO dto = new CommentBasicDTO();
        dto.setBody(comment.getBody());
        return dto;
    }

    public List<CommentBasicDTO> listCommentsToListDtos(List<Comment> comments){
        List<CommentBasicDTO> commentsDtos =
                comments.stream().map(comment -> commentBodyToCommentBasicDTO(comment)).collect(Collectors.toList());
        return commentsDtos;
    }

    public Comment updateCommentBody (CommentBasicDTO dto, Comment comment){
        if (dto.getBody() != null){
            comment.setBody(dto.getBody());
        }
        return comment;
    }
}

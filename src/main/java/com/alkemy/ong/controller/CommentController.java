package com.alkemy.ong.controller;

import com.alkemy.ong.documentation.ICommentController;
import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.CommentGetDto;
import com.alkemy.ong.dto.CommentPostDto;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController implements ICommentController {

    @Autowired
    private ICommentService commentService;

    @PostMapping
    public ResponseEntity<CommentBasicDTO> newComment(@Valid @RequestBody CommentPostDto commentDto, Authentication authentication) throws ResourceNotFoundException {
        CommentBasicDTO commentDtoSaved = commentService.save(commentDto,authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoSaved);
    }

    @GetMapping
    public ResponseEntity<List<CommentBasicDTO>> getAllComments (){
        List<CommentBasicDTO> comments = commentService.getAllComments();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(comments);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id, Authentication authentication)  {
        commentService.delete(id,authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PatchMapping ("/{id}")
    public ResponseEntity<CommentGetDto> updateComment (@PathVariable Long id, @RequestBody CommentBasicDTO dto){
        CommentGetDto updatedComment = commentService.updateComment(id, dto);
        return ResponseEntity.accepted().body(updatedComment);

    }
}

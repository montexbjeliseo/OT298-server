package com.alkemy.ong.controller;

import com.alkemy.ong.documentation.IMemberController;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberPaginationDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static com.alkemy.ong.util.Constants.Endpoints.*;
import static com.alkemy.ong.util.Pagination.INITIAL_PAGE;
import static com.alkemy.ong.util.Pagination.PAGE_SIZE;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(MEMBER)
public class MemberController implements IMemberController {

    @Autowired
    private IMemberService memberService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<MemberDTO> save(@Valid @RequestBody MemberDTO dto){
        MemberDTO savedMember = memberService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(memberService.getAll(), HttpStatus.OK);
    }

    @PutMapping(value = ID, produces = "application/json", consumes = "application/json")
    public ResponseEntity<MemberDTO> update(@Valid @PathVariable("id") Long id, @RequestBody MemberDTO dto) {
        MemberDTO updatedMemberDTO = memberService.update(id,dto);
        return ResponseEntity.ok().body(updatedMemberDTO);
    }
    @DeleteMapping(value = ID)
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException{
        return new ResponseEntity<>(memberService.delete(id), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<MemberPaginationDTO> getPaginated(
            @Valid @PageableDefault(page = INITIAL_PAGE, size = PAGE_SIZE) Pageable pageable,
            HttpServletRequest request,
            UriComponentsBuilder uriBuilder) {
        return ResponseEntity.ok(memberService.getPaginated(pageable, request, uriBuilder));
    }
}

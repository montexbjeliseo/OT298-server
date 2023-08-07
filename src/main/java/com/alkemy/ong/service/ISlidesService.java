package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlidesDTO;

import java.util.List;
import java.util.LinkedList;

public interface ISlidesService {
    SlidesDTO delete(Long id);

    SlidesDTO save(SlidesDTO slidesDTO) throws Exception;

    LinkedList<SlidesDTO> listSlides(LinkedList<SlidesDTO> slidesDTOlist);

    SlidesDTO getById(Long id);

    List<SlidesDTO> getAllSlides();

    SlidesDTO update(Long id, SlidesDTO slides);
}

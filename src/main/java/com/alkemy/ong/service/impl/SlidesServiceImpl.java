package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlidesDTO;
import com.alkemy.ong.exception.ThereAreNoSlides;
import com.alkemy.ong.dto.MediaBasicDTO;
import com.alkemy.ong.mapper.SlidesMapper;
import com.alkemy.ong.model.Slides;
import com.alkemy.ong.repository.SlidesRepository;
import com.alkemy.ong.service.ISlidesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.alkemy.ong.exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class SlidesServiceImpl implements ISlidesService {

    @Autowired
    private SlidesRepository slidesRepository;
    @Autowired
    private SlidesMapper slidesMapper;
    @Autowired
    private AmazonS3ServiceImpl amazonS3Service;
    @Autowired
    private MediaBasicDTO mediaBasicDTO;
    @Autowired
    private MessageSource message;

    public List<SlidesDTO> getAllSlides() {
        List<Slides> slides = slidesRepository.findAll();
        if (slides.isEmpty()) {
            throw new ThereAreNoSlides("{slides.empty}");
        }
        List<SlidesDTO> dtos = slidesMapper.listSlidesToDtos(slides);
        return dtos;
    }

    @Override
    public SlidesDTO save(SlidesDTO slidesDTO) throws Exception {

        Slides slides = slidesMapper.slidesDtoToSlides(slidesDTO);

        slides.setImage(amazonS3Service.uploadFileBase64(slides.getImage(), slides.getText()));

        Slides slideSave = slidesRepository.save(slides);

        return slidesMapper.slidesToSlidesDto(slideSave);

    }

    public void listOrderPosition(Slides slides) {
        LinkedList<Slides> slidesList = new LinkedList<>();
        for (Slides slide : slidesList) {
            if (slides.getPosition() == null) {
                slidesList.addLast(slide);
            }
        }
    }

    @Override
    public LinkedList<SlidesDTO> listSlides(LinkedList<SlidesDTO> slidesDTOList) {
        LinkedList<Slides> slidesList = (LinkedList<Slides>) slidesRepository.findAll();
        for (SlidesDTO dto : slidesDTOList) {
            if (dto.getPosition() == null) {
                slidesList.addLast(slidesMapper.slidesDtoToSlides(dto));
            }
        }
        return slidesMapper.listSlide2listSlideDTO(slidesList);

    }

    @Override
    public SlidesDTO getById(Long id) {
        Optional slides = slidesRepository.findById(id);
        if (!slides.isPresent()) {
            throw new ResourceNotFoundException(message.getMessage("slides.notFound", null, Locale.US));
        }
        return slidesMapper.slidesToSlidesDto((Slides) slides.get());
    }

    @Override
    public SlidesDTO delete(Long id) {
        if (!slidesRepository.existsById(id)) {
            throw new ResourceNotFoundException(message.getMessage("id.invalid", null, Locale.US));
        }
        SlidesDTO dto = slidesMapper.slidesToSlidesDto(slidesRepository.findById(id).get());

        slidesRepository.deleteById(id);

        return dto;
    }

    @Override
    public SlidesDTO update(Long id, SlidesDTO slides) {

        Optional currentSlides = slidesRepository.findById(id);
        if (!currentSlides.isPresent()) {
            throw new ResourceNotFoundException(message.getMessage("slides.notFound", null, Locale.US));
        }

        Slides slidesToUpdate = slidesMapper.updateSlidesFromSlidesDto(slides, (Slides) currentSlides.get());
        
        return slidesMapper.slidesToSlidesDto(slidesRepository.save(slidesToUpdate));

    }
}


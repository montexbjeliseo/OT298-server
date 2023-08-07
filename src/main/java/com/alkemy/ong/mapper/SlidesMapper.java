package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.SlidesDTO;
import com.alkemy.ong.model.Slides;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.LinkedList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SlidesMapper {


    Slides slidesDtoToSlides(SlidesDTO slidesDto);

    SlidesDTO slidesToSlidesDto(Slides slides);

    List<SlidesDTO> listSlidesToDtos(List<Slides> slides);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Slides updateSlidesFromSlidesDto(SlidesDTO slidesDto, @MappingTarget Slides slides);
    LinkedList<SlidesDTO> listSlide2listSlideDTO(LinkedList<Slides> slidesList);

}

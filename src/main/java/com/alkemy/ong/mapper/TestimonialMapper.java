package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.TestimonialBasicDTO;
import org.mapstruct.*;

import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.dto.TestimonialDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestimonialMapper {
    TestimonialDTO toDto(Testimonial entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Testimonial toEntity(TestimonialDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Testimonial updateTestimonialFromDto(TestimonialBasicDTO dto, @MappingTarget Testimonial testimonial);

    List<TestimonialDTO> listTestimonialsToListDtos (List<Testimonial> testimonials);
}
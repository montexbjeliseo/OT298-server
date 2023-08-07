package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ActivityBasicDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.model.Activity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    ActivityDTO activityToActivityDTO (Activity entity);

    Activity activityDTOToActivity (ActivityDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Activity updateActivityToDTO(ActivityBasicDTO dto, @MappingTarget Activity activity);
}

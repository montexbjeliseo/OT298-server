package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.OrganizationBasicDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.model.Organization;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    OrganizationBasicDTO organizationToOrganizationBasicDTO(Organization entity);

    OrganizationUpdateDTO organizationToUpdateDTO(Organization organization);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Organization updateDTOToOrganization(OrganizationUpdateDTO dto, @MappingTarget Organization organization);

}

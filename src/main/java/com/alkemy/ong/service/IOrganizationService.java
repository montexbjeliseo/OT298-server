package com.alkemy.ong.service;

import com.alkemy.ong.dto.OrganizationBasicDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;

public interface IOrganizationService {
    OrganizationBasicDTO getOrganizationBasic();
    OrganizationUpdateDTO updateOrganization(Long id, OrganizationUpdateDTO updateDTO);

}

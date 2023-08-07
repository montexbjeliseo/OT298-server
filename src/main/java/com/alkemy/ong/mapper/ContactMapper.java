package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.model.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactDTO contactToContactDTO (Contact entity);

    Contact contactDTOToContact (ContactDTO dto);
}

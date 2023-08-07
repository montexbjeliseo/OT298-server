package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.IContactService;
import com.alkemy.ong.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired

    private IEmailService emailService;

    @Autowired

    private MessageSource message;

    @Transactional
    @Override
    public ContactDTO save (ContactDTO dto) throws IOException{
        Contact contact = contactMapper.contactDTOToContact(dto);
        Contact savedContact = contactRepository.save(contact);
        try{
            emailService.sendThanksContactEmail(contact.getEmail());
        }catch (IOException ioException){
            throw new IOException(message.getMessage("email.wasNotSend",null,Locale.US));
        }
        ContactDTO savedDto =contactMapper.contactToContactDTO(savedContact);
        return savedDto;
    }

    public List<ContactDTO> getAll (){
        List<Contact> contactList = contactRepository.findAll();
        if (!contactList.isEmpty()){
            List<ContactDTO> contactDTOList = new ArrayList<>();
            for (Contact contact : contactList){
                contactDTOList.add(this.contactMapper.contactToContactDTO(contact));
            }
            return contactDTOList;
        } else {
            throw new ResourceNotFoundException(message.getMessage("{contacts.notFound}",null, Locale.US));
        }
    }

    public void deleteById (Long id){
        contactRepository.deleteById(id);
    }
}

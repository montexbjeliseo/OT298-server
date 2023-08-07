package com.alkemy.ong.repository;

import com.alkemy.ong.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Contact save (Contact entity);

    List<Contact> findAll ();
}

package com.alkemy.ong.security.repository;

import com.alkemy.ong.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail (String email);


}

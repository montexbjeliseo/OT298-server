package com.alkemy.ong.security.repository;



import com.alkemy.ong.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   Role findByName (String name);
}

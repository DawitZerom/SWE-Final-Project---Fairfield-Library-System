package edu.miu.cs.cs425.fairfieldlibrarywebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}

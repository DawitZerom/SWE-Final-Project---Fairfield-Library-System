package edu.miu.cs.cs425.fairfieldlibrarywebapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.LibraryMember;

@Repository
public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Integer> {
    List<LibraryMember> findAllByFirstnameContainingOrLastnameContainingOrPhoneContaining(String firstname,
            String lastname, String phone);

    Optional<LibraryMember> findByMemberNumber(String memberNumber);
}

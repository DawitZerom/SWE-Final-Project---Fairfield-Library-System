package edu.miu.cs.cs425.fairfieldlibrarywebapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.LibraryMemberType;

@Repository
public interface LibraryMemberTypeRepository extends JpaRepository<LibraryMemberType, Integer> {
    List<LibraryMemberType> findAllByNameContainingOrMaxLengthBorrowBookContainingOrOverdueFee(String name,
            String maxLengthBorrowBook, String overdueFee);

    List<LibraryMemberType> findByNameContaining(String name);
}

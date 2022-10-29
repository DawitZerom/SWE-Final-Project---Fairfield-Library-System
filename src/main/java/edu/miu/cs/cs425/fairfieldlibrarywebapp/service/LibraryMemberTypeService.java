package edu.miu.cs.cs425.fairfieldlibrarywebapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.LibraryMemberType;

public interface LibraryMemberTypeService {
    List<LibraryMemberType> getLibraryMemberTypes();

    Page<LibraryMemberType> getLibraryMemberTypesPaged(int pageNo);

    LibraryMemberType findLibraryMemberTypeById(Integer libraryMemberTypeId);

    LibraryMemberType saveNewLibraryMemberType(LibraryMemberType libraryMemberType);

    LibraryMemberType updateLibraryMemberType(LibraryMemberType libraryMemberType);

    void deleteLibraryMemberType(Integer libraryMemberTypeId);

    List<LibraryMemberType> searchLibraryMemberTypes(String searchString);
}

package edu.miu.cs.cs425.fairfieldlibrarywebapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.LibraryMemberType;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.repository.LibraryMemberTypeRepository;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.LibraryMemberTypeService;

@Service
public class LibraryMemberTypeServiceImpl implements LibraryMemberTypeService {
    @Autowired
    private LibraryMemberTypeRepository libraryMemberTypeRepository;

    @Override
    public List<LibraryMemberType> getLibraryMemberTypes() {
        return libraryMemberTypeRepository.findAll();
    }

    @Override
    public Page<LibraryMemberType> getLibraryMemberTypesPaged(int pageNo) {
        return libraryMemberTypeRepository.findAll(PageRequest.of(pageNo, 10, Direction.ASC, "name"));
    }

    @Override
    public LibraryMemberType findLibraryMemberTypeById(Integer libraryMemberTypeId) {
        return libraryMemberTypeRepository.findById(libraryMemberTypeId).orElse(null);
    }

    @Override
    public LibraryMemberType saveNewLibraryMemberType(LibraryMemberType libraryMemberType) {
        return libraryMemberTypeRepository.save(libraryMemberType);
    }

    @Override
    public LibraryMemberType updateLibraryMemberType(LibraryMemberType libraryMemberType) {
        return libraryMemberTypeRepository.save(libraryMemberType);
    }

    @Override
    public void deleteLibraryMemberType(Integer libraryMemberTypeId) {
        libraryMemberTypeRepository.deleteById(libraryMemberTypeId);
    }

    @Override
    public List<LibraryMemberType> searchLibraryMemberTypes(String searchString) {
        // return
        // libraryMemberTypeRepository.findAllByNameContainingOrMaxLengthBorrowBookContainingOrOverdueFee(
        // searchString, searchString, searchString);
        return libraryMemberTypeRepository.findByNameContaining(searchString);
    }

}

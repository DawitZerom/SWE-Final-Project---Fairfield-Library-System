package edu.miu.cs.cs425.fairfieldlibrarywebapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.LibraryMember;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.repository.LibraryMemberRepository;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.LibraryMemberService;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    @Autowired
    private LibraryMemberRepository libraryMemberRepository;

    @Override
    public List<LibraryMember> getLibraryMembers() {
        return libraryMemberRepository.findAll();
    }

    @Override
    public Page<LibraryMember> getLibraryMembersPaged(int pageNo) {
        return libraryMemberRepository.findAll(PageRequest.of(pageNo, 2, Direction.ASC, "firstname"));
    }

    @Override
    public LibraryMember findLibraryMemberById(Integer libraryMemberId) {
        return libraryMemberRepository.findById(libraryMemberId).orElse(null);
        // return libraryMemberRepository.findById(libraryMemberId)
        // .orElseThrow(() -> new CustomNotFoundException(
        // String.format("Member with Id: %d is not found", libraryMemberId)));
    }

    @Override
    public LibraryMember saveNewLibraryMember(LibraryMember libraryMember) {
        return libraryMemberRepository.save(libraryMember);
    }

    @Override
    public LibraryMember updateLibraryMember(LibraryMember libraryMember) {
        return libraryMemberRepository.save(libraryMember);
    }

    @Override
    public void deleteLibraryMember(Integer libraryMemberId) {
        libraryMemberRepository.deleteById(libraryMemberId);
    }

    @Override
    public List<LibraryMember> searchLibraryMembers(String searchString) {
        return libraryMemberRepository.findAllByFirstnameContainingOrLastnameContainingOrPhoneContaining(searchString,
                searchString, searchString);
    }

    @Override
    public LibraryMember findLibraryMemberByMemberNumber(String memberNumber) {
        return libraryMemberRepository.findByMemberNumber(memberNumber).orElse(null);
        // return libraryMemberRepository.findByMemberNumber(memberNumber)
        // .orElseThrow(() -> new CustomNotFoundException(
        // String.format("Member with memberNumber: %s is not found", memberNumber)));
    }

}

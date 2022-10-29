package edu.miu.cs.cs425.fairfieldlibrarywebapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.LibraryMember;

public interface LibraryMemberService {
    List<LibraryMember> getLibraryMembers();

    Page<LibraryMember> getLibraryMembersPaged(int pageNo);

    LibraryMember findLibraryMemberById(Integer libraryMemberId);

    LibraryMember findLibraryMemberByMemberNumber(String memberNumber);

    LibraryMember saveNewLibraryMember(LibraryMember libraryMember);

    LibraryMember updateLibraryMember(LibraryMember libraryMember);

    void deleteLibraryMember(Integer libraryMemberId);

    List<LibraryMember> searchLibraryMembers(String searchString);
}

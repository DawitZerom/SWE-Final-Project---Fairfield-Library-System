package edu.miu.cs.cs425.fairfieldlibrarywebapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByTitleContainingOrIsbnContainingOrAuthorContaining(String title, String isbn, String author);

    Optional<Book> findByIsbn(String isbn);
}

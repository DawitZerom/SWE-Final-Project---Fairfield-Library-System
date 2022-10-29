package edu.miu.cs.cs425.fairfieldlibrarywebapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.Book;

public interface BookService {
    List<Book> getBooks();

    Page<Book> getBooksPaged(int pageNo);

    Book findBookById(Integer bookId);

    Book findBookByISBN(String isbn);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBook(Integer bookid);

    List<Book> searchBooks(String searchString);
}

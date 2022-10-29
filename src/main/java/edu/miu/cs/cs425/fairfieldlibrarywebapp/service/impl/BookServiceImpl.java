package edu.miu.cs.cs425.fairfieldlibrarywebapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.Book;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.repository.BookRepository;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.BookService;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
        // return bookRepository.findById(bookId)
        // .orElseThrow(() -> new CustomNotFoundException(String.format("Book with Id:
        // %d is not found", bookId)));
    }

    @Override
    public Book saveNewBook(Book book) {
        book.setAvailableCopy(book.getTotalCopy());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        book.setAvailableCopy(book.getTotalCopy());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Integer bookid) {
        bookRepository.deleteById(bookid);
    }

    @Override
    public Page<Book> getBooksPaged(int pageNo) {
        return bookRepository.findAll(PageRequest.of(pageNo, 2, Direction.ASC, "title"));
    }

    @Override
    public List<Book> searchBooks(String searchString) {
        return bookRepository.findAllByTitleContainingOrIsbnContainingOrAuthorContaining(searchString, searchString,
                searchString);
    }

    @Override
    public Book findBookByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn).orElse(null);
        // return bookRepository.findByIsbn(isbn)
        // .orElseThrow(() -> new CustomNotFoundException(String.format("Book with ISBN:
        // %s is not found", isbn)));
    }
}

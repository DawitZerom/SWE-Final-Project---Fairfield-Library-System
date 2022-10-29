package edu.miu.cs.cs425.fairfieldlibrarywebapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.Book;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.BookService;

@Controller
@RequestMapping(value = { "/library/secured/book" })
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(value = { "/list" })
    public ModelAndView listBooks(@RequestParam(defaultValue = "0") int pageNo) {
        var books = bookService.getBooksPaged(pageNo);
        var modelAndView = new ModelAndView();
        modelAndView.addObject("books", books);
        modelAndView.addObject("currentPageNo", pageNo);
        modelAndView.setViewName("secured/librarian/book/list");
        return modelAndView;
    }

    @GetMapping(value = { "/member/list" })
    public ModelAndView listBooksForMember(@RequestParam(defaultValue = "0") int pageNo) {
        var books = bookService.getBooksPaged(pageNo);
        var modelAndView = new ModelAndView();
        modelAndView.addObject("books", books);
        modelAndView.addObject("currentPageNo", pageNo);
        modelAndView.setViewName("secured/member/book/list");
        return modelAndView;
    }

    @GetMapping(value = { "/new" })
    public ModelAndView displayNewBookForm() {
        var modelAndView = new ModelAndView();
        modelAndView.addObject("book", new Book());
        modelAndView.setViewName("secured/librarian/book/new");
        return modelAndView;
    }

    @PostMapping(value = { "/new" })
    public ModelAndView addNewBook(@Valid @ModelAttribute Book book, BindingResult bindingResult) {
        var modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("book", book);
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.setViewName("secured/librarian/book/new");
            return modelAndView;
        }
        bookService.saveNewBook(book);
        modelAndView.setViewName("redirect:/library/secured/book/list");
        return modelAndView;
    }

    @GetMapping(value = { "/edit/{bookId}" })
    public ModelAndView displayEditBook(@PathVariable Integer bookId) {
        var modelAndView = new ModelAndView();
        var book = bookService.findBookById(bookId);
        if (book == null) {
            modelAndView.setViewName("redirect:/library/secured/book/list");
            return modelAndView;
        }
        modelAndView.addObject("book", book);
        modelAndView.setViewName("secured/librarian/book/edit");
        return modelAndView;
    }

    @PostMapping(value = { "/update" })
    public ModelAndView updateBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        var modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("book", book);
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.setViewName("secured/librarian/book/edit");
            return modelAndView;
        }
        bookService.updateBook(book);
        modelAndView.setViewName("redirect:/library/secured/book/list");
        return modelAndView;
    }

    @GetMapping(value = { "/delete/{bookId}" })
    public ModelAndView deleteBook(@PathVariable Integer bookId) {
        var modelAndView = new ModelAndView();
        bookService.deleteBook(bookId);
        modelAndView.setViewName("redirect:/library/secured/book/list");
        return modelAndView;
    }

    @GetMapping(value = { "/search" })
    public ModelAndView searchBooks(@RequestParam String searchString) {
        if (searchString.isBlank()) {
            return new ModelAndView("redirect:/library/secured/book/list");
        }
        var modelAndView = new ModelAndView();
        var books = bookService.searchBooks(searchString);
        modelAndView.addObject("books", books);
        modelAndView.addObject("searchString", searchString);
        modelAndView.setViewName("secured/librarian/book/searchResult");
        return modelAndView;
    }

    @GetMapping(value = { "/member/search" })
    public ModelAndView searchBooksForMember(@RequestParam String searchString) {
        if (searchString.isBlank()) {
            return new ModelAndView("redirect:/library/secured/book/member/list");
        }
        var modelAndView = new ModelAndView();
        var books = bookService.searchBooks(searchString);
        modelAndView.addObject("books", books);
        modelAndView.addObject("searchString", searchString);
        modelAndView.setViewName("secured/member/book/searchResult");
        return modelAndView;
    }
}

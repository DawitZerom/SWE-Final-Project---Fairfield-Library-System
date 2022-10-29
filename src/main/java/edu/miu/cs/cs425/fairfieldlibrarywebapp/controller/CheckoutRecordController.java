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

import edu.miu.cs.cs425.fairfieldlibrarywebapp.dto.CheckoutRecordDTO;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.BookCopyNotAvailableException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.CustomNotFoundException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.MemberCannotCheckoutException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.BookService;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.CheckoutRecordService;

@Controller
@RequestMapping(value = { "/library/secured/checkout" })
public class CheckoutRecordController {
    @Autowired
    private CheckoutRecordService checkoutRecordService;
    @Autowired
    private BookService bookService;

    @GetMapping(value = { "/list" })
    public ModelAndView listCheckoutRecords(@RequestParam(defaultValue = "0") int pageNo) {
        var checkoutRecords = checkoutRecordService.getCheckoutRecordsPaged(pageNo);
        var modelAndView = new ModelAndView();
        modelAndView.addObject("checkoutRecords", checkoutRecords);
        modelAndView.addObject("currentPageNo", pageNo);
        modelAndView.setViewName("secured/librarian/checkout/list");
        return modelAndView;
    }

    @GetMapping(value = { "/new" })
    public ModelAndView displayNewCheckoutRecordForm() {
        var modelAndView = new ModelAndView();
        modelAndView.addObject("checkoutRecordDTO", new CheckoutRecordDTO());
        modelAndView.setViewName("secured/librarian/checkout/new");
        return modelAndView;
    }

    @GetMapping(value = { "/new/book/{bookId}" })
    public ModelAndView displayNewCheckoutRecordForm(@PathVariable Integer bookId) {
        var modelAndView = new ModelAndView();
        var checkoutRecordDTO = new CheckoutRecordDTO();
        var book = bookService.findBookById(bookId);
        checkoutRecordDTO.setIsbn(book.getIsbn());
        modelAndView.addObject("checkoutRecordDTO", checkoutRecordDTO);
        modelAndView.setViewName("secured/librarian/checkout/new");
        return modelAndView;
    }

    @PostMapping(value = { "/new" })
    public ModelAndView addNewCheckoutRecord(@Valid @ModelAttribute CheckoutRecordDTO checkoutRecordDTO,
            BindingResult bindingResult)
            throws CustomNotFoundException, BookCopyNotAvailableException, MemberCannotCheckoutException {
        var modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("checkoutRecordDTO", checkoutRecordDTO);
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.setViewName("secured/librarian/checkout/new");
            return modelAndView;
        }
        // var res = checkoutRecordService.saveNewCheckoutRecord(checkoutRecordDTO);
        // if (res == null) {
        // modelAndView.addObject("checkoutRecordDTO", checkoutRecordDTO);
        // modelAndView.addObject("errorMessage", "This member cannot checkout another
        // book");
        // modelAndView.setViewName("secured/librarian/checkout/new");
        // return modelAndView;
        // }
        checkoutRecordService.saveNewCheckoutRecord(checkoutRecordDTO);
        modelAndView.setViewName("redirect:/library/secured/checkout/list");
        return modelAndView;
    }

    @GetMapping(value = { "/edit/{checkoutRecordId}" })
    public ModelAndView displayEditCheckoutRecord(@PathVariable Integer checkoutRecordId) {
        var modelAndView = new ModelAndView();
        var checkoutRecord = checkoutRecordService.findCheckoutRecordById(checkoutRecordId);
        if (checkoutRecord == null) {
            modelAndView.setViewName("redirect:/library/secured/checkout/list");
            return modelAndView;
        }
        var checkoutRecordDTO = new CheckoutRecordDTO(checkoutRecordId, checkoutRecord.getBook().getIsbn(),
                checkoutRecord.getLibraryMember().getMemberNumber());
        modelAndView.addObject("checkoutRecordDTO", checkoutRecordDTO);
        modelAndView.setViewName("secured/librarian/checkout/edit");
        return modelAndView;
    }

    @PostMapping(value = { "/update" })
    public ModelAndView updateCheckoutRecord(@Valid @ModelAttribute CheckoutRecordDTO checkoutRecordDTO,
            BindingResult bindingResult) throws CustomNotFoundException {
        var modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("checkoutRecordDTO", checkoutRecordDTO);
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.setViewName("secured/librarian/checkout/edit");
            return modelAndView;
        }
        checkoutRecordService.updateCheckoutRecord(checkoutRecordDTO);
        modelAndView.setViewName("redirect:/library/secured/checkout/list");
        return modelAndView;
    }

    @GetMapping(value = { "/delete/{checkoutRecordId}" })
    public ModelAndView deleteCheckoutRecord(@PathVariable Integer checkoutRecordId) {
        var modelAndView = new ModelAndView();
        checkoutRecordService.deleteCheckoutRecord(checkoutRecordId);
        modelAndView.setViewName("redirect:/library/secured/checkout/list");
        return modelAndView;
    }

    @GetMapping(value = { "/search" })
    public ModelAndView searchCheckoutRecords(@RequestParam String searchString,
            @RequestParam(defaultValue = "0") int pageNo) {
        if (searchString.isBlank()) {
            return new ModelAndView("redirect:/library/secured/checkout/list");
        }
        var modelAndView = new ModelAndView();
        var checkoutRecords = checkoutRecordService.searchCheckoutRecords(searchString, pageNo);
        modelAndView.addObject("checkoutRecords", checkoutRecords);
        modelAndView.addObject("currentPageNo", pageNo);
        modelAndView.addObject("searchString", searchString);
        modelAndView.setViewName("secured/librarian/checkout/searchResult");
        return modelAndView;
    }
}

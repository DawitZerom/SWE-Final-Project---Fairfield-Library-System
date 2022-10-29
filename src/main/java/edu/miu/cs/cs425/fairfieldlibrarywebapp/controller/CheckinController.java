package edu.miu.cs.cs425.fairfieldlibrarywebapp.controller;

import java.util.List;

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

import edu.miu.cs.cs425.fairfieldlibrarywebapp.dto.CheckinDTO;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.CustomNotFoundException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.CheckoutRecord;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.CheckinService;

@Controller
@RequestMapping(value = { "/library/secured/checkin" })
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    @GetMapping(value = { "/find" })
    public ModelAndView displayFormFindCheckin() {
        var modelAndView = new ModelAndView();
        modelAndView.setViewName("secured/librarian/checkin/find");
        return modelAndView;
    }

    @GetMapping(value = { "/list" })
    public ModelAndView getCheckins(@RequestParam(defaultValue = "0") int pageNo) {
        var checkoutRecords = checkinService.getCheckinsPaged(pageNo);
        var modelAndView = new ModelAndView();
        modelAndView.addObject("checkoutRecords", checkoutRecords);
        modelAndView.addObject("currentPageNo", pageNo);
        modelAndView.setViewName("secured/librarian/checkin/list");
        return modelAndView;
    }

    @GetMapping(value = { "/searchbyisbn" })
    public ModelAndView findCheckinsByIsbn(@RequestParam String isbn) {
        var modelAndView = new ModelAndView();
        List<CheckoutRecord> checkoutRecords = checkinService.findCheckoutRecordsByIsbn(isbn);
        modelAndView.addObject("isbn", isbn);
        if (checkoutRecords == null) {
            modelAndView.addObject("notFound", String.format("Book with ISBN: %s is not found", isbn));
            modelAndView.setViewName("secured/librarian/checkin/find");
            return modelAndView;
        }
        modelAndView.addObject("checkoutRecords", checkoutRecords);
        modelAndView.setViewName("secured/librarian/checkin/foundlist");
        return modelAndView;
    }

    @GetMapping(value = { "/find/{checkoutRecordId}" })
    public ModelAndView findCheckinByCheckoutRecordId(@PathVariable Integer checkoutRecordId) {
        var modelAndView = new ModelAndView();
        var checkoutRecord = checkinService.findCheckoutRecordById(checkoutRecordId);
        if (checkoutRecord == null) {
            modelAndView.addObject("notFound", String.format("Book with Id: %d is not found", checkoutRecordId));
            modelAndView.setViewName("secured/librarian/checkin/find");
            return modelAndView;
        }
        modelAndView.addObject("checkoutRecord", checkoutRecord);
        modelAndView.setViewName("secured/librarian/checkin/detail");
        return modelAndView;
    }

    @PostMapping(value = { "/checkinbook" })
    public ModelAndView checkinBook(@RequestParam Integer checkoutRecordId) {
        var modelAndView = new ModelAndView();
        var checkoutRecord = checkinService.findCheckoutRecordById(checkoutRecordId);
        if (checkoutRecord == null) {
            modelAndView.addObject("notFound", "The record is not found");
            modelAndView.setViewName("secured/librarian/checkin/find");
            return modelAndView;
        }
        checkinService.checkin(checkoutRecord);
        modelAndView.setViewName("redirect:/library/secured/checkin/list");
        return modelAndView;
    }

    @GetMapping(value = { "/edit/{checkoutRecordId}" })
    public ModelAndView displayEditCheckin(@PathVariable Integer checkoutRecordId) {
        var modelAndView = new ModelAndView();
        var checkoutRecord = checkinService.findCheckoutRecordById(checkoutRecordId);
        if (checkoutRecord == null) {
            modelAndView.setViewName("redirect:/library/secured/checkin/list");
            return modelAndView;
        }
        modelAndView.addObject("checkoutRecord", checkoutRecord);
        modelAndView.setViewName("secured/librarian/checkin/edit");
        return modelAndView;
    }

    @PostMapping(value = { "/update" })
    public ModelAndView updateCheckin(@Valid @ModelAttribute CheckinDTO checkinDTO,
            BindingResult bindingResult) throws CustomNotFoundException {
        var modelAndView = new ModelAndView();
        var checkoutRecord = checkinService.findCheckoutRecordById(checkinDTO.getCheckoutRecordId());
        if (checkoutRecord == null) {
            modelAndView.setViewName("secured/librarian/checkin/list");
            return modelAndView;
        }
        checkinService.updateCheckin(checkinDTO);
        modelAndView.setViewName("redirect:/library/secured/checkin/list");
        return modelAndView;
    }

    @GetMapping(value = { "/search" })
    public ModelAndView saearchCheckins(@RequestParam String searchString,
            @RequestParam(defaultValue = "0") int pageNo) {
        if (searchString.isBlank()) {
            return new ModelAndView("redirect:/library/secured/checkin/list");
        }
        var modelAndView = new ModelAndView();
        var checkoutRecords = checkinService.searchCheckins(searchString, pageNo);
        modelAndView.addObject("checkoutRecords", checkoutRecords);
        modelAndView.addObject("currentPageNo", pageNo);
        modelAndView.addObject("searchString", searchString);
        modelAndView.setViewName("secured/librarian/checkin/searchResult");
        return modelAndView;
    }

    @GetMapping(value = { "/delete/{checkoutRecordId}" })
    public ModelAndView deleteCheckoutRecord(@PathVariable Integer checkoutRecordId) {
        var modelAndView = new ModelAndView();
        checkinService.deleteCheckin(checkoutRecordId);
        modelAndView.setViewName("redirect:/library/secured/checkin/list");
        return modelAndView;
    }

}

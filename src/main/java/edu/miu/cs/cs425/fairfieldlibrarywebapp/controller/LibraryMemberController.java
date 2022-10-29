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

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.Address;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.LibraryMember;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.LibraryMemberService;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.LibraryMemberTypeService;

@Controller
@RequestMapping(value = { "/library/secured/member", "/library/secured/librarymember" })
public class LibraryMemberController {
    @Autowired
    private LibraryMemberService libraryMemberService;

    @Autowired
    private LibraryMemberTypeService libraryMemberTypeService;

    @GetMapping(value = { "/list" })
    public ModelAndView listLibrarymembers(@RequestParam(defaultValue = "0") int pageNo) {
        var libraryMembers = libraryMemberService.getLibraryMembersPaged(pageNo);
        var modelAndView = new ModelAndView();
        modelAndView.addObject("libraryMembers", libraryMembers);
        modelAndView.addObject("currentPageNo", pageNo);
        modelAndView.setViewName("secured/librarian/librarymember/list");
        return modelAndView;
    }

    @GetMapping(value = { "/new" })
    public ModelAndView displayNewLibrarymemberForm() {
        var modelAndView = new ModelAndView();
        modelAndView.addObject("libraryMember", new LibraryMember());
        modelAndView.addObject("address", new Address());
        modelAndView.addObject("libraryMemberTypes", libraryMemberTypeService.getLibraryMemberTypes());
        modelAndView.setViewName("secured/librarian/librarymember/new");
        return modelAndView;
    }

    @PostMapping(value = { "/new" })
    public ModelAndView addNewLibrarymember(@Valid @ModelAttribute LibraryMember libraryMember,
            BindingResult bindingResult) {
        var modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("libraryMember", libraryMember);
            modelAndView.addObject("libraryMemberTypes", libraryMemberTypeService.getLibraryMemberTypes());
            modelAndView.addObject("address", libraryMember.getAddress());
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.setViewName("secured/librarian/librarymember/new");
            return modelAndView;
        }
        libraryMemberService.saveNewLibraryMember(libraryMember);
        modelAndView.setViewName("redirect:/library/secured/librarymember/list");
        return modelAndView;
    }

    @GetMapping(value = { "/edit/{libraryMemberId}" })
    public ModelAndView displayEditlibraryMember(@PathVariable Integer libraryMemberId) {
        var modelAndView = new ModelAndView();
        var libraryMember = libraryMemberService.findLibraryMemberById(libraryMemberId);
        if (libraryMember == null) {
            modelAndView.setViewName("redirect:/library/secured/librarymember/list");
            return modelAndView;
        }
        modelAndView.addObject("libraryMember", libraryMember);
        modelAndView.addObject("address",
                libraryMember.getAddress() == null ? new Address() : libraryMember.getAddress());
        modelAndView.addObject("libraryMemberTypes", libraryMemberTypeService.getLibraryMemberTypes());
        modelAndView.setViewName("secured/librarian/librarymember/edit");
        return modelAndView;
    }

    @PostMapping(value = { "/update" })
    public ModelAndView updatelibraryMember(@Valid @ModelAttribute("libraryMember") LibraryMember libraryMember,
            BindingResult bindingResult) {
        var modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("libraryMember", libraryMember);
            modelAndView.addObject("address", libraryMember.getAddress());
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.setViewName("secured/librarian/librarymember/edit");
            return modelAndView;
        }
        libraryMemberService.updateLibraryMember(libraryMember);
        modelAndView.setViewName("redirect:/library/secured/member/list");
        return modelAndView;
    }

    @GetMapping(value = { "/delete/{libraryMemberId}" })
    public ModelAndView deletelibraryMember(@PathVariable Integer libraryMemberId) {
        var modelAndView = new ModelAndView();
        libraryMemberService.deleteLibraryMember(libraryMemberId);
        modelAndView.setViewName("redirect:/library/secured/librarymember/list");
        return modelAndView;
    }

    @GetMapping(value = { "/search" })
    public ModelAndView searchLibraryMembers(@RequestParam String searchString) {
        if (searchString.isBlank()) {
            return new ModelAndView("redirect:/library/secured/member/list");
        }
        var modelAndView = new ModelAndView();
        var libraryMembers = libraryMemberService.searchLibraryMembers(searchString);
        modelAndView.addObject("libraryMembers", libraryMembers);
        modelAndView.addObject("searchString", searchString);
        modelAndView.setViewName("secured/librarian/librarymember/searchResult");
        return modelAndView;
    }
}

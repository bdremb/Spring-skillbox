package com.example.spring.springdatacontacts.controller;

import com.example.spring.springdatacontacts.model.Contact;
import com.example.spring.springdatacontacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        model.addAttribute("contact", new Contact());
        return "index";
    }

    @GetMapping("/contact/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Contact contact = contactService.getById(id);
        if (nonNull(contact)) {
            model.addAttribute("contact", contact);
            model.addAttribute("contacts", contactService.findAll());
            return "index";
        }
        return "index";
    }

    @PostMapping("/contact/edit")
    public String editContact(@ModelAttribute Contact contact) {
        if (isNull(contact.getId()) || isNull(contactService.getById(contact.getId()))) {
            contactService.save(contact);
        } else {
            contactService.update(contact);
        }
        return "redirect:/";
    }

    @GetMapping("/contact/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteById(id);
        return "redirect:/";
    }

}

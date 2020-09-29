package com.sample.masking.controller;

import com.sample.masking.dto.Contact;
import com.sample.masking.json.JsonView;
import com.sample.masking.json.JsonViewModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.masking.json.Match;
import com.sample.masking.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;


@RestController
public class ContactController {
    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    Contact getContact(@PathVariable Long id) throws InvocationTargetException, IOException, NoSuchMethodException, IllegalAccessException {
        Optional<Contact> contact = contactService.findById(id);
      return contact.get();
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    String getAllContacts() throws IOException {
        Optional<String> contactsList = contactService.findAll();
        return contactsList.get();
    }
}

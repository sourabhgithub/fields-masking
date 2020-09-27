package com.sample.masking.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.masking.dto.Address;
import com.sample.masking.dto.Contact;
import com.sample.masking.dto.Phone;
import com.sample.masking.entity.PersonContact;
import com.sample.masking.json.JsonView;
import com.sample.masking.json.JsonViewModule;
import com.sample.masking.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sample.masking.json.Match.match;


@Service
public class ContactService {


    ObjectMapper mapper = new ObjectMapper().registerModule(new JsonViewModule());

    @Autowired
    PersonRepository personRepository;

    private static final String contactFields[] = {"profileImage"};
    private static final String addressFields[] = {"address.city"};

    public Optional<String> findById(Long id) throws IOException {
        Contact byId = getById(id);
        String response = mapper.writeValueAsString(JsonView.with(byId).onClass(Contact.class, match().exclude(contactFields)));

        //In case we need to remove from chile object
       // String response = mapper.writeValueAsString(JsonView.with(byId).onClass(Address.class, match().exclude(removeFields)));
        return Optional.of(response);
    }



    public Optional<String> findAll() throws IOException {

        List<Contact> allContacts = findAllContacts();
        String response = mapper.writeValueAsString(JsonView.with(allContacts).onClass(Contact.class, match().exclude(addressFields)));

        //In case we need to remove from chile object
        // String response = mapper.writeValueAsString(JsonView.with(byId).onClass(Address.class, match().exclude(removeFields)));
        return Optional.of(response);

    }

    private Contact getById(Long id) {
        Contact contact = new Contact();
        Optional<PersonContact> byId = personRepository.findById(id);
        if(byId.isPresent()) {
            PersonContact personContact = byId.get();

            Phone phone = new Phone();
            phone.setPersonalNumber(personContact.getPersonalNumber());
            phone.setWorkNumber(personContact.getWorkNumber());

            Address address =  new Address();
            address.setAddressLine(personContact.getAddressLine());
            address.setCity(personContact.getCity());
            address.setCountryCode(personContact.getCountryCode());
            address.setState(personContact.getState());
            address.setZipCode(personContact.getZipCode());


            contact.setName(personContact.getName());
            contact.setBirthDate(personContact.getBirthDate());
            contact.setPhoneNumber(phone);
            contact.setAddress(address);
            contact.setCompany(personContact.getCompany());
            contact.setProfileImage(personContact.getProfileImage());
            contact.setEmail(personContact.getEmail());

        }
        return contact;
    }

    private List<Contact> findAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        List<PersonContact> all = personRepository.findAll();
        for(PersonContact personContact : all) {
            Contact contact = new Contact();
            Phone phone = new Phone();
            phone.setPersonalNumber(personContact.getPersonalNumber());
            phone.setWorkNumber(personContact.getWorkNumber());

            Address address =  new Address();
            address.setAddressLine(personContact.getAddressLine());
            address.setCity(personContact.getCity());
            address.setCountryCode(personContact.getCountryCode());
            address.setState(personContact.getState());
            address.setZipCode(personContact.getZipCode());


            contact.setName(personContact.getName());
            contact.setBirthDate(personContact.getBirthDate());
            contact.setPhoneNumber(phone);
            contact.setAddress(address);
            contact.setCompany(personContact.getCompany());
            contact.setProfileImage(personContact.getProfileImage());
            contact.setEmail(personContact.getEmail());

            contacts.add(contact);
        }
        return contacts;
       }

}

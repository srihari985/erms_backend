package com.erms.ERMS_Application.Demo.contactForm;

import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactFormService {

    @Autowired
    private ContactFormRepository contactFormRepository;

    @Autowired
    private SalesRepository salesRepository;


    public ContactFormDTO createContact(long salesId, String contactPerson, String contactNumber, String companyName, String emailId, String address, String gstIn) {
        Sales sale = salesRepository.findById(salesId)
                .orElseThrow(() -> new EntityNotFoundException("Sales not found with id: " + salesId));

        ContactForm contactForm = new ContactForm();
        contactForm.setContactPerson(contactPerson);
        contactForm.setContactNumber(contactNumber);
        contactForm.setCompanyName(companyName);
        contactForm.setEmailId(emailId);
        contactForm.setAddress(address);
        contactForm.setGstIn(gstIn);
        contactForm.setSales(sale);

        ContactForm savedContact = contactFormRepository.save(contactForm);

        // Return the DTO
        return new ContactFormDTO(
                savedContact.getcId(),
                savedContact.getContactPerson(),
                savedContact.getContactNumber(),
                savedContact.getCompanyName(),
                savedContact.getEmailId(),
                savedContact.getAddress(),
                savedContact.getGstIn(),
                salesId  // Pass the sales ID
        );
    }

    public Optional<ContactFormDTO> getContactFormById(long cId) {
        Optional<ContactForm> contactFormEntityOpt = contactFormRepository.findById(cId);

        if (contactFormEntityOpt.isPresent()) {
            ContactForm contactForm = contactFormEntityOpt.get();
            ContactFormDTO contactFormDTO = new ContactFormDTO(
                    contactForm.getcId(),
                    contactForm.getContactPerson(),
                    contactForm.getContactNumber(),
                    contactForm.getCompanyName(),
                    contactForm.getEmailId(),
                    contactForm.getAddress(),
                    contactForm.getGstIn(),
                    contactForm.getSales() != null ? contactForm.getSales().getId() : 0L  // Get the sales ID
            );
            return Optional.of(contactFormDTO);  // Return the DTO wrapped in Optional
        }
        return Optional.empty();  // Return empty Optional if not found
    }

    public List<ContactFormDTO> getAllContactForm() {
        List<ContactForm> addAll = contactFormRepository.findAll();

        return addAll.stream().map(conatctFrom -> new ContactFormDTO(
                conatctFrom.getcId(),
                conatctFrom.getContactPerson(),
                conatctFrom.getContactNumber(),
                conatctFrom.getCompanyName(),
                conatctFrom.getEmailId(),
                conatctFrom.getAddress(),
                conatctFrom.getGstIn(),
                conatctFrom.getSales() != null ? conatctFrom.getSales().getId() : 0L  // Get the sales ID
        )).collect(Collectors.toList());
    }



    public ContactForm updateContactForm(long cId, ContactForm contactForm) {

        ContactForm contactForms = contactFormRepository.findById(cId)
                .orElseThrow(() -> new EntityNotFoundException("ContactForm not found with id: " + cId));

        Optional.ofNullable(contactForm.getContactPerson()).ifPresent(contactForms::setContactPerson);
        Optional.ofNullable(contactForm.getContactNumber()).ifPresent(contactForms::setContactNumber);
        Optional.ofNullable(contactForm.getCompanyName()).ifPresent(contactForms::setCompanyName);
        Optional.ofNullable(contactForm.getEmailId()).ifPresent(contactForms::setEmailId);
        Optional.ofNullable(contactForm.getAddress()).ifPresent(contactForms::setAddress);
        Optional.ofNullable(contactForm.getGstIn()).ifPresent(contactForms::setGstIn);


        return contactFormRepository.save(contactForms);
    }

    public void deleteContactFormById(long cId) {
        ContactForm contactForm = contactFormRepository.findById(cId)
                .orElseThrow(() -> new EntityNotFoundException("ContactForm not found with id: " + cId));
        contactFormRepository.delete(contactForm);
    }

}

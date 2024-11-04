package com.erms.ERMS_Application.Demo.contactForm;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/contactForm")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping("/save/{salesId}")
    public ResponseEntity<ContactFormDTO> contactForm(@PathVariable long salesId,
                                                      @RequestParam String contactPerson,
                                                      @RequestParam String contactNumber,
                                                      @RequestParam String companyName,
                                                      @RequestParam String emailId,
                                                      @RequestParam String address,
                                                      @RequestParam String gstIn) {
        ContactFormDTO contactFormDTO = contactFormService.createContact(salesId, contactPerson, contactNumber, companyName, emailId, address, gstIn);
        return ResponseEntity.ok(contactFormDTO);
    }


    @GetMapping("/getContactFormById/{cId}")
    public ResponseEntity<?> getByIdContactForm(@PathVariable long cId) {
        Optional<ContactFormDTO> contactFormDTOOpt = contactFormService.getContactFormById(cId);

        if (contactFormDTOOpt.isPresent()) {
            return ResponseEntity.ok(contactFormDTOOpt.get());  // Return 200 OK with the specific party details
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contact Form  not found for ID: " + cId);  // Return 404 if not found
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllContactForm(){

        try {
            List<ContactFormDTO> allContactDTO = contactFormService.getAllContactForm();
            return new ResponseEntity<>(allContactDTO, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error fetching all conatact forms: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/update/{cId}")
    public ResponseEntity<?> updateContactForm(
            @PathVariable long cId,
            @RequestBody ContactForm contactForm
            ) {

        try {
            ContactForm updatedContact = contactFormService.updateContactForm(cId,contactForm);
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating contact: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @DeleteMapping("/delete/{cId}")
    public ResponseEntity<?> deleteContactFormById(@PathVariable long cId) {
        try {
            contactFormService.deleteContactFormById(cId);
            return new ResponseEntity<>("Contact Form deleted successfully for ID: " + cId, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Contact Form not found for ID: " + cId, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error deleting contact form: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}

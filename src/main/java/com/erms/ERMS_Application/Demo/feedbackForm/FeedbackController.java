package com.erms.ERMS_Application.Demo.feedbackForm;

import com.erms.ERMS_Application.Demo.contactForm.ContactForm;
import com.erms.ERMS_Application.Demo.contactForm.ContactFormDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @PostMapping("/save/{salesId}")
    public ResponseEntity<FeedbackFormDTO> feedbackForm(
            @PathVariable long salesId, @RequestBody FeedbackForm feedback) {

        // Create a new feedback form using the service layer
        FeedbackFormDTO feedbackFormDTO = feedbackService.createFeedback(salesId, feedback);

        return ResponseEntity.ok(feedbackFormDTO);
    }

    @GetMapping("/getfeedbackForm/{fbId}")
    public ResponseEntity<?> getByIdFeedback(@PathVariable long fbId) {
        Optional<FeedbackFormDTO> feedbackFormDTO = feedbackService.getFeedbackById(fbId);

        if (feedbackFormDTO.isPresent()) {
            return ResponseEntity.ok(feedbackFormDTO.get());  // Return 200 OK with the specific party details
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contact Form  not found for ID: " + fbId);  // Return 404 if not found
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFeedback(){

        try {
            List<FeedbackFormDTO> allFeedbackFormDTO = feedbackService.getAllFeedbackForm();
            return new ResponseEntity<>(allFeedbackFormDTO, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error fetching all feedback forms: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/update/{fbId}")
    public ResponseEntity<?> updateContactForm(
            @PathVariable long fbId,
            @RequestParam(required = false) String requirements,
            @RequestParam(required = false) String feedback
    ) {

        try {
            FeedbackForm updatedFeedbackForm = feedbackService.updateFeedbackForm(fbId, requirements, feedback);
            return new ResponseEntity<>(updatedFeedbackForm, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating contact: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteById/{fbId}")
    public ResponseEntity<?> deleteFeedbackById(@PathVariable long fbId) {
        try {
            feedbackService.deleteFeedbackById(fbId);
            return new ResponseEntity<>("Feedback Form deleted successfully for ID: " + fbId, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Feedback Form not found for ID: " + fbId, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error deleting feedback form: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

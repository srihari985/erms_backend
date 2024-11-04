package com.erms.ERMS_Application.Demo.WebsiteFeedbackForm;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/websiteFeedback")
public class WebsiteFeedbackFormController {

    @Autowired
    private WebsiteFeedbackFormService websiteFeedbackFormService;

    @PostMapping("/save/{salesId}")
    public ResponseEntity<WebsiteFeedbackFormEntity> saveWebsiteFeedback(
            @PathVariable long salesId, @RequestBody WebsiteFeedbackFormEntity websiteFeedback) {

        WebsiteFeedbackFormEntity savedFeedback = websiteFeedbackFormService.saveWebsiteFeedback(salesId,websiteFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFeedback);
    }

    @GetMapping("/get/{wfbId}")
    public ResponseEntity<?> getWebsiteFeedbackById(@PathVariable long wfbId) {
        Optional<WebsiteFeedbackFormDTO> feedbackFormDTO = websiteFeedbackFormService.getFeedbackById(wfbId);

        if (feedbackFormDTO.isPresent()) {
            return ResponseEntity.ok(feedbackFormDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Website Feedback Form not found for ID: " + wfbId);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllWebsiteFeedback() {
        try {
            List<WebsiteFeedbackFormDTO> allFeedback = websiteFeedbackFormService.getAllFeedback();
            return new ResponseEntity<>(allFeedback, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error fetching all feedback forms: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{wfbId}")
    public ResponseEntity<?> updateWebsiteFeedback(
            @PathVariable long wfbId,
            @RequestParam(required = false) String hrbpAppFb,
            @RequestParam(required = false) String inventoryFb,
            @RequestParam(required = false) String ermsFb,
            @RequestParam(required = false) String erp) {

        try {
            WebsiteFeedbackFormEntity updatedFeedback = websiteFeedbackFormService.updateFeedback(wfbId, hrbpAppFb, inventoryFb, ermsFb, erp);
            return new ResponseEntity<>(updatedFeedback, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Website Feedback Form not found for ID: " + wfbId, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating feedback form: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{wfbId}")
    public ResponseEntity<?> deleteWebsiteFeedbackById(@PathVariable long wfbId) {
        try {
            websiteFeedbackFormService.deleteFeedbackById(wfbId);
            return new ResponseEntity<>("Feedback Form deleted successfully for ID: " + wfbId, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Feedback Form not found for ID: " + wfbId, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error deleting feedback form: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


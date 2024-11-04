package com.erms.ERMS_Application.Demo.video;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/videos")
public class DemoVideoController {

    @Autowired
    private DemoVideoService demoVideoService;

    @PostMapping("/save/link")
    public ResponseEntity<DemoVideo> uploadVideoLink(@RequestParam String title,@RequestParam String link) {
        try {
            DemoVideo savedVideo = demoVideoService.saveVideolink(title,link);
            return new ResponseEntity<>(savedVideo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DemoVideo>> getAllVideos() {
        try {
            List<DemoVideo> videos = demoVideoService.getAllVideos();
            if (videos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 if no videos found
            }
            return new ResponseEntity<>(videos, HttpStatus.OK); // Return 200 and list of videos
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Handle exceptions
        }
    }

    @DeleteMapping("/deleteById/{videoId}")
    public ResponseEntity<?> deleteVideoById(@PathVariable long videoId) {
        try {
            demoVideoService.deleteVideoById(videoId);
            return new ResponseEntity<>("Video deleted successfully for ID: " + videoId, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Video not found for ID: " + videoId, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error deleting video: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

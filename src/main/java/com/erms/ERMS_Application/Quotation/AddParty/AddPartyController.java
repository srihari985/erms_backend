package com.erms.ERMS_Application.Quotation.AddParty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/addParty")
public class AddPartyController {

	@Autowired
	private AddPartyService addPartyService;

	@PostMapping("/save/{salesId}")
	public ResponseEntity<AddPartyDTO> addParty(@PathVariable long salesId, @RequestBody AddPartyEntity addParty) {
		AddPartyDTO createdParty = addPartyService.createParty(salesId, addParty);
		return new ResponseEntity<>(createdParty, HttpStatus.CREATED);
	}

	@GetMapping("/getPartyById/{adId}")
	public ResponseEntity<?> getAddedParty(@PathVariable long adId) {
		Optional<AddPartyDTO> partyDTOOpt = addPartyService.getParty(adId);

		if (partyDTOOpt.isPresent()) {
			return ResponseEntity.ok(partyDTOOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Party not found for ID: " + adId);
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllParties() {
		try {
			List<AddPartyDTO> allPartiesDTO = addPartyService.getAllParties();
			return new ResponseEntity<>(allPartiesDTO, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Error fetching all parties: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/update/{adId}")
	public ResponseEntity<?> updatePartially(@PathVariable long adId, @RequestBody AddPartyEntity updateAdd) {
		try {
			AddPartyDTO updatedParty = addPartyService.updateAddParty(adId, updateAdd);
			return new ResponseEntity<>(updatedParty, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Error updating party: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{adId}")
	public ResponseEntity<?> deleteByAddPartyId(@PathVariable long adId) {
		try {
			addPartyService.deleteByPartyId(adId);
			return new ResponseEntity<>("Party deleted successfully", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Error deleting party: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

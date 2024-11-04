package com.erms.ERMS_Application.Quotation.ItemsSaleTotal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erms.ERMS_Application.Quotation.ItemsSaleTable.ItemsSaleTableDTO;

@RestController
@RequestMapping("/api/itemsTotal")
public class ItemsSalesTotalController {

    @Autowired
    private ItemsSalesTotalService itemsSalesTotalService;




    //////////////////// Partial Update //////////////////////////////
    @PatchMapping("/update/{fId}")
    public ResponseEntity<?> updateFormDetail(@PathVariable long fId,
                                              @RequestParam float additionalCharges,
                                              @RequestParam String description) {
        try {
            ItemsSalesTotal updatedItemsSalesTotal = itemsSalesTotalService.updateFormDetails(fId, additionalCharges, description);
            ItemsSalesTotalUpdateDTO dto = itemsSalesTotalService.convertToDTO(updatedItemsSalesTotal);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating form: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //////////////////////////get Totals//////////////////
    
    ///////////////// getting total items by fid////////////////
    @GetMapping("/getTotals/{fId}")
    public ResponseEntity<ItemsSalesTotalUpdateDTO> getTotals(
            @PathVariable long fId) {
    	ItemsSalesTotalUpdateDTO totals = itemsSalesTotalService.getFinalTotals(fId);
        return ResponseEntity.ok(totals);
    }

}

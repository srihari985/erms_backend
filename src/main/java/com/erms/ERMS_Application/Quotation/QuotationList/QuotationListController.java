package com.erms.ERMS_Application.Quotation.QuotationList;

import com.erms.ERMS_Application.Quotation.AddParty.AddPartyDTO;
import com.erms.ERMS_Application.Quotation.Form.FormDTO;
import com.erms.ERMS_Application.Quotation.ItemsSaleTable.ItemsSaleTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotationList")
public class QuotationListController {

    @Autowired
    private QuotationListService quotationListService;


    // Get all items
    @GetMapping("/{sId}/getAll")
    public ResponseEntity<List<QuotationListDto>> getAllItems(@PathVariable long sId) {
        List<QuotationListDto> quotationList = quotationListService.getAllQuotationsList(sId);
        return new ResponseEntity<>(quotationList, HttpStatus.OK);
    }

    @GetMapping("/getAllQuotationDetails")
    public List<QuotationResponseDTO> getQuotations() {
        return quotationListService.getAllQuotations();
    }

    @GetMapping("/quotationDetailsBySaleId/{salesId}")
    public List<QuotationResponseDTO> getQuotationsBySalesId(@PathVariable Long salesId) {
        return quotationListService.getQuotationsBySalesId(salesId);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteQuotationById(@PathVariable Long id) {
        quotationListService.deleteQuotationById(id);
    }

    @PostMapping("/createQuotation/{sId}/{adId}/{fId}")
    public QuotationListDto createQuotation(
            @PathVariable long sId,
            @PathVariable long adId,
            @PathVariable long fId
            ) {
        return quotationListService.createQuotation(sId, adId, fId);
    }



/////////////update Quotation///////////////

    @PatchMapping("/update/quotation/{fId}")
    public QuotationListDto updateQuotationsByFormId(
            @PathVariable("fId") long fId,
            @RequestBody QuotationListDto quotationUpdateDto) {
        return quotationListService.updateQuotationByFormId(fId, quotationUpdateDto);
    }

    // Get AddPartyEntity details by fId
    @GetMapping("/getByIdAddPartyDetailsOnFid/{fId}")
    public ResponseEntity<AddPartyDTO> getByIdAddPartyDetailsOnFid(@PathVariable long fId) {
        AddPartyDTO addPartyDetails = quotationListService.getAddPartyDetailsByFid(fId);
        return new ResponseEntity<>(addPartyDetails, HttpStatus.OK);
    }

    // Get FormEntity details by fId
    @GetMapping("/getByIdFidDetails/{fId}")
    public ResponseEntity<FormDTO> getByIdFidDetails(@PathVariable long fId) {
        FormDTO formDetails = quotationListService.getFormDetailsByFid(fId);
        return new ResponseEntity<>(formDetails, HttpStatus.OK);
    }

    // Get ItemsSaleTableEntity details by fId
    @GetMapping("/getByIdItemsSaleTableDetailsOnFid/{fId}")
    public ResponseEntity<List<ItemsSaleTableDTO>> getByIdItemsSaleTableDetailsOnFid(@PathVariable long fId) {
        List<ItemsSaleTableDTO> itemsSaleTableDetails = quotationListService.getItemsSaleTableDetailsByFid(fId);
        return new ResponseEntity<>(itemsSaleTableDetails, HttpStatus.OK);
    }


}




package com.erms.ERMS_Application.Quotation.ItemsSaleTable;

import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import com.erms.ERMS_Application.Quotation.Form.FormService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/itemSalesTable")
public class ItemsSaleTableController {

    @Autowired
    private FormService formService;
    @Autowired
    private ItemsSalesTableRepo itemsSalesTableRepo;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private ItemsSaleTableService itemsSaleTableService;

    ////////////////////Adding List of items///////////////////////////////

    @PostMapping("/saveitems/{adId}/{fId}/{sId}")
    public ResponseEntity<List<ItemsSaleTableDTO>> saveItems(
            @PathVariable long adId,
            @PathVariable long fId,
            @PathVariable long sId,
            @RequestBody List<ItemsSaleTableDTO> itemsList) {

        // Call the service to save the list of items
        List<ItemsSaleTableDTO> savedItemsList = itemsSaleTableService.saveItems(adId, fId, sId, itemsList);

        // Return the list of saved items as DTOs
        return new ResponseEntity<>(savedItemsList, HttpStatus.CREATED);
    }


    // Get items by sales ID
    @GetMapping("/getItemsBySalesId/{sId}")
    public ResponseEntity<List<ItemsSaleTableDTO>> getItemsBySalesId(@PathVariable long sId) {
        List<ItemsSaleTableDTO> itemsList = itemsSaleTableService.getItemsBySalesId(sId);
        return ResponseEntity.ok(itemsList);
    }

    // Get all items
    @GetMapping("/getAll")
    public ResponseEntity<List<ItemsSaleTableDTO>> getAllItems() {
        List<ItemsSaleTableDTO> itemsList = itemsSaleTableService.getAllItems();
        return new ResponseEntity<>(itemsList, HttpStatus.OK);
    }

    @GetMapping("/getRecentItems/{sId}/{adId}/{saleDate}")
    public ResponseEntity<List<ItemsSaleTableDTO>> getRecentItems(
            @PathVariable long sId,
            @PathVariable long adId,
            @PathVariable String saleDate) {
        List<ItemsSaleTableDTO> recentItemsList = itemsSaleTableService.getRecentItemsBySalesIdAdIdAndDate(sId, adId, saleDate);
        return ResponseEntity.ok(recentItemsList);
    }


    @GetMapping("/getSelectedItems/{fId}")
    public ResponseEntity<List<ItemsSaleTableDTO>> getSelectedItems(
            @PathVariable long fId,
            @RequestParam List<Long> itemIds) {
        List<ItemsSaleTableDTO> selectedItemsList = itemsSaleTableService.getSelectedItemsByFormId(fId, itemIds);
        return ResponseEntity.ok(selectedItemsList);
    }
    
    
    ///////////////// getting final items by fid////////////////
    @GetMapping("/getfinalItems/{fId}")
    public ResponseEntity<List<ItemsSaleTableDTO>> getFinalItems(
            @PathVariable long fId) {
        List<ItemsSaleTableDTO> selectedItemsList = itemsSaleTableService.getFinalItems(fId);
        return ResponseEntity.ok(selectedItemsList);
    }

    // Patch API to update items
    @PatchMapping("/updateItems/{sId}/{adId}/{fId}")
    public TotalsDTO updateItems(
            @PathVariable long sId,
            @PathVariable long adId,
            @PathVariable long fId,
            @RequestBody List<ItemsSaleTableEntity> updatedItems,
            @RequestParam List<Long> itIds) {
            return itemsSaleTableService.updateItems(sId, adId,fId, itIds, updatedItems);

    }

    ///////////////////// Delete Form By Id ///////////////////////////
    @DeleteMapping("/{itSalesId}")
    public ResponseEntity<?> deleteItemById(@PathVariable("itSalesId") long itId) {
        try {
            itemsSaleTableService.deleteByItemsId(itId);
            return new ResponseEntity<>("ItemsSale Table deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting Items: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}

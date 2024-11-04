package com.erms.ERMS_Application.Quotation.ItemsSaleTotal;

import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import com.erms.ERMS_Application.Quotation.Form.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ItemsSalesTotalService {

    @Autowired
    private ItemsSalesTotalRepo itemsSalesTotalRepo;

    @Autowired
    private FormRepository formRepository;


    public ItemsSalesTotal updateFormDetails(long fId, float additionalCharges, String description) throws Exception {

        // Check if the FormEntity exists
        FormEntity updateForm = formRepository.findById(fId)
                .orElseThrow(() -> new Exception("FormEntity not found with ID: " + fId));

        // Retrieve the ItemsSalesTotal associated with the FormEntity
        ItemsSalesTotal updateTotal = itemsSalesTotalRepo.findByFormEntity(updateForm);

        if (updateTotal == null) {
            throw new Exception("ItemsSalesTotal not found for the given FormEntity.");
        }

        // Update fields
        updateTotal.setAdditionalCharges(additionalCharges);
        updateTotal.setDescription(description);

        // Calculate new grand total
        float grandTotal = updateTotal.getFinalAmount() + additionalCharges;
        updateTotal.setGrandTotal(grandTotal);

        // Save the updated entity back to the database
        return itemsSalesTotalRepo.save(updateTotal);
    }



    // Convert to DTO
    public ItemsSalesTotalUpdateDTO convertToDTO(ItemsSalesTotal itemsSalesTotal) {
        ItemsSalesTotalUpdateDTO dto = new ItemsSalesTotalUpdateDTO();
        dto.setItemsTotalId(itemsSalesTotal.getItemsTotalId());
        dto.setAdditionalCharges(itemsSalesTotal.getAdditionalCharges());
        dto.setDescription(itemsSalesTotal.getDescription());
        dto.setTotalAmount(itemsSalesTotal.getTotalAmount());
        dto.setTotalTax(itemsSalesTotal.getTotalTax());
        dto.setTotalDiscount(itemsSalesTotal.getTotalDiscount());
        dto.setFinalAmount(itemsSalesTotal.getFinalAmount());
        dto.setGrandTotal(itemsSalesTotal.getGrandTotal());
        return dto;
    }



    public ItemsSalesTotalUpdateDTO getFinalTotals(long fId) {
        FormEntity form = formRepository.findById(fId).orElse(null);
        
        if (form != null) {
            ItemsSalesTotal total = itemsSalesTotalRepo.findByFormEntity(form);
            
            if (total != null) {
                ItemsSalesTotalUpdateDTO dto = new ItemsSalesTotalUpdateDTO();
                dto.setItemsTotalId(total.getItemsTotalId());
                dto.setAdditionalCharges(total.getAdditionalCharges());
                dto.setDescription(total.getDescription());
                dto.setTotalAmount(total.getTotalAmount());
                dto.setTotalTax(total.getTotalTax());
                dto.setTotalDiscount(total.getTotalDiscount());
                dto.setFinalAmount(total.getFinalAmount());
                dto.setGrandTotal(total.getGrandTotal());
                
                return dto;
            }
        }
        
        return null;
    }

}


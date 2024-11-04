package com.erms.ERMS_Application.Quotation.ItemsSaleTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import com.erms.ERMS_Application.Quotation.AddParty.AddPartyRepo;
import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import com.erms.ERMS_Application.Quotation.Form.FormRepository;
import com.erms.ERMS_Application.Quotation.Form.FormService;
import com.erms.ERMS_Application.Quotation.ItemsSaleTotal.ItemsSalesTotal;
import com.erms.ERMS_Application.Quotation.ItemsSaleTotal.ItemsSalesTotalRepo;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ItemsSaleTableService {

    @Autowired
    private AddPartyRepo addPartyRepo;

    @Autowired
    private FormService formService; // Assuming FormService is already defined

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private ItemsSalesTableRepo itemsSalesTableRepo;

    @Autowired
    private ItemsSalesTotalRepo itemsSalesTotalRepo;
    
    @Autowired
    private FormRepository formRepo;

    public List<ItemsSaleTableDTO> saveItems(long adId, long fId, long sId, List<ItemsSaleTableDTO> itemsList) {

        // Find the AddPartyEntity using the provided adId
        AddPartyEntity addEntity = addPartyRepo.findById(adId)
                .orElseThrow(() -> new EntityNotFoundException("AddPartyEntity not found with id: " + adId));

        // Find the FormEntity and Sales entity using the provided IDs
        FormEntity formEntity = formService.findByFormId(fId)
                .orElseThrow(() -> new EntityNotFoundException("FormEntity not found with id: " + fId));
        Sales sale = salesRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException("Sales not found with id: " + sId));

        // List to hold saved DTOs
        List<ItemsSaleTableDTO> savedItemsDTOList = new ArrayList<>();

        for (ItemsSaleTableDTO itemDTO : itemsList) {
            // Convert DTO to Entity
            ItemsSaleTableEntity addItem = new ItemsSaleTableEntity();
            addItem.setItems(itemDTO.getItems());
            addItem.setHsn(itemDTO.getHsn());
            addItem.setQty(itemDTO.getQty());
            addItem.setPriceItem(itemDTO.getPriceItem());
            addItem.setDiscount(itemDTO.getDiscount());
            addItem.setTax(itemDTO.getTax());
            addItem.setAmount(itemDTO.getAmount());
            addItem.setSaleDate(LocalDate.now());
            addItem.setDescription(itemDTO.getDescription());
            addItem.setCategory(itemDTO.getCategory());

            // Set references to other entities
            addItem.setFormEntity(formEntity);
            addItem.setSales(sale);
            addItem.setAddPartyEntity(addEntity);

            // Save entity and map it back to DTO
            ItemsSaleTableEntity savedItem = itemsSalesTableRepo.save(addItem);
            ItemsSaleTableDTO savedItemDTO = new ItemsSaleTableDTO(
                    savedItem.getItId(), savedItem.getItems(), savedItem.getHsn(), savedItem.getQty(),
                    savedItem.getPriceItem(), savedItem.getDiscount(), savedItem.getTax(), savedItem.getAmount(),
                    savedItem.getSaleDate(), savedItem.getDescription(), savedItem.getCategory()
            );

            // Add saved DTO to list
            savedItemsDTOList.add(savedItemDTO);
        }

        return savedItemsDTOList;
    }


    // Method to get items by sales ID
    public List<ItemsSaleTableDTO> getItemsBySalesId(long sId) {
        // Find the Sales entity using the provided sId
        Sales sale = salesRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException("Sales not found with id: " + sId));

        // Get the list of items associated with the sales entity
        List<ItemsSaleTableEntity> itemsEntities = itemsSalesTableRepo.findBySales(sale);

        // Convert entity list to DTO list
        return itemsEntities.stream()
                .map(this::convertToDTO) // Assuming you have this method
                .collect(Collectors.toList());
    }

    // Convert entity to DTO
    private ItemsSaleTableDTO convertToDTO(ItemsSaleTableEntity itemEntity) {
        ItemsSaleTableDTO dto = new ItemsSaleTableDTO();
        dto.setItId(itemEntity.getItId());
        dto.setItems(itemEntity.getItems());
        dto.setHsn(itemEntity.getHsn());
        dto.setQty(itemEntity.getQty());
        dto.setPriceItem(itemEntity.getPriceItem());
        dto.setDiscount(itemEntity.getDiscount());
        dto.setTax(itemEntity.getTax());
        dto.setAmount(itemEntity.getAmount());
        dto.setSaleDate(itemEntity.getSaleDate());
        dto.setDescription(itemEntity.getDescription());
        dto.setCategory(itemEntity.getCategory());
        return dto;
    }

    // Get all items
    public List<ItemsSaleTableDTO> getAllItems() {
        List<ItemsSaleTableEntity> itemEntities = itemsSalesTableRepo.findAll();
        List<ItemsSaleTableDTO> itemsDTOList = new ArrayList<>();

        // Convert each entity to DTO
        for (ItemsSaleTableEntity itemEntity : itemEntities) {
            itemsDTOList.add(convertToDTO(itemEntity));
        }

        return itemsDTOList;
    }


    public List<ItemsSaleTableDTO> getRecentItemsBySalesIdAdIdAndDate(long sId, long adId, String saleDate) {
        LocalDate date = LocalDate.parse(saleDate); // Parse the date from string
        List<ItemsSaleTableEntity> items = itemsSalesTableRepo.findRecentItemsBySalesIdAdIdAndDate(sId, adId, date);

        return items.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public List<ItemsSaleTableDTO> getSelectedItemsByFormId(long fId, List<Long> itemIds) {
        // Fetch selected items by form ID and list of item IDs
        List<ItemsSaleTableEntity> items = itemsSalesTableRepo.findSelectedItemsByFormId(fId, itemIds);

        // Convert to DTO
        return items.stream()
                .map(this::convertToDTO) // Assuming you have a method to convert entity to DTO
                .collect(Collectors.toList());
    }




    // Service method to update multiple items
    public TotalsDTO updateItems(long sId, long adId,long fId, List<Long> itIds, List<ItemsSaleTableEntity> updatedItems) {
        if (itIds.size() != updatedItems.size()) {
            throw new IllegalArgumentException("The number of item IDs must match the number of items provided for update.");
        }

        // Fetch existing items by their IDs
        List<ItemsSaleTableEntity> existingItems = itemsSalesTableRepo.findBySalesIdAndAdIdAndItIdIn(sId, adId, itIds);



        if (existingItems.isEmpty()) {
            throw new EntityNotFoundException("No items found for the provided sales ID and advertisement ID.");
        }

        for (int i = 0; i < existingItems.size(); i++) {
            ItemsSaleTableEntity existingItem = existingItems.get(i);
            ItemsSaleTableEntity updatedItem = updatedItems.get(i);

            // Update only the fields that are present in updatedItem
            if (updatedItem.getItems() != null) {
                existingItem.setItems(updatedItem.getItems());
            }
            if (updatedItem.getQty() >= 0) { // Assuming qty can be zero but not negative
                existingItem.setQty(updatedItem.getQty());
            }
            if (updatedItem.getPriceItem() >= 0) { // Assuming price can be zero but not negative
                existingItem.setPriceItem(updatedItem.getPriceItem());
            }
            if (updatedItem.getDiscount() >= 0) {
                existingItem.setDiscount(updatedItem.getDiscount());
            }
            if (updatedItem.getTax() >= 0) {
                existingItem.setTax(updatedItem.getTax());
            }
            if (updatedItem.getAmount() >= 0) {
                existingItem.setAmount(updatedItem.getAmount());
            }
            if(updatedItem.getDescription() != null){
                existingItem.setDescription(updatedItem.getDescription());
            }
            if(updatedItem.getCategory() != null){
                existingItem.setCategory((updatedItem.getCategory()));
            }

            existingItem.setSaleDate(LocalDate.now());

            // Add other fields as necessary
        }

        // Save all updated items back to the database
        itemsSalesTableRepo.saveAll(existingItems);

        // Calculate the total amount after saving the updated items

        float totalAmount = 0;
        float totalDiscount = 0;
        float totalTax = 0;
        for (ItemsSaleTableEntity item : existingItems) {
            // Calculate the amount for this item: priceItem * qty
            if (item.getPriceItem() != 0 && item.getQty() != 0) {

                if ((item.getDiscount() != 0 || item.getTax() != 0) || (item.getDiscount() != 0 || item.getTax() == 0)) {
                    float amount = item.getPriceItem() * item.getQty();
                    item.setAmount(amount);



                    float discountAmount = amount * (item.getDiscount() / 100);
                    item.setAmount(amount-discountAmount);



                    if (item.getTax() != 0) {
                        float tax = amount * (item.getTax() / 100);
                        totalTax += tax;
                        item.setAmount(amount-discountAmount+tax);

                    }

                    totalAmount += amount;
                    totalDiscount += discountAmount;


                } else {
                    totalAmount += item.getPriceItem() * item.getQty();
                    item.setAmount(item.getPriceItem() * item.getQty());
                    if (item.getTax() != 0) {
                        float a = item.getPriceItem() * item.getQty();
                        float t = a * (item.getTax() / 100);
                        totalTax += t;
                        item.setAmount(a+t);
                    }
                }

            }
        }

        System.out.println("total discount amount : " + totalDiscount);

        System.out.println("total Tax  :" + totalTax);

        itemsSalesTableRepo.saveAll(existingItems);
        float finalAmount=totalAmount-totalDiscount+totalTax;

        Sales sale = salesRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException("Sales not found with id: " + sId));

        FormEntity formEntity = formService.findByFormId(fId)
                .orElseThrow(() -> new EntityNotFoundException("FormEntity not found with id: " + fId));
        ItemsSalesTotal ist=itemsSalesTotalRepo.findByFormEntity(formEntity);
        if(ist !=null ){
            //ItemsSalesTotal ist=itemsSalesTotalRepo.findById(fId).orElse(null);
            ist.setFinalAmount(finalAmount);
            ist.setTotalAmount(totalAmount);
            ist.setTotalDiscount(totalDiscount);
            ist.setTotalTax(totalTax);
            ist.setSales(sale);
            ist.setFormEntity(formEntity);
            itemsSalesTotalRepo.save(ist);

        }
        else {
            ItemsSalesTotal item = new ItemsSalesTotal();
            item.setFinalAmount(finalAmount);
            item.setTotalAmount(totalAmount);
            item.setTotalDiscount(totalDiscount);
            item.setTotalTax(totalTax);
            item.setSales(sale);
            item.setFormEntity(formEntity);
            itemsSalesTotalRepo.save(item);
        }




        return new TotalsDTO(finalAmount, totalDiscount, totalTax,totalAmount);
    }


    @Transactional
    public void deleteByItemsId(long itId) {
        itemsSalesTableRepo.deleteById(itId);
    }

    public List<ItemsSaleTableDTO> getFinalItems(long fId) {
        FormEntity form = formRepo.findById(fId).orElse(null);
        
        if (form != null) {
            List<ItemsSaleTableEntity> itemsList = itemsSalesTableRepo.findByFormEntity(form);
            List<ItemsSaleTableDTO> dtoList = new ArrayList<>();
            
            for (ItemsSaleTableEntity item : itemsList) {
                ItemsSaleTableDTO dto = new ItemsSaleTableDTO();
                dto.setItId(item.getItId());
                dto.setItems(item.getItems());
                dto.setHsn(item.getHsn());
                dto.setQty(item.getQty());
                dto.setPriceItem(item.getPriceItem());
                dto.setDiscount(item.getDiscount());
                dto.setTax(item.getTax());
                dto.setAmount(item.getAmount());
                dto.setSaleDate(item.getSaleDate());
                dto.setDescription(item.getDescription());
                dto.setCategory(item.getCategory());
                dtoList.add(dto);
            }
            
            return dtoList;
        }
        
        return null;
    }

}

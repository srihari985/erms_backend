package com.erms.ERMS_Application.Quotation.QuotationList;

import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import com.erms.ERMS_Application.Quotation.AddParty.AddPartyDTO;
import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import com.erms.ERMS_Application.Quotation.AddParty.AddPartyRepo;
import com.erms.ERMS_Application.Quotation.Form.FormDTO;
import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import com.erms.ERMS_Application.Quotation.Form.FormRepository;
import com.erms.ERMS_Application.Quotation.Form.FormService;
import com.erms.ERMS_Application.Quotation.ItemsSaleTable.ItemsSaleTableDTO;
import com.erms.ERMS_Application.Quotation.ItemsSaleTable.ItemsSaleTableEntity;
import com.erms.ERMS_Application.Quotation.ItemsSaleTable.ItemsSalesTableRepo;
import com.erms.ERMS_Application.Quotation.ItemsSaleTotal.ItemsSalesTotal;
import com.erms.ERMS_Application.Quotation.ItemsSaleTotal.ItemsSalesTotalRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationListService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private  QuotationListRepo quotationListRepo;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private FormService formService;

    @Autowired
    private AddPartyRepo addPartyRepo;

    @Autowired
    private ItemsSalesTableRepo itemsSalesTableRepo;

    @Autowired
    private ItemsSalesTotalRepo itemsSalesTotalRepo;


    // Method to get all quotations and return them as a list of DTOs
    public List<QuotationListDto> getAllQuotationsList(long sId) {

        Sales sale = salesRepository.findById(sId)
                .orElseThrow(() -> new EntityNotFoundException("Sales not found with id: " + sId));


        List<QuotationListEntity> quotationEntities = quotationListRepo.findAll(); // Fetch all quotations from the database
        return quotationEntities.stream()
                .map(this::getQuotationListDto) // Convert each entity to DTO
                .collect(Collectors.toList());
    }


    public List<QuotationResponseDTO> getAllQuotations() {
        List<FormEntity> formEntities = formRepository.findAll();
        return formEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<QuotationResponseDTO> getQuotationsBySalesId(Long salesId) {
        List<FormEntity> formEntities = formRepository.findByAddParty_Sales_Id(salesId);
        return formEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private QuotationResponseDTO convertToDto(FormEntity form) {
        QuotationResponseDTO dto = new QuotationResponseDTO();
        dto.setqId(form.getfId());
        dto.setfId(form.getfId());
        dto.setQuotationNumber(form.getQuotationNumber());
        dto.setQuotationDate(form.getQuotationDate());
        dto.setDueDate(form.getDueDate());

        AddPartyEntity addParty = form.getAddParty();
        if (addParty != null) {
            AddPartyDTO addPartyDTO = new AddPartyDTO();
            addPartyDTO.setAdId(addParty.getAdId());
            addPartyDTO.setCustomerName(addParty.getCustomerName());
            dto.setAddPartyDTO(addPartyDTO); // Use AddPartyDTO instead
        }

        // Set istId from ItemsSaleTableEntity if available
        if (form.getItemsSaleTableEntities() != null && !form.getItemsSaleTableEntities().isEmpty()) {
            ItemsSaleTableEntity itemSaleTable = form.getItemsSaleTableEntities().get(0); // Assuming first item is needed
            dto.setIstId(itemSaleTable.getItId()); // Set the itId as istId in the DTO
        }



        dto.setGrandTotal(
                form.getItemsSalesTotals() != null && !form.getItemsSalesTotals().isEmpty()
                        ? form.getItemsSalesTotals().get(0).getGrandTotal()
                        : 0);

        return dto;
    }


    public void deleteQuotationById(Long id) {
        formRepository.deleteById(id);
    }


    // Create a new quotation
    @Transactional
    public QuotationListDto createQuotation(long sId, long adId, long fId) {

        Sales saleEntity = salesRepository.findById(sId)
                .orElseThrow(() -> new RuntimeException("Sales Id not found"));

        AddPartyEntity addParty = addPartyRepo.findById(adId)
                .orElseThrow(() -> new RuntimeException("AddParty Id not found"));

        FormEntity form = formService.findByFormId(fId)
                .orElseThrow(() -> new RuntimeException("Form Id not found"));

        // Create and set up a new QuotationListEntity

        QuotationListEntity quotationEntity = new QuotationListEntity();
        quotationEntity.setSales(saleEntity);
        quotationEntity.setAddPartyEntity(addParty);
        quotationEntity.setFormEntity(form);
        quotationEntity.setQuotationNumber(form.getQuotationNumber());
        quotationEntity.setQuotationDate(form.getQuotationDate());
        quotationEntity.setDueDate(form.getDueDate());
        quotationEntity.setCustomerName(addParty.getCustomerName());
        quotationEntity.setGrandTotal(
                form.getItemsSalesTotals() != null && !form.getItemsSalesTotals().isEmpty()
                        ? form.getItemsSalesTotals().get(0).getGrandTotal()
                        : 0
        );

        // Save the new quotation entity to the database
        quotationEntity = quotationListRepo.save(quotationEntity);

        // Convert the saved entity to a DTO
        QuotationListDto dto = getQuotationListDto(quotationEntity);

        return dto;
    }

    private QuotationListDto getQuotationListDto(QuotationListEntity quotationEntity) {
        QuotationListDto dto = new QuotationListDto();

        dto.setqId(quotationEntity.getqId());
        dto.setSaleId(quotationEntity.getSales().getId());
        dto.setAdId(quotationEntity.getAddPartyEntity().getAdId());
        dto.setfId(quotationEntity.getFormEntity().getfId());
        dto.setQuotationNumber(quotationEntity.getQuotationNumber());
        dto.setQuotationDate(quotationEntity.getQuotationDate());
        dto.setDueDate(quotationEntity.getDueDate());
        dto.setCustomerName(quotationEntity.getCustomerName());
        dto.setGrandTotal(quotationEntity.getGrandTotal());
        return dto;
    }

    ///////////////// update quotation //////////////////
    public QuotationListDto updateQuotationByFormId(long fId, QuotationListDto quotationUpdateDto) {
        FormEntity form = formService.findByFormId(fId)
                .orElseThrow(() -> new RuntimeException("Form Id not found"));

        QuotationListEntity quotation = quotationListRepo.findByFormEntity(form);
        ItemsSalesTotal total = itemsSalesTotalRepo.findByFormEntity(form);

        // Fetch AddPartyEntity if form has a related AddPartyEntity
        AddPartyEntity addParty = (form.getAddParty() != null)
                ? quotationListRepo.findByAddPartyEntity(form.getAddParty())
                : null;

        // Update fields if they are provided in the DTO
        if (quotationUpdateDto.getCustomerName() != null) {
            quotation.setCustomerName(quotationUpdateDto.getCustomerName());
        }
        if (quotationUpdateDto.getDueDate() != null) {
            quotation.setDueDate(quotationUpdateDto.getDueDate());
        }
        if (quotationUpdateDto.getQuotationDate() != null) {
            quotation.setQuotationDate(quotationUpdateDto.getQuotationDate());
        }
        if (quotationUpdateDto.getQuotationNumber() != null) {
            quotation.setQuotationNumber(quotationUpdateDto.getQuotationNumber());
        }
        if (quotationUpdateDto.getGrandTotal() != 0) {
            quotation.setGrandTotal(quotationUpdateDto.getGrandTotal());
        }

        quotation.setFormEntity(form);


        // Save updated quotation
        quotationListRepo.save(quotation);

        // Populate DTO to return
        QuotationListDto dto = new QuotationListDto();
        if (addParty != null) {
            dto.setAdId(addParty.getAdId());
        }
        dto.setqId(quotation.getqId());
        dto.setSaleId(quotation.getSales().getId());
        dto.setfId(fId);
        dto.setCustomerName(quotation.getCustomerName());
        dto.setDueDate(quotation.getDueDate());
        dto.setQuotationDate(quotation.getQuotationDate());
        dto.setQuotationNumber(quotation.getQuotationNumber());
        dto.setGrandTotal(total != null ? total.getGrandTotal() : 0);

        return dto;
    }



    // Method to get AddPartyEntity by fId
    public AddPartyDTO getAddPartyDetailsByFid(long fId) {
        FormEntity formEntity = formRepository.findById(fId)
                .orElseThrow(() -> new RuntimeException("Form Id not found"));
        AddPartyEntity addPartyEntity = formEntity.getAddPartyEntity();
        return new AddPartyDTO(addPartyEntity);  // Assuming AddPartyDTO has a constructor to convert AddPartyEntity
    }

    // Method to get FormEntity by fId
    public FormDTO getFormDetailsByFid(long fId) {
        FormEntity formEntity = formRepository.findById(fId)
                .orElseThrow(() -> new RuntimeException("Form Id not found"));
        return new FormDTO(formEntity);
    }

    // Method to get ItemsSaleTableEntity by fId
    public List<ItemsSaleTableDTO> getItemsSaleTableDetailsByFid(long fId) {
        List<ItemsSaleTableEntity> itemsSaleTableEntities = itemsSalesTableRepo.findByFormEntity_FId(fId);
        if (itemsSaleTableEntities.isEmpty()) {
            throw new RuntimeException("ItemsSaleTableEntities not found for Form Id: " + fId);
        }
        // Map entities to DTOs
        return itemsSaleTableEntities.stream()
                .map(ItemsSaleTableDTO::new) // Use the DTO constructor
                .collect(Collectors.toList());
    }




}

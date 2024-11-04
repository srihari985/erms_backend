package com.erms.ERMS_Application.Quotation.ItemsTable;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import com.erms.ERMS_Application.Quotation.Form.FormService;

@Service
public class ItemsTableService {

	@Autowired
	private ItemsTableRepository itemsTableRepo;

	@Autowired
	private FormService formService;

	public ResponseEntity<?> saveItems(long fId, ItemsTableEntity addItems) {
		Optional<FormEntity> formOptional = formService.findByFormId(fId);

		if (!formOptional.isPresent()) {
			return new ResponseEntity<>("FormEntity not found", HttpStatus.NOT_FOUND);
		}

		FormEntity addForm = formOptional.get();
		addItems.setFormEntity(addForm);

		ItemsTableEntity savedItems = itemsTableRepo.save(addItems);
		return new ResponseEntity<>(toDTO(savedItems), HttpStatus.CREATED);
	}

	public ResponseEntity<?> getItemsByFormId(long fId) {
		Optional<FormEntity> formDetails = formService.findByFormId(fId);

		if (formDetails.isPresent()) {
			List<ItemsTableEntity> items = formDetails.get().getItemsTable();
			List<ItemsTableDTO> itemsDTO = items.stream().map(this::toDTO).collect(Collectors.toList());
			return new ResponseEntity<>(itemsDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Items not found", HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<?> getAllItems() {
		List<ItemsTableEntity> allItems = itemsTableRepo.findAll();
		List<ItemsTableDTO> allItemsDTO = allItems.stream().map(this::toDTO).collect(Collectors.toList());
		return new ResponseEntity<>(allItemsDTO, HttpStatus.OK);
	}

	public ResponseEntity<?> updateItemsTable(long itId, ItemsTableEntity itemsTable) {
		ItemsTableEntity updateItems = itemsTableRepo.findById(itId).orElseThrow();

		Optional.ofNullable(itemsTable.getItems()).ifPresent(updateItems::setItems);
		Optional.ofNullable(itemsTable.getHsn()).ifPresent(updateItems::setHsn);
		Optional.ofNullable(itemsTable.getQty()).ifPresent(updateItems::setQty);
		Optional.ofNullable(itemsTable.getPriceItem()).ifPresent(updateItems::setPriceItem);
		Optional.ofNullable(itemsTable.getDiscount()).ifPresent(updateItems::setDiscount);
		Optional.ofNullable(itemsTable.getTax()).ifPresent(updateItems::setTax);
		Optional.ofNullable(itemsTable.getAmount()).ifPresent(updateItems::setAmount);
		Optional.ofNullable(itemsTable.getDescription()).ifPresent(updateItems::setDescription);
		Optional.ofNullable(itemsTable.getCategory()).ifPresent(updateItems::setCategory);

		ItemsTableEntity savedItem = itemsTableRepo.save(updateItems);
		return new ResponseEntity<>(toDTO(savedItem), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteItemById(long itId) {
		itemsTableRepo.deleteById(itId);
		return new ResponseEntity<>("ItemsTable deleted successfully", HttpStatus.OK);
	}

	private ItemsTableDTO toDTO(ItemsTableEntity item) {
		ItemsTableDTO dto = new ItemsTableDTO();
		dto.setItId(item.getItId());
		dto.setItems(item.getItems());
		dto.setHsn(item.getHsn());
		dto.setQty(item.getQty());
		dto.setPriceItem(item.getPriceItem());
		dto.setDiscount(item.getDiscount());
		dto.setTax(item.getTax());
		dto.setAmount(item.getAmount());
		dto.setDescription(item.getDescription());
		dto.setCategory(item.getCategory());
		return dto;
	}
}

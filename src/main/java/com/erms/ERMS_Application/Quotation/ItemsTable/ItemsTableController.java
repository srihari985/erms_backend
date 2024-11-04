package com.erms.ERMS_Application.Quotation.ItemsTable;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itemstable")
public class ItemsTableController {

	@Autowired
	private ItemsTableService itemsTableService;

	@PostMapping("/saveitems/{fId}")
	public ResponseEntity<?> saveItems(@PathVariable long fId, @RequestBody ItemsTableEntity addItems) {
		return itemsTableService.saveItems(fId, addItems);
	}

	@GetMapping("/getItemsByFormId/{fId}")
	public ResponseEntity<?> getItemsByFormId(@PathVariable long fId) {
		return itemsTableService.getItemsByFormId(fId);
	}

	@GetMapping("/getAllForms")
	public ResponseEntity<?> getAllItems() {
		return itemsTableService.getAllItems();
	}

	@PatchMapping("/update/{itId}")
	public ResponseEntity<?> updateItemsTable(@PathVariable long itId, @RequestBody ItemsTableEntity updateItem) {
		return itemsTableService.updateItemsTable(itId, updateItem);
	}

	@DeleteMapping("/{itId}")
	public ResponseEntity<?> deleteItemById(@PathVariable("itId") long itId) {
		return itemsTableService.deleteItemById(itId);
	}
}

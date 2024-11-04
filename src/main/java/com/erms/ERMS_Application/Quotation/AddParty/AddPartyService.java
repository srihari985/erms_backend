package com.erms.ERMS_Application.Quotation.AddParty;

import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddPartyService {

	@Autowired
	private AddPartyRepo addPartyRepo;

	@Autowired
	private SalesRepository salesRepo;

	public AddPartyDTO createParty(long salesId, AddPartyEntity addParty) {
		Sales sale = salesRepo.findById(salesId).orElse(null);
		addParty.setSales(sale);
		AddPartyEntity savedParty = addPartyRepo.save(addParty);
		return convertToDTO(savedParty);
	}

	public Optional<AddPartyDTO> getParty(long adId) {
		Optional<AddPartyEntity> partyEntityOpt = addPartyRepo.findById(adId);
		return partyEntityOpt.map(this::convertToDTO);
	}

	public List<AddPartyDTO> getAllParties() {
		List<AddPartyEntity> allParties = addPartyRepo.findAll();
		return allParties.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	public AddPartyDTO updateAddParty(long adId, AddPartyEntity addParty) {
		AddPartyEntity updatePartial = addPartyRepo.findById(adId).orElseThrow();

		Optional.ofNullable(addParty.getCustomerName()).ifPresent(updatePartial::setCustomerName);
		Optional.ofNullable(addParty.getMobileNumber()).ifPresent(updatePartial::setMobileNumber);
		Optional.ofNullable(addParty.getBillingAddress()).ifPresent(updatePartial::setBillingAddress);
		Optional.ofNullable(addParty.getState()).ifPresent(updatePartial::setState);
		Optional.ofNullable(addParty.getPincode()).ifPresent(updatePartial::setPincode);
		Optional.ofNullable(addParty.getCity()).ifPresent(updatePartial::setCity);
		Optional.ofNullable(addParty.getShippingAddress()).ifPresent(updatePartial::setShippingAddress);
		Optional.ofNullable(addParty.getShippingCity()).ifPresent(updatePartial::setShippingCity);
		Optional.ofNullable(addParty.getShippingState()).ifPresent(updatePartial::setShippingState);
		Optional.ofNullable(addParty.getShippingPincode()).ifPresent(updatePartial::setShippingPincode);
		Optional.ofNullable(addParty.getGstIn()).ifPresent(updatePartial::setGstIn);

		AddPartyEntity updatedEntity = addPartyRepo.save(updatePartial);
		return convertToDTO(updatedEntity);
	}

	public void deleteByPartyId(long adId) {
		addPartyRepo.deleteById(adId);
	}

	private AddPartyDTO convertToDTO(AddPartyEntity addPartyEntity) {
		AddPartyDTO dto = new AddPartyDTO();
		dto.setAdId(addPartyEntity.getAdId());
		dto.setCustomerName(addPartyEntity.getCustomerName());
		dto.setMobileNumber(addPartyEntity.getMobileNumber());
		dto.setBillingAddress(addPartyEntity.getBillingAddress());
		dto.setState(addPartyEntity.getState());
		dto.setPincode(addPartyEntity.getPincode());
		dto.setCity(addPartyEntity.getCity());
		dto.setShippingAddress(addPartyEntity.getShippingAddress());
		dto.setShippingState(addPartyEntity.getShippingState());
		dto.setShippingPincode(addPartyEntity.getShippingPincode());
		dto.setShippingCity(addPartyEntity.getShippingCity());
		dto.setGstIn(addPartyEntity.getGstIn());
		dto.setSalesId(addPartyEntity.getSales() != null ? addPartyEntity.getSales().getId() : 0L);
		return dto;
	}
}

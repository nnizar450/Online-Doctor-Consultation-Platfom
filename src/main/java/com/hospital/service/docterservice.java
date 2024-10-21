package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.entity.docterreg;
import com.hospital.repository.DocterregRepository;

@Service
public class docterservice {

	@Autowired
	DocterregRepository docrepo;

	@Autowired
	DocterregRepository docterregRepository;

	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return docrepo.existsByEmail(email);
	}

	public List<docterreg> getAlloffer() {

		return docterregRepository.findAll();

	}

	public void updateData(Long id) {
		Optional<docterreg> optionalEntity = docterregRepository.findById(id);
		if (optionalEntity.isPresent()) {
			docterreg entity = optionalEntity.get();
			entity.setStatus("Accept");
			docterregRepository.save(entity);
		} else {
			// Handle not found scenario
		}
	}

	public void updateDataa(Long id) {
		Optional<docterreg> optionalEntity = docterregRepository.findById(id);
		if (optionalEntity.isPresent()) {
			docterreg entity = optionalEntity.get();
			entity.setStatus("reject");
			docterregRepository.save(entity);
		} else {
			// Handle not found scenario
		}
	}

}

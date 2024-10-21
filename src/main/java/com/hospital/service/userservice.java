package com.hospital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hospital.entity.UserAppointment;
import com.hospital.entity.docterreg;
import com.hospital.repository.UserAppointmentRepository;
import com.hospital.repository.UserRepository;

@Service
public class userservice {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserAppointmentRepository userAppointmentRepo;

	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

	public List<UserAppointment> getAlldata() {
		// TODO Auto-generated method stub
		return userAppointmentRepo.findAll();
	}

	public void updateData(Long id) {
		Optional<UserAppointment> optionalEntity = userAppointmentRepo.findById(id);
		if (optionalEntity.isPresent()) {
			UserAppointment entity = optionalEntity.get();
			entity.setStatus("Appointed");
			userAppointmentRepo.save(entity);
		} else {
			// Handle not found scenario
		}
	}

	public void updateDataa(Long id) {
		Optional<UserAppointment> optionalEntity = userAppointmentRepo.findById(id);
		if (optionalEntity.isPresent()) {
			UserAppointment entity = optionalEntity.get();
			entity.setStatus("reject");
			userAppointmentRepo.save(entity);
		} else {
			// Handle not found scenario
		}
	}

	public UserAppointment saveFile(MultipartFile file) {
		String docname = file.getOriginalFilename();
		try {
			UserAppointment doc = new UserAppointment(docname, file.getContentType(), file.getBytes());
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Optional<UserAppointment> getFile(Integer fileId) {
		return userAppointmentRepo.findById(fileId);
	}

	public List<UserAppointment> getFiles() {
		return userAppointmentRepo.findAll();
	}

}

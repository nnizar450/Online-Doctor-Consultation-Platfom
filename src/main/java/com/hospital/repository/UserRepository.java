package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.UserAppointment;
import com.hospital.entity.userentity;

public interface UserRepository extends JpaRepository<userentity, Long> {

	boolean existsByEmail(String email);

	userentity findByEmail(String email);

	UserAppointment save(UserAppointment doc);

}

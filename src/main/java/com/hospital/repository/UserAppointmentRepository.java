package com.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.hospital.entity.UserAppointment;

public interface UserAppointmentRepository extends JpaRepository<UserAppointment, Long> {

	List<UserAppointment> findByFname(@Param("doctype") String doctype);

	List<UserAppointment> findByDoctertype(@Param("doctertype") String doctertype);

	List<UserAppointment> findAllByEmail(String ob);

	Optional<UserAppointment> findById(Integer fileId);

	List<UserAppointment> findByLname(String uemail);

}

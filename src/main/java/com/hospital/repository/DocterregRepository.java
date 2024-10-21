package com.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.docterreg;

public interface DocterregRepository extends JpaRepository<docterreg, Long> {

	public String save(DocterregRepository docrepo);

	public docterreg findByEmail(String email);

	public boolean existsByEmail(String email);

}

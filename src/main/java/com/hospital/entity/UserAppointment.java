package com.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class UserAppointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String fname;
	private String lname;
	private String email;
	private String mobile;
	private String doctertype;

	private String hospitalname;
	private String date;
	private String description;
	private String status;
	private String phealth;
	private String image;
	private String docemail;

	public String getDocemail() {
		return docemail;
	}

	public void setDocemail(String docemail) {
		this.docemail = docemail;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEncryptphealth() {
		return encryptphealth;
	}

	public void setEncryptphealth(String encryptphealth) {
		this.encryptphealth = encryptphealth;
	}

	private String encryptphealth;

	private String fileName;
	private String fileType;

	@Lob
	@Column(columnDefinition = "LONGTEXT")
	private byte[] filedata;

	// Constructors, getters, setters

	public UserAppointment(String fileName, String fileType, byte[] filedata) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.filedata = filedata;
	}

	public UserAppointment() {
		super();
	}

	// Other methods

	public void setFile1(String fileName) {
		this.fileName = fileName;
	}

	// Other getters and setters

	// Getter and setter for filedata should also be considered if needed
	// e.g., if you want to retrieve filedata separately.

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDoctertype() {
		return doctertype;
	}

	public void setDoctertype(String doctertype) {
		this.doctertype = doctertype;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhealth() {
		return phealth;
	}

	public void setPhealth(String phealth) {
		this.phealth = phealth;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	public void setFile(String fileName2) {
		// TODO Auto-generated method stub

	}
}
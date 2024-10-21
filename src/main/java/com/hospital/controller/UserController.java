package com.hospital.controller;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hospital.entity.UserAppointment;
import com.hospital.entity.docterreg;
import com.hospital.entity.userentity;
import com.hospital.repository.UserAppointmentRepository;
import com.hospital.repository.UserRepository;
import com.hospital.service.userservice;
import com.hospital.service.EmailService;
import com.hospital.service.EmailService1;

import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	userservice UserService;

	@Autowired
	UserAppointmentRepository userAppointmentRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	EmailService1 emailServicee;

	@GetMapping("/user")
	public String method() {

		return "userlog";

	}

	@GetMapping("/userlog")
	public String Userlog() {

		return "userlog";

	}

	@GetMapping("/userreg")
	public String userregister() {

		return "userreg";

	}

	@PostMapping("/uregister")
	public String registerUser(@ModelAttribute userentity user, Model model) {

		boolean a = UserService.checkEmail(user.getEmail());

		String message = (a == true) ? "This Eail was Already Exist.." : "Register successfully..";
		model.addAttribute("message", message);

		if (a == false) {
			userentity ob = new userentity();

			ob.setName(user.getName());
			ob.setEmail(user.getEmail());
			ob.setPass(user.getPass());
			ob.setCpass(user.getCpass());
			ob.setStatus("");

			userRepository.save(user);

			return "userlog";
		} else {

			return "userreg";

		}
	}

	@PostMapping("/ulogin")
	public String docterlogin(@ModelAttribute userentity user, Model m, HttpSession session) {

		session.setAttribute("uemail", user.getEmail());
		userentity existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser != null && existingUser.getPass().equals(user.getPass())) {

			System.out.println("" + existingUser.getPass());
			System.out.println("" + user.getEmail());

			return "usermain";

		} else {

			m.addAttribute("msg", "Invalid username or password");

			return "userlog";
		}

	}

	@PostMapping("/uappointment")
	public String appointment(@ModelAttribute UserAppointment userAppointment, Model model, HttpSession session,
			@RequestParam("files") MultipartFile files) {

		try {

			String docname = files.getOriginalFilename();

			UserAppointment doc = new UserAppointment(docname, files.getContentType(), files.getBytes());
			// Create a new UserAppointment object from the form data
			UserAppointment userAppointmentEntity = new UserAppointment();
			userAppointmentEntity.setFname(userAppointment.getFname());
			userAppointmentEntity.setLname(userAppointment.getLname());
			userAppointmentEntity.setLname(userAppointment.getEmail());

			System.out.println("email " + userAppointment.getEmail());

			userAppointmentEntity.setDoctertype(userAppointment.getDoctertype());
			userAppointmentEntity.setHospitalname(" ");
			userAppointmentEntity.setMobile(userAppointment.getMobile());
			userAppointmentEntity.setDescription(userAppointment.getDescription());
			userAppointmentEntity.setDate(userAppointment.getDate());
			userAppointmentEntity.setStatus("request");

			userAppointmentEntity.setFileName(docname);
			userAppointmentEntity.setFiledata(doc.getFiledata());
			userAppointmentEntity.setFileType(doc.getFileType());

			userAppointmentRepo.save(userAppointmentEntity);

			// Redirect to the usermain page after successful processing
			return "usermain";
		} catch (Exception e) {
			e.printStackTrace();
			// Handle the exception, you might want to add proper error handling and
			// redirect to an error page
			return "error";
		}
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId,
			@RequestParam(required = false) Boolean download) {
		java.util.Optional<UserAppointment> optionalDoc = UserService.getFile(fileId);

		if (optionalDoc.isPresent()) {
			UserAppointment doc = optionalDoc.get();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			headers.setContentDisposition(
					ContentDisposition.builder(download != null && download ? "attachment" : "inline")
							.filename(doc.getFileName()).build());

			return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(doc.getFiledata()));
		} else {
			// Handle the case where the file with the given ID is not found
			return ResponseEntity.notFound().build();
		}
	}

//
//	@GetMapping("/downloadFile/{fileId}")
//	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId, @RequestParam(required = false) Boolean download) {
//		java.util.Optional<UserAppointment> optionalDoc = UserService.getFile(fileId);
//
//	    if (optionalDoc.isPresent()) {
//	    	UserAppointment doc = optionalDoc.get();
//
//	    	
//	    	
//	    	if(optionalDoc.isPresent()) {
//	    		
//	    		String input = JOptionPane.showInputDialog(null, "Enter Input:", "Dialog for Input",
//	    		        JOptionPane.WARNING_MESSAGE);
//	    		    System.out.println(input);
//	    		
//	    		
//	    		
//	    		
//	    		
//	    		 HttpHeaders headers = new HttpHeaders();
//	 	        headers.setContentType(MediaType.parseMediaType("application/pdf"));
//	 	        headers.setContentDisposition(ContentDisposition.builder(download != null && download ? "attachment" : "inline")
//	 	                .filename(doc.getFileName()).build());
//
//	 	        return ResponseEntity.ok()
//	 	                .headers(headers)
//	 	                .body(new ByteArrayResource(doc.getFiledata()));
//	    	}
//	       
//	    } else {
//	        // Handle the case where the file with the given ID is not found
//	        return ResponseEntity.notFound().build();
//	    }
//		return null;
//	}
//	

	@GetMapping("/userapplication")
	public String userapplicationview(Model model) {

		List<UserAppointment> UAppointment = UserService.getAlldata();
		System.out.println("appointment" + UAppointment);
		model.addAttribute("appointment", UAppointment);

		return "userapplicationview";
	}

	@GetMapping("/uaccept/{id}")
	public String accept(@PathVariable("id") Long id) {
		UserService.updateData(id);

		return "redirect:/userapplication";

	}

	@GetMapping("/ureject/{id}")
	public String accept1(@PathVariable("id") Long id) {
		UserService.updateDataa(id);

		return "redirect:/userapplication";

	}

	@GetMapping("/appointstatus")
	public String appointstatus(HttpSession session, Model model) {

		String uemail = (String) session.getAttribute("uemail");

		List<UserAppointment> user = userAppointmentRepo.findByLname(uemail);

		model.addAttribute("usercom", user);

		return "appintstatus";

	}

	@GetMapping("/userview")
	public String userviewdetails(Model model, HttpSession session) {

		List<UserAppointment> user = UserService.getAlldata();
		System.out.println("doclist   " + user);
		model.addAttribute("ureport", user);

		return "userviewreport";

	}

	@GetMapping("/userviewreport")
	public String userview(Model model, HttpSession session) {

		String email = (String) session.getAttribute("uemail");

		int min = 2000;
		int max = 4000;
		int b = (int) (Math.random() * (max - min + 1) + min);

		System.out.println("otp " + b);

		String otp = String.valueOf(b);

		// Send OTP to the user's email
		emailServicee.sendEmail(email, "Health Report Otp", otp);

		// Add the OTP to the model for later verification (in case you need it on the
		// client-side)
		model.addAttribute("otp", String.valueOf(b));

		return "userotp";
	}

//	
//	@PostMapping("/uploadFiles")
//	public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//		for (MultipartFile file: files) {
//			  String docname = file.getOriginalFilename();
//			  try {
//				  UserAppointment doc = new UserAppointment(docname,file.getContentType(),file.getBytes());
//				  return userRepository.save(doc);
//			  }
//			  catch(Exception e) {
//				  e.printStackTrace();
//			  }
//			
//		}
//		return "redirect:/file";
//	}
//	@GetMapping("/downloadFile/{fileId}")
//	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
//		Doc doc = docStorageService.getFile(fileId).get();
//		return ResponseEntity.ok()
//				.contentType(MediaType.parseMediaType(doc.getDocType()))
//				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
//				.body(new ByteArrayResource(doc.getData()));
//	}

}

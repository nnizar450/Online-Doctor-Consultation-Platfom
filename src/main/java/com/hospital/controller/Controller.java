package com.hospital.controller;

import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hospital.entity.Hospitallog;
import com.hospital.entity.UserAppointment;
import com.hospital.entity.docterreg;
import com.hospital.repository.DocterregRepository;
import com.hospital.repository.UserAppointmentRepository;
import com.hospital.service.EmailService;
import com.hospital.service.EmailService1;
import com.hospital.service.docterservice;

import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	DocterregRepository docrepo;

	@Autowired
	docterservice docservice;

	@Autowired
	UserAppointmentRepository userAppointmentRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	EmailService1 emailServicee;

	@GetMapping("/")
	public String index() {

		return "index";
	}

	@GetMapping("/loginn")
	public String home() {

		return "loginn";
	}

	@GetMapping("/logout")
	public String logout() {

		return "index";
	}

	@GetMapping("/doclog")
	public String doclog() {

		return "docterlog";
	}

	@GetMapping("/docreg")
	public String docreg() {

		return "docterreg";
	}

	@GetMapping("/docappointment")
	public String docappointment(Model model, HttpSession session) {

		String uemail = (String) session.getAttribute("uemail");

		model.addAttribute("uemail", uemail);

		return "docappintment";
	}

	@GetMapping("/success")
	public String showSuccessPage() {
		return "docterlog";
	}

	@PostMapping("/login")
	public String loginUser(@ModelAttribute Hospitallog hos, Model m, HttpSession session) {

		if ((hos.getEmail().equals("medease@gmail.com") && hos.getPassword().equals("medease"))) {

			session.setAttribute("htype", hos.getHospitaltype());

			System.out.println("success");

			return "hospitalhome";
		} else {

			System.out.println("faileur");

			m.addAttribute("msg", "Invalid username or password");

			return "loginn";
		}

	}

	@PostMapping("/docterreg")
	public String docterreg(@RequestParam("fname") String firstname, @RequestParam("image") MultipartFile Image,
			@RequestParam("email") String Email, @RequestParam("address") String Address,
			@RequestParam("state") String State, @RequestParam("pcode") String Pcode,
			@RequestParam("doctype") String Doctype, @RequestParam("dob") String DOB,
			@RequestParam("cpass") String Cpassword, @RequestParam("ccode") String Ccode,
			@RequestParam("gender") String Gender, @RequestParam("pass") String Pass,
			@RequestParam("phone") String Phone, Model model) {
		// Create a new Dress object

		docterreg ob = new docterreg();

		ob.setFname(firstname);
		ob.setLname(" ");
		ob.setEmail(Email);
		ob.setAddress(Address);
		ob.setState(State);
		ob.setPcode(Pcode);
		ob.setDoctype(Doctype);
		ob.setDob(DOB);
		ob.setCpass(Cpassword);
		ob.setCcode(Ccode);
		ob.setGender(Gender);
		ob.setPhone(Phone);
		ob.setPass(Pass);
		ob.setStatus("request");

		boolean a = docservice.checkEmail(Email);

		String message = (a == true) ? "This Mail was Already Exist.." : "Register successfully..";
		model.addAttribute("msg", message);

		if (a == false) {

			try {
				// Save the dress image
				String fileName = Image.getOriginalFilename();
				byte[] imageBytes = Image.getBytes();

				// Specify the directory where the image will be saved
				String directory = "src/main/resources/static/images/";

				// Create the directory if it doesn't exist
				Files.createDirectories(Paths.get(directory));

				// Specify the image file path
				Path imagePath = Paths.get(directory + fileName);

				// Write the image bytes to the specified path
				Files.write(imagePath, imageBytes);

				ob.setImage(fileName);
			} catch (IOException e) {
				// Handle the exception (e.g., log the error, show an error message)
				e.printStackTrace();
			}

			docrepo.save(ob);

			return "success";

		} else {

			return "docterreg";

		}

	}

	@PostMapping("/docterlogin")
	public String docterlogin(@ModelAttribute docterreg docreg, Model m, HttpSession session) {

		String status = "Accept";
		docterreg existingUser = docrepo.findByEmail(docreg.getEmail());
		if (existingUser != null && existingUser.getPass().equals(docreg.getPass())
				&& existingUser.getStatus().equals(status)) {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(dtf.format(now));

			existingUser.setIntime(dtf.format(now));
			existingUser.setOuttime("online");
			docrepo.save(existingUser);

			session.setAttribute("docemail", docreg.getEmail());
			System.out.println("" + existingUser.getPass());
			System.out.println("" + docreg.getEmail());

			return "docterhome";

		} else {

			m.addAttribute("msg", "Invalid username or password");

			return "docterlog";
		}

	}

	@GetMapping("/doclogout")
	public String doclogout(HttpSession session) {
		System.out.println("email  enter");

		String ob = (String) session.getAttribute("docemail");

		docterreg existingUser = docrepo.findByEmail(ob);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		existingUser.setOuttime(dtf.format(now));

		docrepo.save(existingUser);

		return "redirect:/doclog";

	}

	@GetMapping("/doctermanage")
	public String offer(Model model) {

		List<docterreg> doclist = docservice.getAlloffer();
		System.out.println("doclist   " + doclist);
		model.addAttribute("doclist", doclist);

		return "doctermanage";

	}

	@GetMapping("/accept/{id}")
	public String accept(@PathVariable("id") Long id) {
		docservice.updateData(id);

		return "redirect:/doctermanage";

	}

	@GetMapping("/reject/{id}")
	public String accept1(@PathVariable("id") Long id) {
		docservice.updateDataa(id);
		return "redirect:/doctermanage";

	}

	@GetMapping("/docact/{id}")
	public String docact(@PathVariable("id") long id, Model model) {

		Optional<docterreg> ob = docrepo.findById(id);

		if (ob.isPresent()) {
			docterreg entity = ob.get();

			model.addAttribute("doc", entity);

			return "docactivity";
		} else {

			return "doctermanage";
		}

	}

	@GetMapping("/viewpatient")
	public String viewpatient(HttpSession session, Model model) {

		String ob = (String) session.getAttribute("docemail");

		docterreg existingUser = docrepo.findByEmail(ob);

		if (existingUser != null) {
			String name = existingUser.getFname();
			System.out.println("doctertype  " + name);

			List<UserAppointment> userAppointments = userAppointmentRepository.findByDoctertype(name);

			if (!userAppointments.isEmpty()) {

				model.addAttribute("userappointments", userAppointments);
				System.out.println(" userappointment  " + userAppointments.toString());

				return "patient";
			} else {
				// Handle the case where no appointments were found for the doctor.
			}
		} else {
			// Handle the case where no doctor was found for the provided email.
		}

		return "index";
	}

	@GetMapping("/report/{id}")
	public String report(@PathVariable("id") Long id, HttpSession session) {

		session.setAttribute("id", id);

		return "reportpage";
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("phealth") String phealth, @RequestParam("file") MultipartFile file,
			HttpSession session) throws MessagingException, jakarta.mail.MessagingException {
		UserAppointment userappoint = new UserAppointment();

		Long id = (Long) session.getAttribute("id");

		Optional<UserAppointment> userappint = userAppointmentRepository.findById(id);
		if (userappint.isPresent()) {
			UserAppointment user = userappint.get();

			user.setPhealth(phealth);

			try {
				// Save the dress image
				String fileName = file.getOriginalFilename();
				byte[] imageBytes = file.getBytes();

				// Specify the directory where the image will be saved
				String directory = "src/main/resources/static/images/";

				// Create the directory if it doesn't exist
				Files.createDirectories(Paths.get(directory));

				// Specify the image file path
				Path imagePath = Paths.get(directory + fileName);

				// Write the image bytes to the specified path
				Files.write(imagePath, imageBytes);

				user.setImage(fileName);
			} catch (IOException e) {
				// Handle the exception (e.g., log the error, show an error message)
				e.printStackTrace();
			}

			String encryptdata = AES.encrypt(phealth);

			System.out.println("encrypttt " + encryptdata);

			user.setStatus("completed");

			user.setEncryptphealth(encryptdata);

			String docemail = session.getAttribute("docemail").toString();
			user.setDocemail(docemail);

			userAppointmentRepository.save(user);

		}

		String uemail = (String) session.getAttribute("uemail");

		List<UserAppointment> userAppointments = userAppointmentRepository.findByLname(uemail);
		for (UserAppointment user : userAppointments) {

			if ("completed".equals(user.getStatus())) {

				System.out.println("enter");

				emailService.sendEmail(uemail, "Medical Report", ((UserAppointment) user).getPhealth(), file);

				return "docterhome";
			}

		}

		return "";
	}

	@GetMapping("/docview")
	public String docview(Model model, HttpSession session) {
		String ob = (String) session.getAttribute("docemail");

		docterreg existingUser = docrepo.findByEmail(ob);

		if (existingUser != null) {
			String fname = existingUser.getFname();
			System.out.println("doctertype  " + fname);

			List<UserAppointment> userAppointments = userAppointmentRepository.findByDoctertype(fname);

			if (!userAppointments.isEmpty()) {

				model.addAttribute("userappointments", userAppointments);
				System.out.println(" userappointment  " + userAppointments.toString());

				model.addAttribute("userlist", userAppointments);

				return "docviewpatient";

			}
		}
		return "error";

	}

	@GetMapping("/docotp")
	public String docotp(Model model, HttpSession session) {
		String docemail = (String) session.getAttribute("docemail");

		int min = 2000;
		int max = 4000;
		int b = (int) (Math.random() * (max - min + 1) + min);

		System.out.println("otp " + b);

		String otp = String.valueOf(b);

		// Send OTP to the user's email
		emailServicee.sendEmail(docemail, "Health Report Otp", otp);

		// Add the OTP to the model for later verification (in case you need it on the
		// client-side)
		model.addAttribute("otp", String.valueOf(b));

		return "docotp";

	}

}

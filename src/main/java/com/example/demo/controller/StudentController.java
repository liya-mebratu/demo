
package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.services.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

//	@GetMapping
//	public List<Student> getAllStudents() {
//		return studentRepository.findAll();
//	}

	// localhost:8080/api/students/1
	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id, @RequestHeader Map<String, String> headers) {
		System.out.println(headers);
		String userId = headers.get("user-id");
		if (!userId.equals("liya")) {

			throw new UnauthorizedUserException(userId);

//			return student.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
//					.orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
			// return student.map(ResponseEntity::ok).orElseGet(() ->
			// ResponseEntity.notFound().build());
		} else {
			Optional<Student> student = studentService.getStudentById(id);
			// return new ResponseEntity<>(student, HttpStatus.OK);

			if (!student.isPresent()) {
				throw new ResourceNotFoundException("Student not found with id: " + id);

			}
			return new ResponseEntity<>(student.get(), HttpStatus.OK);

		}

	}

	// localhost:8080/api/students/v1?student_id=1&limit=10
	@GetMapping("/v1")
	public ResponseEntity<Student> getStudentById2(@RequestParam(value = "student_id") Long id,
			@RequestParam(value = "limit") Long limit) {
		Optional<Student> student = studentService.getStudentById(id);
		return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> createStudent(@RequestBody List<Student> student) {

		student.forEach(s -> studentService.save(s));
		// List<Student> studentList = student.stream().map(e ->
		// studentService.save(e)).collect(Collectors.toList());
		// or return studentRepository.saveAll(student);
		// return studentList;
		// return studentRepository.save(student);

		return ResponseEntity.ok().build();
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
//		Optional<Student> optionalStudent = studentService.getStudentById(id);
//		if (optionalStudent.isPresent()) {
//			Student student = optionalStudent.get();
//			if (studentDetails.getFirstName() != null) {
//				student.setFirstName(studentDetails.getFirstName());
//			}
//			if (studentDetails.getLastName() != null) {
//				student.setLastName(studentDetails.getLastName());
//			}
//			if (studentDetails.getEmail() != null) {
//				student.setEmail(studentDetails.getEmail());
//			}
//
//			return ResponseEntity.ok(studentService.save(student));
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		if (studentService.existsById(id)) {
			studentService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/bulk1")
	public ResponseEntity<Void> deleteStudentBulk(@RequestBody List<Student> student) {
		for (Student s : student) {

			if (studentService.existsById(s.getId())) {
				studentService.deleteById(s.getId());
			}

		}
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("/bulk2")
	public ResponseEntity<Void> deleteStudentBulk2(@RequestBody StudentIdDto studentDto) {
		List<Long> idList = studentDto.getId();
		for (Long id : idList) {
			if (studentService.existsById(id)) {
				studentService.deleteById(id);
			}
		}
//		if (idList != null && !idList.isEmpty()) {
//			studentRepository.deleteAllById(idList);
//		}

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/firstname/{firstName}")
	public Student getStudentByFirstName(@PathVariable String firstName) {

		Student student = studentService.findByFirstName(firstName);

		return student;
	}

	@GetMapping("/name")
	public List<Student> getStudentByName(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {

		List<Student> student = studentService.findByFirstNameOrLastName(firstName, lastName);

		return student;
	}

	// http://localhost:8080/api/students/name1?firstName=Liya&lastName=kalieb
	@GetMapping("/name1")
	public Student findFirstByFirstNameOrLastName(@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {

		Student student = studentService.findFirstByFirstNameOrLastName(firstName, lastName);

		return student;
	}

	@GetMapping("/domain/{domain}")
	public List<Student> findByDomain(@PathVariable(value = "domain") String domain) {

		List<Student> students = studentService.findByDomain(domain);

		return students;
	}

	@GetMapping("/domain")
	public List<Student> findByDomainAndFnameNative(@RequestParam(value = "firstName") String fName,
			@RequestParam(value = "domain") String domain) {

		List<Student> students = studentService.findByDomainAndFnameNative(fName, domain);

		return students;
	}

}

class StudentIdDto {
	List<Long> id;

	public List<Long> getId() {
		return id;
	}

	public void setId(List<Long> id) {
		this.id = id;
	}
}

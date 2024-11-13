package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public Optional<Student> getStudentById(Long id) {
		Optional<Student> student = studentRepository.findById(id);

		return student;
	}

	// @Async
	public void save(Student student) {
		Student studentList = studentRepository.save(student);
		// return studentList;
		// test
	}

	public boolean existsById(Long id) {
		return studentRepository.existsById(id);
	}

	public void deleteById(Long id) {
		studentRepository.deleteById(id);

	}

	public Student findByFirstName(String firstName) {

		return studentRepository.findByFirstName(firstName);
	}

	public List<Student> findByFirstNameOrLastName(String firstName, String lastName) {

		return studentRepository.findByFirstNameOrLastName(firstName, lastName);
	}

	public Student findFirstByFirstNameOrLastName(String firstName, String lastName) {

		return studentRepository.findFirstByFirstNameOrLastName(firstName, lastName);
	}

	public List<Student> findByDomain(String domain) {

		return studentRepository.findByDomain(domain);
	}

	public List<Student> findByDomainAndFname(String fName, String domain) {

		return studentRepository.findByDomainAndFname(fName, domain);
	}

	public List<Student> findByDomainAndFnameNative(String fName, String domain) {

		return studentRepository.findByDomainAndFnameNative(fName, domain);
	}

}

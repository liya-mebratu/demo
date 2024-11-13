package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	// Custom queries can be added here if needed

	Student findByFirstName(String firstName);

	List<Student> findByFirstNameOrLastName(String firstName, String lastName);

	Student findFirstByFirstNameOrLastName(String firstName, String lastName);

	@Query(value = "select s from Student s where s.email like %?1%")
	List<Student> findByDomain(String domainName);

	// or
	List<Student> findByEmailContaining(String domainName);
	// using indexed parameters
//	@Query(value = "select s from Student s where s.firstName = ?1 and s.email like %?2%")
//	List<Student> findByDomainAndFname(String firstName, String domainName);

	// using named parameters
	@Query(value = "select s from Student s where s.firstName = :fname and s.email like %:domain%")
	List<Student> findByDomainAndFname(@Param("fname") String firstName, @Param("domain") String domainName);

	@Query(value = "select * from Students s where s.first_Name = :fname and s.email like %:domain%", nativeQuery = true)
	List<Student> findByDomainAndFnameNative(@Param("fname") String firstName, @Param("domain") String domainName);

}

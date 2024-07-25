package com.practice.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.practice.sms.entity.Student;
import com.practice.sms.repository.StudentRepository;

@SpringBootApplication
public class StudentManagementSystemApplication implements CommandLineRunner {
	
	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		Student s1 = new Student("Ashok", "Grandhi", "ashok@gmail.com");
//		studentRepository.save(s1);
//		Student s2 = new Student("Rohith", "Grandhi", "rohith@gmail.com");
//		studentRepository.save(s2);
		
		
		
		
		
		
	}

}

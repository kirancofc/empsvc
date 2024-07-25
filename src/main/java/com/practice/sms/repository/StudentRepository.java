package com.practice.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.sms.entity.Student;


public interface StudentRepository extends JpaRepository<Student, Long>{

}

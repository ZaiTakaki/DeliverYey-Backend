package com.IT342.DeliverYey.Service;

import com.IT342.DeliverYey.Entity.StudentEntity;
import com.IT342.DeliverYey.Repository.StudentRepository;
import com.IT342.DeliverYey.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public StudentService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public StudentEntity insertStudent(StudentEntity student) {
        // Password validation
        if (!isValidPassword(student.getPassword())) {
            throw new IllegalArgumentException("Invalid password format. It must be at least 8 characters with 1 uppercase letter.");
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    public List<StudentEntity> getAllStudent() {
        return studentRepository.findAll();
    }

    public StudentEntity updateStudent(int sid, StudentEntity newStudentDetails){
        StudentEntity student = new StudentEntity();
        try {
            //1. search the ID  number of the student that will be updated
            student = studentRepository.findById(sid).get();

            //2. update the record
            student.setFirstname(newStudentDetails.getFirstname());
            student.setFirstname(newStudentDetails.getLastname());
            student.setGender(newStudentDetails.getGender());
            student.setIdNumber(newStudentDetails.getIdNumber());
        }catch (NoSuchElementException ex){
            throw new NoSuchElementException("Student" + sid + "does not exist!");
        }finally {
            return studentRepository.save(student);
        }
    }

    public String deleteStudent(int sid){
        String msg = "";

        if (studentRepository.findById(sid) != null){
            studentRepository.deleteById(sid);
            msg = "Student " + sid +"is successfully deleted!";
        } else
            msg = "Student " + sid + " does not exist.";
        return msg;
    }

    public boolean isStudentExists(int sid) {
        // Use findById directly to check if a user with the given ID exists
        return studentRepository.findById(sid).isPresent();
    }

    private boolean isValidPassword(String password) {
        // Password should be at least 8 characters with 1 uppercase letter
        return password.matches("^(?=.*[A-Z]).{8,}$");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

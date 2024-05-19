package com.IT342.DeliverYey.Service;

import com.IT342.DeliverYey.Entity.StaffEntity;
import com.IT342.DeliverYey.Entity.StudentEntity;
import com.IT342.DeliverYey.Repository.StaffRepository;
import com.IT342.DeliverYey.Repository.StudentRepository;
import com.IT342.DeliverYey.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(StaffService.class.getName());

    public StaffEntity insertStaff(StaffEntity staff) {
        try {
            if (staff == null || staff.getStudent() == null) {
                throw new IllegalArgumentException("Staff or student information is null.");
            }

            // Check if the student exists in the database
            Optional<StudentEntity> optionalStudent = studentRepository.findById(staff.getStudent().getSid());
            if (optionalStudent.isPresent()) {
                StudentEntity student = optionalStudent.get();
                staff.setStudent(student);
                student.setUserType(UserType.STAFF);
                studentRepository.save(student);
                return staffRepository.save(staff);
            } else {
                // Log a warning if the student is not found
                logger.log(Level.WARNING, "Student with ID {0} not found", staff.getStudent().getSid());
                // Return an error message to indicate that the student doesn't exist
                throw new NoSuchElementException("Student with ID " + staff.getStudent().getSid() + " not found.");
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.log(Level.SEVERE, "Failed to insert Staff.", e);
            // Rethrow the exception or throw a new runtime exception
            throw new RuntimeException("Failed to insert Staff.", e);
        }
    }


    public List<StaffEntity> getAllStaff() {
        return staffRepository.findAll();
    }

    public StaffEntity updateStaff(int staffId, StaffEntity newStaffDetails){
        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new NoSuchElementException("Staff with ID " + staffId + " does not exist!"));

        if (!isValidContactInfo(newStaffDetails.getContactInfo())) {
            // Handle validation error
            throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
        }

        // Ensure that the staffId remains the same
        newStaffDetails.setStaffId(staffId);

        // Update other fields
        staff.setContactInfo(newStaffDetails.getContactInfo());
        staff.setStatus(newStaffDetails.getStatus());
        staff.setIdNumber(newStaffDetails.getIdNumber());
        staff.setPassword(newStaffDetails.getPassword());

        return staffRepository.save(staff);
    }


    public String deleteStaff(int staffId) {
        Optional<StaffEntity> optionalStaff = staffRepository.findById(staffId);

        if (optionalStaff.isPresent()) {
            StaffEntity staff = optionalStaff.get();
            StudentEntity student = staff.getStudent();

            if (student != null) {
                student.setStaff(null); // Set the reference to null
                student.setUserType(UserType.STUDENT);
                studentRepository.save(student);
            }

            staffRepository.deleteById(staffId);
            return "Staff " + staffId + " is successfully deleted!";
        } else {
            return "Staff " + staffId + " does not exist.";
        }
    }

    private boolean isValidContactInfo(String contactInfo) {
        // Check if contactInfo is 11 digits starting with 09
        return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
    }
}

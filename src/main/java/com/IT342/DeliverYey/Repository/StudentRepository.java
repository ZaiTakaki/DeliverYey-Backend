package com.IT342.DeliverYey.Repository;

import com.IT342.DeliverYey.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository <StudentEntity, Integer> {
    StudentEntity saveAndFlush(StudentEntity entity);
}

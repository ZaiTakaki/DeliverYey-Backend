package com.IT342.DeliverYey.Controller;

import com.IT342.DeliverYey.Entity.StudentEntity;
import com.IT342.DeliverYey.Service.OrderItemService;
import com.IT342.DeliverYey.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/insertStudent")
    public StudentEntity insertStudent(@RequestBody StudentEntity student) {
        return studentService.insertStudent(student);
    }

    @GetMapping("/getAllStudent")
    public List<StudentEntity> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PutMapping("/updateStudent")
    public StudentEntity updateStudent(@RequestParam int sid, @RequestBody StudentEntity newStudentDetails) {
        return studentService.updateStudent(sid, newStudentDetails);
    }

    @DeleteMapping("/deleteStudent/{sid}")
    public String deleteStudent(@PathVariable int sid) {
        return studentService.deleteStudent(sid);
    }
}

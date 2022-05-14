package Security.LogIn.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"Malik"),
            new Student(2,"Ojo"),
            new Student(3,"Jibola")
    );
@GetMapping
    public List<Student> getAllStudent(){
        return STUDENTS;
    }
@PostMapping
    public void registerNewStudent(@RequestBody Student student){
    System.out.println("registerNewStudent");
        System.out.println(student);
    }
  @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
      System.out.println("deleteStudent");
        System.out.println(studentId);
    }
@PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId")Integer studentId,Student student){
    System.out.println("updateStudent");
        System.out.printf("%s %s",student, student);
    }
}

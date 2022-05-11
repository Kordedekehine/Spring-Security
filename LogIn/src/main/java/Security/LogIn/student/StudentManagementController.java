package Security.LogIn.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RequestMapping("management/api/v1/students")
@RestController
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"Kolade Ruth"),
            new Student(2,"Ojo Olaosebikan"),
            new Student(3,"Jibola Pasuma")
    );

    @GetMapping
    public List<Student> getAllStudents(){
        System.out.println("Get All Student");
        return STUDENTS;
    }

    @PostMapping
    public void registerNewStudent(Student student){
        System.out.println("registerNewStudent");
        System.out.println(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println("deleteStudent");
        System.out.println(studentId);
    }

   @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId")Integer studentId,@RequestBody Student student){
       System.out.println("updateStudent");
        System.out.println(String.format("%s %s",  student,student));
    }
}

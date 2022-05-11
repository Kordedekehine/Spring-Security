package Security.LogIn.student;

import Security.LogIn.student.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"Kolade Ruth"),
            new Student(2,"Ojo Olaosebikan"),
            new Student(3,"Jibola Pasuma")
    );

    @GetMapping(path = "{studentId}")
    public Student getStudentById(@PathVariable("studentId") Integer studentId){
        return STUDENTS.stream()

                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("student " + studentId +
                        "does not exists"));
    }
}

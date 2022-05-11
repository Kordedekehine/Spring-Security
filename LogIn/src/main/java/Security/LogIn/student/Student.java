package Security.LogIn.student;

import java.util.Objects;

public class Student {

    private final Integer StudentId;
    private final String StudentName;


    public Student(Integer studentId, String studentName) {
        StudentId = studentId;
        StudentName = studentName;
    }

    public Integer getStudentId() {
        return StudentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "StudentId=" + StudentId +
                ", StudentName='" + StudentName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(StudentId, student.StudentId) && Objects.equals(StudentName, student.StudentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(StudentId, StudentName);
    }
}

package Security.LogIn.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/testapi")
public class TestApi {

    @GetMapping("/everybody")
    public String general(){
        return "staffs and students lorisilorisi";
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public String userAccess() {
        return "Student Content.";
    }
    @GetMapping("/employee")
    @PreAuthorize("hasRole('Teacher')")
    public String moderatorAccess() {
        return "Teachers Content.";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Content.";
    }
}

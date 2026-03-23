package hei.school.student;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {


    private final List<Student> students = new ArrayList<>();


    @GetMapping("/welcome")
    public String welcome(@RequestParam String name) {
        return "Welcome " + name;
    }


    @PostMapping("/students")
    public String addStudents(@RequestBody List<Student> newStudents) {
        students.addAll(newStudents);
        return getStudentsNames();
    }


    @GetMapping("/students")
    public String getStudents(
            @RequestHeader(value = "Accept", defaultValue = "text/plain") String accept) {

        if ("text/plain".equals(accept)) {
            return getStudentsNames();
        }
        return "Format non supporté.";
    }


    private String getStudentsNames() {
        if (students.isEmpty()) {
            return "Aucun étudiant enregistré.\n";
        }

        StringBuilder sb = new StringBuilder();
        for (Student s : students) {
            sb.append(s.getFirstName())
                    .append(" ")
                    .append(s.getLastName())
                    .append("\n");
        }
        return sb.toString();
    }
}

package hei.school.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>();


    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(@RequestParam(required = false) String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Paramètre 'name' manquant ou vide.");
            }
            return ResponseEntity.ok("Welcome " + name);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur serveur.");
        }
    }

    @PostMapping("/students")
    public ResponseEntity<List<Student>> addStudents(@RequestBody List<Student> newStudents) {
        try {
            students.addAll(newStudents);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ArrayList<>(students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(
            @RequestHeader(value = "Accept", required = false) String accept) {

        try {
            if (accept == null || accept.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Entête Accept manquante.");
            }

            if ("application/json".equals(accept)) {
                return ResponseEntity.ok(students);
            } else if ("text/plain".equals(accept)) {
                return ResponseEntity.ok(getStudentsNames());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                        .body("Format non supporté.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur.");
        }
    }

    private String getStudentsNames() {
        if (students.isEmpty()) {
            return "Aucun étudiant enregistré.\n";
        }
        StringBuilder sb = new StringBuilder();
        for (Student s : students) {
            sb.append(s.getFirstName()).append(" ").append(s.getLastName()).append("\n");
        }
        return sb.toString();
    }
}
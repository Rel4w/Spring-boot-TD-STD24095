package hei.school.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentValidator validator;
    private final StudentService service;

    public StudentController(StudentValidator validator, StudentService service) {
        this.validator = validator;
        this.service = service;
    }

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
    public ResponseEntity<?> createStudents(@RequestBody List<Student> newStudents) {  // ← changé en <?>
        try {
            validator.validate(newStudents);                    // Validator
            List<Student> saved = service.addStudents(newStudents);  // Service
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest()
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
                return ResponseEntity.ok(service.getAllStudents());
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
        List<Student> all = service.getAllStudents();
        if (all.isEmpty()) {
            return "Aucun étudiant enregistré.\n";
        }
        StringBuilder sb = new StringBuilder();
        for (Student s : all) {
            sb.append(s.getFirstName()).append(" ").append(s.getLastName()).append("\n");
        }
        return sb.toString();
    }
}
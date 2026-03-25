package hei.school.student;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentValidator {

    public void validate(List<Student> students) {
        for (Student student : students) {
            if (student.getReference() == null || student.getReference().isBlank()) {
                throw new BadRequestException("NewStudent.reference cannot be null or empty");
            }
            if (student.getFirstName() == null || student.getFirstName().isBlank()) {
                throw new BadRequestException("NewStudent.firstName cannot be null or empty");
            }
            if (student.getLastName() == null || student.getLastName().isBlank()) {
                throw new BadRequestException("NewStudent.lastName cannot be null or empty");
            }
        }
    }
}
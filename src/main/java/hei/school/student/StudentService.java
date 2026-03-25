package hei.school.student;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> studentsInMemory = new ArrayList<>();

    public List<Student> addStudents(List<Student> newStudents) {
        studentsInMemory.addAll(newStudents);
        return new ArrayList<>(studentsInMemory);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentsInMemory);
    }
}
package hei.school.student;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {

    @JsonProperty("Reference")
    private String reference;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("Age")
    private int age;

    public Student() {}

    public Student(String reference, String firstName, String lastName, int age) {
        this.reference = reference;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}

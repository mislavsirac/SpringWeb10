package spring.boot.mislav.sirac.web;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.boot.mislav.sirac.student.StudentCommand;
import spring.boot.mislav.sirac.student.StudentDTO;
import spring.boot.mislav.sirac.student.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Test
    void getAllStudents() {
        // Arrange
        List<StudentDTO> expectedStudents = new ArrayList<>();
        when(studentService.findAll()).thenReturn(expectedStudents);

        // Act
        List<StudentDTO> actualStudents = studentController.getAllStudents();

        // Assert
        assertEquals(expectedStudents, actualStudents);
        verify(studentService).findAll();
    }

    @Test
    void getStudentByJMBAG() {
        // Arrange
        String jmbag = "123456789";
        Optional<StudentDTO> expectedStudent = Optional.of(new StudentDTO());
        when(studentService.findByJMBAG(jmbag)).thenReturn(expectedStudent);
        ResponseEntity<StudentDTO> expectedResponse = ResponseEntity.ok(expectedStudent.get());

        // Act
        ResponseEntity<StudentDTO> actualResponse = studentController.getStudentByJMBAG(jmbag);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService).findByJMBAG(jmbag);
    }

    @Test
    void save() {
        // Arrange
        StudentCommand studentCommand = new StudentCommand();
        Optional<StudentDTO> expectedStudent = Optional.of(new StudentDTO());
        when(studentService.save(studentCommand)).thenReturn(expectedStudent);
        ResponseEntity<StudentDTO> expectedResponse =
                ResponseEntity.status(HttpStatus.CREATED).body(expectedStudent.get());

        // Act
        ResponseEntity<StudentDTO> actualResponse = studentController.save(studentCommand);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService).save(studentCommand);
    }

    @Test
    void update() {
        // Arrange
        String jmbag = "123456789";
        StudentCommand updateStudentCommand = new StudentCommand();
        Optional<StudentDTO> expectedStudent = Optional.of(new StudentDTO());
        when(studentService.update(jmbag, updateStudentCommand)).thenReturn(expectedStudent);
        ResponseEntity<StudentDTO> expectedResponse = ResponseEntity.ok(expectedStudent.get());

        // Act
        ResponseEntity<StudentDTO> actualResponse = studentController.update(jmbag, updateStudentCommand);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(studentService).update(jmbag, updateStudentCommand);
    }

    @Test
    void delete() {
        // Arrange
        String jmbag = "123456789";

        // Act
        assertDoesNotThrow(() -> studentController.delete(jmbag));

        // Assert
        verify(studentService).deleteByJMBAG(jmbag);
    }
}

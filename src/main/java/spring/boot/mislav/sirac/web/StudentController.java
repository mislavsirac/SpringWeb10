package spring.boot.mislav.sirac.web;

import spring.boot.mislav.sirac.student.StudentCommand;
import spring.boot.mislav.sirac.student.StudentDTO;
import spring.boot.mislav.sirac.student.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
    
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getAllStudents(){
        return studentService.findAll();
    }
    
    @GetMapping("/{JMBAG}")
    public ResponseEntity<StudentDTO> getStudentByJMBAG(@PathVariable final String JMBAG){
        return studentService.findByJMBAG(JMBAG)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }
    
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<StudentDTO> save(@Valid @RequestBody final StudentCommand command){
        return studentService.save(command)
                .map(
                        studentDTO -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(studentDTO)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{JMBAG}")
    public ResponseEntity<StudentDTO> update(@PathVariable String JMBAG, @Valid @RequestBody final StudentCommand updateStudentCommand){
        return studentService.update(JMBAG, updateStudentCommand)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{JMBAG}")
    public void delete(@PathVariable String JMBAG){
        studentService.deleteByJMBAG(JMBAG);
    }
    
}

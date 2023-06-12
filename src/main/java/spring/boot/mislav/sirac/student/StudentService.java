package spring.boot.mislav.sirac.student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    
    List<StudentDTO> findAll();
    
    Optional<StudentDTO> findByJMBAG(String JMBAG);
    
    Optional<StudentDTO> save(StudentCommand studentCommand);
    
    Optional<StudentDTO> update(String JMBAG, StudentCommand updatedStudentCommand);
    
    void deleteByJMBAG(String JMBAG);
}

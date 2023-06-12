package spring.boot.mislav.sirac.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import spring.boot.mislav.sirac.student.StudentService;

public class StudentListJob implements Job {

    @Autowired
    private StudentService studentService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Ovo su trenutno upisani studenti:");
        System.out.println("----------------------------------------------");
        studentService.findAll().forEach(student -> {
            String studentInfo = String.format("%s - %s %s",
                    student.getJmbag(), student.getFirstName(), student.getLastName());
            System.out.println(studentInfo);
        });
        System.out.println("----------------------------------------------");
    }
}

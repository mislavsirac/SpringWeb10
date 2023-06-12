package spring.boot.mislav.sirac.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.boot.mislav.sirac.job.StudentListJob;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail studentListJobDetail() {
        return JobBuilder.newJob(StudentListJob.class)
                .withIdentity("studentListJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger studentListJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(10)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(studentListJobDetail())
                .withIdentity("studentListJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
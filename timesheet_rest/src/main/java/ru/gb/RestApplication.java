package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import ru.gb.model.*;
import ru.gb.repository.ProjectRepository;
import ru.gb.repository.TimesheetRepository;
import ru.gb.repository.UserRepository;
import ru.gb.repository.UserRoleRepository;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@EnableDiscoveryClient
@SpringBootApplication
public class RestApplication {

    /**
     * spring-data - ...
     * spring-data-jdbc - зависимость, которая предоставляет удобные преднастроенные инструменты
     * для работы c реляционными БД
     * spring-data-jpa - зависимость, которая предоставляет удобные преднастроенные инструменты
     * для работы с JPA
     *      spring-data-jpa
     *       /
     *     /
     *   jpa   <------------- hibernate (ecliselink ...)
     *
     * spring-data-mongo - зависимость, которая предоставляет инструменты для работы с mongo
     * spring-data-aws
     *
     *
     */

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(RestApplication.class, args);

        UserRepository userRepository = ctx.getBean(UserRepository.class);
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin

        User user = new User();
        user.setLogin("user");
        user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq"); // user

        User anonymous = new User();
        anonymous.setLogin("anon");
        anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon

        admin = userRepository.save(admin);
        user = userRepository.save(user);
        anonymous = userRepository.save(anonymous);

        UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);

        UserRole adminAdminRole = new UserRole();
        adminAdminRole.setUserId(admin.getId());
        adminAdminRole.setRoleName(Role.ADMIN.getName());
        userRoleRepository.save(adminAdminRole);

        UserRole adminUserRole = new UserRole();
        adminUserRole.setUserId(admin.getId());
        adminUserRole.setRoleName(Role.USER.getName());
        userRoleRepository.save(adminUserRole);

        UserRole userUserRole = new UserRole();
        userUserRole.setUserId(user.getId());
        userUserRole.setRoleName(Role.USER.getName());
        userRoleRepository.save(userUserRole);

        ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);
        for (int i = 1; i <= 5; i++) {
            Project project = new Project();
            project.setName("Project #" + i);
            projectRepo.save(project);
        }

        TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);

        LocalDate createdAt = LocalDate.now();
        for (int i = 1; i <= 10; i++) {
            createdAt = createdAt.plusDays(1);

            Timesheet timesheet = new Timesheet();
            timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
            timesheet.setCreatedAt(createdAt);
            timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

            timesheetRepo.save(timesheet);
        }
    }
}

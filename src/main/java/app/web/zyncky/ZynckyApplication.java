package app.web.zyncky;

import app.web.zyncky.constant.RoleEnum;
import app.web.zyncky.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ZynckyApplication {

    final RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(ZynckyApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (roleService.getAllRoles().isEmpty()) {
                for (RoleEnum roleEnum : RoleEnum.values()) {
                    roleService.createRole(roleEnum.name());
                    log.info("Role: {} created successfully", roleEnum.name());
                }
            }
        };
    }

}

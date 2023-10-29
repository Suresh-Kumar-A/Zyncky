package app.web.zyncky;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import app.web.zyncky.constant.RoleEnum;
import app.web.zyncky.service.RoleService;

@SpringBootApplication
public class ZynckyApplication {

	@Autowired
	RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(ZynckyApplication.class, args);
	}

	@Bean
	CommandLineRunner execAppStartupScript() {
		return args -> {
			if (roleService.getAllRole().size() == 0) {
				for (RoleEnum roleEnum : RoleEnum.values()) {
					roleService.createRole(roleEnum.name());
					System.out.println("Role (" + roleEnum.name() + ") is created successfully");
				}
			}
		};
	}
}

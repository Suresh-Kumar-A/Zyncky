package app.web.zyncky;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import app.web.zyncky.constant.RoleEnum;
import app.web.zyncky.service.RoleService;
import app.web.zyncky.service.UserService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class ZynckyApplication {

	private final RoleService roleService;

	private final UserService userService;

	@Value("${app.default.admin.username}")
	private String defaultAdminUsername;

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

			if (!userService.doUserExists(defaultAdminUsername)) {
				userService.createDefaultAdminUser();
			}
		};
	}
}

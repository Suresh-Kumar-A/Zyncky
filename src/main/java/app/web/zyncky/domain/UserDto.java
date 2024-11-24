package app.web.zyncky.domain;

import app.web.zyncky.constant.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    String uid;
    String emailAddress;
    String password;
    @Builder.Default
    String displayName = "Guest User";
    @Builder.Default
    Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Builder.Default
    String roleName = RoleEnum.GUEST.name();
}

package app.web.zyncky.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Integer id;

    private String uid;

    private String userName;

    private String displayName;

    private String password;

    private Date createdAt;

    @Default
    private boolean locked = false;

    private String roleName;
}
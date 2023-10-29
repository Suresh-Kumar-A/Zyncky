package app.web.zyncky.constant;

import lombok.Getter;

@Getter
public enum RoleEnum {
    GUEST(1), USER(2), ADMIN(3);

    private int value = 1;

    private RoleEnum(int value) {
        this.value = value;
    }

    private RoleEnum(String text) {
        switch (text.toUpperCase()) {
            case "USER":
                value = 2;
                break;
            case "ADMIN":
                value = 3;
                break;
            default:
                value = 1;
                break;
        }
    }
}

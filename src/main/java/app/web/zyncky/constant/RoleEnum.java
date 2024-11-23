package app.web.zyncky.constant;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN(1), USER(2), GUEST(3);
    private int value = 3;

    RoleEnum(final int val) {
        if (val > 0 && val < 3) {
            this.value = val;
        }
    }

    public static RoleEnum mapToRole(final String roleName) {
        return switch (roleName.toUpperCase()) {
            case "ADMIN" -> ADMIN;
            case "USER" -> USER;
            default -> GUEST;
        };
    }

    public static boolean isValidRole(final String roleName) {
        if (roleName == null) {
            return false;
        } else {
            return switch (roleName) {
                case "ADMIN", "USER", "GUESt" -> true;
                default -> false;
            };
        }
    }
}

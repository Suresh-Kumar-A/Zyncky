package com.gmail.creativegeeksuresh.zyncky.service.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AppConstants {

        public static final String APP_NAME = "Zyncky";
        public static final String APP_PREFIX = "ZKY";
        public static final String APP_DESC = "Default app created to use the functionalities";
        public static final String UNDERSCORE = "_";

        public static final String ADMIN_ROLE_STRING = "ADMIN";
        public static final String USER_ROLE_STRING = "USER";
        public static final String ROLE_STRING = "ROLE";
        public static final Set<String> DEFAULT_ROLE_SET = Collections
                        .unmodifiableSet(new HashSet<>(Arrays.asList(ADMIN_ROLE_STRING, USER_ROLE_STRING)));

        public static final String PRIVATE_KEY_PATH = "security/keys/jwt_privatekey.der";
        public static final String PUBLIC_KEY_PATH = "security/keys/jwt_publickey.der";
}

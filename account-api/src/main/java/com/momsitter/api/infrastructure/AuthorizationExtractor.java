package com.momsitter.api.infrastructure;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class AuthorizationExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    private static final String BEARER_TYPE = "Bearer";

    private AuthorizationExtractor() {
    }

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (isBearerHeader(value)) {
                return authHeaderValue(request, value);
            }
        }

        return null;
    }

    private static boolean isBearerHeader(String value) {
        return value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }

    private static String authHeaderValue(HttpServletRequest request, String value) {
        String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
        request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
        int commaIndex = authHeaderValue.indexOf(',');
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
    }
}

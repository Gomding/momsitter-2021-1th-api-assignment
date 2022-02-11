package com.momsitter.authiorization.ui.webconfig;

import com.momsitter.authiorization.infrastructure.AuthorizationExtractor;
import com.momsitter.authiorization.infrastructure.JwtTokenProvider;
import com.momsitter.common.exception.UnauthorizedException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String HEADER_NAME_ORIGIN = "Origin";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Preflight 요청에는 토큰이 없기때문에 토큰 검증을 생략하도록 구현
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPreflightRequest(request)) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        if (Objects.isNull(token)) {
            throw new UnauthorizedException("로그인이 필요한 기능입니다. 로그인 후 다시 시도해주세요.");
        }
        return jwtTokenProvider.validateToken(token);
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return isHttpMethodOptions(request.getMethod()) && hasHeaderOrigin(request);
    }

    public boolean isHttpMethodOptions(String methodName) {
        return HttpMethod.OPTIONS.name().equalsIgnoreCase(methodName);
    }

    private boolean hasHeaderOrigin(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(HEADER_NAME_ORIGIN));
    }
}

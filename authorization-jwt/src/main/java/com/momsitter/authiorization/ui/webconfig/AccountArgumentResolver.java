package com.momsitter.authiorization.ui.webconfig;

import com.momsitter.authiorization.infrastructure.AuthorizationExtractor;
import com.momsitter.domain.Account;
import com.momsitter.authiorization.service.AuthService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AccountArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public AccountArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AccountAuthenticationPrinciple.class);
    }

    @Override
    public Account resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = AuthorizationExtractor.extract(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
        return authService.findAccountByToken(token);
    }
}

package com.momsitter.api.webconfig;

import com.momsitter.authiorization.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthWebConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final AuthInterceptor authInterceptor;

    public AuthWebConfig(AuthService authService, AuthInterceptor authInterceptor) {
        this.authService = authService;
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(accountArgumentResolver());
    }

    @Bean
    public AccountArgumentResolver accountArgumentResolver() {
        return new AccountArgumentResolver(authService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/accounts/me", "/api/accounts/me/**", "/admin/**");
    }
}

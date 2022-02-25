package com.momsitter.featuretoggle.config;

import com.momsitter.featuretoggle.config.property.UnleashProperties;
import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UnleashProperties.class)
public class FeatureToggleConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "momsitter.feature.toggle", name = "vendor", havingValue = "unleash")
    public Unleash unleashFeatureToggle(UnleashProperties properties) {
        UnleashConfig unleashConfig = UnleashConfig.builder()
                .appName(properties.getAppName())
                .instanceId(properties.getInstanceId())
                .environment(System.getenv(properties.getEnvironment()))
                .unleashAPI(properties.getApiUrl())
                .customHttpHeader("Authorization", properties.getApiToken())
                .build();

        return new DefaultUnleash(unleashConfig);
    }
}

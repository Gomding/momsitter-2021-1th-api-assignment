package com.momsitter.api.ui;

import com.momsitter.featuretoggle.FeatureToggle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final FeatureToggle featureToggle;

    public HelloController(FeatureToggle featureToggle) {
        this.featureToggle = featureToggle;
    }

    @GetMapping("/api/hello")
    public String helloWorld() {

        if (featureToggle.isEnabled("test-feature-toggl")) {
            return "Hello World!!!";
        }
        return "Bye World!!!!!!!!!!!!";
    }
}

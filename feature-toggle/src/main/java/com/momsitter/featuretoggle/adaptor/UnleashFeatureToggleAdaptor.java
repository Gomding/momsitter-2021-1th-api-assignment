package com.momsitter.featuretoggle.adaptor;

import io.getunleash.Unleash;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "momsitter.feature.toggle", name = "vendor", havingValue = "unleash")
public class UnleashFeatureToggleAdaptor implements IFeatureToggle {

    private final Unleash unleashFeatureToggle;

    public UnleashFeatureToggleAdaptor(Unleash unleashFeatureToggle) {
        this.unleashFeatureToggle = unleashFeatureToggle;
    }

    public boolean isSupported(FeatureToggleVendor vendor) {
        return FeatureToggleVendor.UNLEASH.equals(vendor);
    }

    @Override
    public boolean isEnabled(String toggleName) {
        return unleashFeatureToggle.isEnabled(toggleName);
    }
}

package com.momsitter.featuretoggle;

import com.momsitter.featuretoggle.adaptor.FeatureToggleVendor;
import com.momsitter.featuretoggle.adaptor.IFeatureToggle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "momsitter.feature.toggle", name = "vendor")
public class FeatureToggle {

    @Value("${momsitter.feature.toggle.vendor}")
    private String featureToggleVendorName;

    private final List<IFeatureToggle> adaptors;

    public FeatureToggle(List<IFeatureToggle> adaptors) {
        this.adaptors = adaptors;
    }

    public boolean isEnabled(String toggleName) {
        return findFeatureToggleAdaptorByVendorName().isEnabled(toggleName);
    }

    private IFeatureToggle findFeatureToggleAdaptorByVendorName() {
        return adaptors.stream()
                .filter(adaptor -> adaptor.isSupported(FeatureToggleVendor.valueOf(featureToggleVendorName.toUpperCase())))
                .findAny().orElseThrow(() -> new UnsupportedOperationException("지원하지 않는 Feature Toggle 입니다."));
    }
}

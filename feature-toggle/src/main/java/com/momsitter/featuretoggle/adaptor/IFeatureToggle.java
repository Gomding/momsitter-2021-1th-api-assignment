package com.momsitter.featuretoggle.adaptor;

public interface IFeatureToggle {
    boolean isSupported(FeatureToggleVendor vendor);
    boolean isEnabled(String toggleName);
}

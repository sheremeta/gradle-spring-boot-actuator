package com.poc.fare;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class FareJacksonModule extends SimpleModule {

    @Override
    public void setupModule(Module.SetupContext context) {
        context.setMixInAnnotations(Fare.class, FareMixin.class);
    }
}

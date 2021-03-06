package com.shazam.fork.injector;

import com.shazam.fork.Configuration;

import java.util.regex.Pattern;

import static com.shazam.fork.injector.ConfigurationInjector.configuration;

public class TestPackagePatternInjector {

    public static Pattern testPackagePattern() {
        Configuration configuration = configuration();
        Pattern testPackagePattern = configuration.getTestPackagePattern();
        return testPackagePattern == null ? testPackagePattern(configuration) : testPackagePattern;
    }

    private static Pattern testPackagePattern(Configuration configuration) {
        return Pattern.compile(configuration.getInstrumentationInfo().getApplicationPackage().replace(".", "\\.") + ".*");
    }
}

package org.saltations.hephaestus;

import com.google.common.base.StandardSystemProperty;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.env.Environment;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

/**
 * Provides an initialized instance of the preferences user root for the application
 */

@Slf4j
@Factory
public class HEPHPrefs
{
    private static final String KEY_FOR_USERS_CABINET = "heph-users-template-cabinet";

    private final Preferences preferences =  Preferences.userNodeForPackage(HEPHApp.class);

    private final String envPrefix;

    @Named("cabinet")
    @Bean
    @Getter
    @Setter
    Path pathToUserCabinet;

    @Inject
    public HEPHPrefs(Environment env)
    {
        this.envPrefix = env.getActiveNames().contains("test") ? "test" : "";

        var defaultUsersCabinet = Paths.get(StandardSystemProperty.USER_HOME.value(), ".heph", envPrefix, "templates").toAbsolutePath().toString();

        this.pathToUserCabinet =  Paths.get(preferences.get(KEY_FOR_USERS_CABINET,defaultUsersCabinet));
    }

    public boolean doesUsersCabinetExist()
    {
       return this.pathToUserCabinet.toFile().exists();
    }

    public void initializeUsersCabinet()
    {
        if (!this.pathToUserCabinet.toFile().exists())
        {
            var created = this.pathToUserCabinet.toFile().mkdirs();
            log.info("Users cabinet creation {}", created ? "SUCCEEDED" : "FAILED");
        }

        var configuredUserCabinet = this.pathToUserCabinet.toAbsolutePath().toString();

        preferences.put(KEY_FOR_USERS_CABINET, configuredUserCabinet);
        log.info("Users cabinet is now configured to [{}]", configuredUserCabinet);
    }


}

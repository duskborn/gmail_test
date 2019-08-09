package com.gmail.mozhgru.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class Config {

    private static final Properties CONFIG_PROPERTIES;
    private static Logger logger = Logger.getLogger(Config.class);

    private static final int SECOND = 1000;

    static {
        CONFIG_PROPERTIES = new Properties();
        try (InputStream configInputStream = new FileInputStream("src/main/resources/config.properties")) {
            CONFIG_PROPERTIES.load(configInputStream);
        } catch (IOException e) {
            logger.info("Unable to load configuration file", e);
            throw new IllegalStateException("Unable to load configuration file", e);
        }
    }

    public static int getGlobalTimeout() {
        return Integer.parseInt(CONFIG_PROPERTIES.getProperty("timeout.global"));
    }

    public static String getAppUrl() {
        return CONFIG_PROPERTIES.getProperty("app.url");
    }

    public static int getAnimationTimeout() {
        return Integer.parseInt(CONFIG_PROPERTIES.getProperty("timeout.animation")) * SECOND;
    }

    public static int getCommandTimeout() {
        return Integer.parseInt(CONFIG_PROPERTIES.getProperty("timeout.command"));
    }

    public static boolean getWaitStrategy() {
        return "fast".equals(CONFIG_PROPERTIES.getProperty("wait.strategy"));
    }

    public static int getPageLoadingTimeout() {
        return Integer.parseInt(CONFIG_PROPERTIES.getProperty("timeout.page.load")) * SECOND;
    }
}
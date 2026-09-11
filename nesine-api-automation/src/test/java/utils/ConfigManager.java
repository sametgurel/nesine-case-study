package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager that reads configuration properties from classpath
 * and supports external overrides via JVM system properties (-Denv=stage, -DbaseUrl=...).
 */
public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();
    private static final String DEFAULT_CONFIG_FILE = "config.properties";

    static {
        java.util.Locale.setDefault(java.util.Locale.ENGLISH);
        loadConfiguration();
    }

    private ConfigManager() {
        // Utility class
    }

    private static void loadConfiguration() {
        String env = System.getProperty("env");
        String fileName = DEFAULT_CONFIG_FILE;

        if (env != null && !env.trim().isEmpty() && !env.equalsIgnoreCase("prod")) {
            fileName = "config-" + env.toLowerCase().trim() + ".properties";
        }

        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                PROPERTIES.load(input);
            } else {
                // Fallback to default config if environment-specific file not found
                try (InputStream defaultInput = ConfigManager.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE)) {
                    if (defaultInput != null) {
                        PROPERTIES.load(defaultInput);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties from " + fileName, e);
        }
    }

    public static String getProperty(String key) {
        // System properties take highest precedence (e.g., -DbaseUrl=...)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.trim().isEmpty()) {
            return systemProperty.trim();
        }
        String value = PROPERTIES.getProperty(key);
        return value != null ? value.trim() : null;
    }

    public static String getProperty(String key, String defaultValue) {
        String val = getProperty(key);
        return val != null ? val : defaultValue;
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "https://pc.nesine.com/v1");
    }

    public static String getPopularCouponsEndpoint() {
        return getProperty("endpoint.popular.coupons", "/PopularCoupons");
    }

    public static int getTimeoutMs() {
        String timeout = getProperty("timeout.ms", "15000");
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            return 15000;
        }
    }

    public static String getEnvironment() {
        return getProperty("environment", "prod");
    }
}

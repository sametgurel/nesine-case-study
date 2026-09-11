package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
                log.info("Configuration properties loaded successfully.");
            } else {
                log.warn("config.properties not found in classpath. Using default fallbacks.");
            }
        } catch (IOException e) {
            log.error("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key, String defaultValue) {
        // System property has higher precedence than properties file
        String systemVal = System.getProperty(key);
        if (systemVal != null && !systemVal.trim().isEmpty()) {
            return systemVal.trim();
        }
        return properties.getProperty(key, defaultValue).trim();
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "https://www.nesine.com");
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public static int getExplicitTimeout() {
        return Integer.parseInt(getProperty("timeout.explicit", "20"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("timeout.pageLoad", "30"));
    }

    public static int getImplicitTimeout() {
        return Integer.parseInt(getProperty("timeout.implicit", "0"));
    }

    public static String getPopularCouponsPath() {
        return getProperty("path.popular.coupons", "/iddaa/populer-kuponlar");
    }

    public static String getIddaaPath() {
        return getProperty("path.iddaa", "/iddaa");
    }

    public static String getLiveScoresPath() {
        return getProperty("path.live.scores", "/iddaa/canli-skor/futbol");
    }

    public static String getMinuteBetPath() {
        return getProperty("path.minute.bet", "/iddaa/dakika-bahis");
    }
}

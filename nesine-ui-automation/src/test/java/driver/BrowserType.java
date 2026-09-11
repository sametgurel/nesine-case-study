package driver;

public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE;

    public static BrowserType fromString(String browserName) {
        if (browserName == null || browserName.trim().isEmpty()) {
            return CHROME;
        }
        try {
            return BrowserType.valueOf(browserName.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return CHROME;
        }
    }
}

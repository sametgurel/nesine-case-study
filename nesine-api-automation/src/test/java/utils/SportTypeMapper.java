package utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility for mapping and verifying consistency between sportId and sport type names.
 */
public final class SportTypeMapper {

    private static final Map<Integer, String> SPORT_ID_TO_TYPE;
    private static final Map<String, Integer> TYPE_TO_SPORT_ID;

    static {
        Map<Integer, String> idMap = new HashMap<>();
        idMap.put(1, "Football");
        idMap.put(2, "Basketball");
        idMap.put(3, "Volleyball");
        idMap.put(4, "Tennis");
        idMap.put(5, "Table Tennis");
        idMap.put(6, "Handball");
        idMap.put(7, "Ice Hockey");
        idMap.put(8, "Snooker");

        SPORT_ID_TO_TYPE = Collections.unmodifiableMap(idMap);

        Map<String, Integer> typeMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : idMap.entrySet()) {
            typeMap.put(entry.getValue().toLowerCase(), entry.getKey());
        }
        TYPE_TO_SPORT_ID = Collections.unmodifiableMap(typeMap);
    }

    private SportTypeMapper() {
        // Utility class
    }

    /**
     * Checks if sportId and type string are consistent with each other.
     *
     * @param sportId the numeric sport identifier (e.g. 1)
     * @param type    the sport type name (e.g. "Football")
     * @return true if consistent, false otherwise
     */
    public static boolean isConsistent(int sportId, String type) {
        if (type == null || type.trim().isEmpty() || sportId <= 0) {
            return false;
        }

        String expectedType = SPORT_ID_TO_TYPE.get(sportId);
        if (expectedType != null) {
            return expectedType.equalsIgnoreCase(type.trim());
        }

        // If it's an unmapped new sport, ensure neither is blank/invalid
        return true;
    }

    public static String getExpectedType(int sportId) {
        return SPORT_ID_TO_TYPE.get(sportId);
    }

    public static Integer getExpectedSportId(String type) {
        return type != null ? TYPE_TO_SPORT_ID.get(type.trim().toLowerCase()) : null;
    }
}

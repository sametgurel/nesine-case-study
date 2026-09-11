package utils;

import java.util.EnumMap;
import java.util.Map;

/**
 * Thread-safe scenario context holder for managing test state across Cucumber steps.
 * Backed by ThreadLocal to support fully isolated, parallel test execution.
 */
public final class ScenarioContext {

    private static final ThreadLocal<Map<ContextKey, Object>> CONTEXT =
            ThreadLocal.withInitial(() -> new EnumMap<>(ContextKey.class));

    private ScenarioContext() {
        // Utility class
    }

    public static void set(ContextKey key, Object value) {
        CONTEXT.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(ContextKey key) {
        return (T) CONTEXT.get().get(key);
    }

    public static <T> T get(ContextKey key, Class<T> clazz) {
        Object value = CONTEXT.get().get(key);
        if (value == null) {
            return null;
        }
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        throw new ClassCastException("Context value for key " + key + " is not of type " + clazz.getName() + " (was " + value.getClass().getName() + ")");
    }

    public static boolean contains(ContextKey key) {
        return CONTEXT.get().containsKey(key);
    }

    public static void remove(ContextKey key) {
        CONTEXT.get().remove(key);
    }

    public static void clear() {
        CONTEXT.get().clear();
        CONTEXT.remove();
    }
}

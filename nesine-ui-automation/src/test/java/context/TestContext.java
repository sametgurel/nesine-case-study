package context;

import java.util.HashMap;
import java.util.Map;

/**
 * Shared scenario state managed by Cucumber PicoContainer dependency injection.
 * A new instance of TestContext is instantiated per Cucumber scenario.
 */
public class TestContext {

    private CouponModel selectedCoupon;
    private final Map<String, Object> customData = new HashMap<>();

    public TestContext() {
        this.selectedCoupon = new CouponModel();
    }

    public CouponModel getSelectedCoupon() {
        return selectedCoupon;
    }

    public void setSelectedCoupon(CouponModel selectedCoupon) {
        this.selectedCoupon = selectedCoupon;
    }

    public void set(String key, Object value) {
        customData.put(key, value);
    }

    public Object get(String key) {
        return customData.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        Object val = customData.get(key);
        if (val == null) {
            return null;
        }
        return (T) val;
    }
}

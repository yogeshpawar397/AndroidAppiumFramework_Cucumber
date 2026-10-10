
package context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<String, Object> scenarioData = new HashMap<>();

    public void set(String key, Object value) {
        validateKey(key);
        scenarioData.put(key, value);
    }

    public Object get(String key) {
        validateKey(key);

        if (!scenarioData.containsKey(key)) {
            throw new IllegalStateException(
                    "No value found in ScenarioContext for key: " + key);
        }

        return scenarioData.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        validateKey(key);

        if (type == null) {
            throw new IllegalArgumentException(
                    "Expected value type must not be null.");
        }

        Object value = get(key);

        if (value != null && !type.isInstance(value)) {
            throw new IllegalStateException(
                    "Value stored for key '" + key
                            + "' is not of expected type: " + type.getName());
        }

        return type.cast(value);
    }

    public boolean contains(String key) {
        validateKey(key);
        return scenarioData.containsKey(key);
    }

    public void remove(String key) {
        validateKey(key);
        scenarioData.remove(key);
    }

    public void clear() {
        scenarioData.clear();
    }

    private void validateKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException(
                    "Scenario context key cannot be null or blank.");
        }
    }
}

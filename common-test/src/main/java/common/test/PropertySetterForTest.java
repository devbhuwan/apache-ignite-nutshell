package common.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * @author Bhuwan Upadhyay
 * @date 2017/06/01
 */
public enum PropertySetterForTest {

    INSTANCE;

    private final Map<String, String> modifiableEnv;

    PropertySetterForTest() {
        try {
            Map<String, String> env = System.getenv();
            Field field = env.getClass().getDeclaredField("m");
            field.setAccessible(true);
            modifiableEnv = (Map<String, String>) field.get(env);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void initExternalConfig() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("test.properties"));
            properties.entrySet().stream().forEach(e -> modifiableEnv.put(e.getKey().toString(), e.getValue().toString()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Map<String, String> getModifiableEnv() {
        return modifiableEnv;
    }
}


package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    private ConfigReader() {
        throw new UnsupportedOperationException("Utility class — do not instantiate");
    }

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (input == null)
                throw new ConfigException("'config.properties' not found in classpath");
            properties.load(input);
        } catch (IOException e) {
            throw new ConfigException("Failed to load 'config.properties'", e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null)
            throw new NoSuchElementException("Missing config key: " + key);
        return value;
    }
}

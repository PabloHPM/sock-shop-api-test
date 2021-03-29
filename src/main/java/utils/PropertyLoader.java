package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertyLoader {
    Properties properties;

    public PropertyLoader() {
        loadSystemProperties();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private void loadSystemProperties() {
        this.properties = new Properties();
        final String FILE_NAME = "propertyFiles/environment.properties";
        InputStream propertyInputStream = Optional.ofNullable(getClass().getClassLoader().getResourceAsStream(FILE_NAME))
                                                  .orElseThrow(() -> new IllegalArgumentException("Can not load property"));
        try {
            properties.load(propertyInputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Can't load property.", e);
        }
    }
}

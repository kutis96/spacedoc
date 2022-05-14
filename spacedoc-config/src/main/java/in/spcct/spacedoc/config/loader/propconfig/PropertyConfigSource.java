package in.spcct.spacedoc.config.loader.propconfig;

import in.spcct.spacedoc.config.loader.ConfigSource;

import java.util.Properties;

public class PropertyConfigSource implements ConfigSource<String> {

    private final Properties properties;

    public PropertyConfigSource(Properties properties) {
        this.properties = properties;
    }

    private String pathToKey(String... pathSegments) {
        return String.join(".", pathSegments);
    }

    @Override
    public boolean containsItem(String... pathSegments) {
        return properties.containsKey(pathToKey(pathSegments));
    }

    @Override
    public String getItem(String... pathSegments) {
        return properties.getProperty(pathToKey(pathSegments));
    }
}

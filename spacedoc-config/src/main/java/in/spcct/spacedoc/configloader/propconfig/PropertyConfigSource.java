package in.spcct.spacedoc.configloader.propconfig;

import in.spcct.spacedoc.configloader.ConfigSource;

import java.util.Properties;

public class PropertyConfigSource implements ConfigSource<String> {

    private final Properties properties;

    public PropertyConfigSource(Properties properties) {
        this.properties = properties;
    }

    @Override
    public boolean containsKey(String path) {
        return properties.containsKey(path);
    }

    @Override
    public String getValue(String path) {
        return properties.getProperty(path);
    }
}

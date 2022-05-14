package in.spcct.spacedoc.config;

import in.spcct.spacedoc.config.loader.ConfigSource;
import lombok.NonNull;

import java.util.Map;

public class ConfigSourceWithOverrides implements ConfigSource {

    private final ConfigSource backingSource;
    private final Map<String, Object> overrides;

    public ConfigSourceWithOverrides(ConfigSource backingSource, Map<String, Object> overrides) {
        this.backingSource = backingSource;
        this.overrides = overrides;
    }

    @Override
    public boolean containsItem(@NonNull String path) {
        return overrides.containsKey(path) || backingSource.containsItem(path);
    }

    @Override
    public Object getItem(@NonNull String path) {
        return overrides.containsKey(path)
                ? overrides.get(path)
                : backingSource.getItem(path);
    }
}

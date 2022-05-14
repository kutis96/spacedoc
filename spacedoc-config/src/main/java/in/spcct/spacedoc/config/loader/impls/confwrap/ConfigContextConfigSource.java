package in.spcct.spacedoc.config.loader.impls.confwrap;

import in.spcct.spacedoc.config.ConfigContext;
import in.spcct.spacedoc.config.loader.ConfigSource;
import lombok.NonNull;

public class ConfigContextConfigSource implements ConfigSource {

    private final ConfigContext backing;

    public ConfigContextConfigSource(ConfigContext backing) {
        this.backing = backing;
    }

    @Override
    public boolean containsItem(@NonNull String path) {
        return backing.contains(String.join(".", path));
    }

    @Override
    public String getItem(@NonNull String path) {
        return backing.get(path);
    }
}

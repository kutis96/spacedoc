package in.spcct.spacedoc.config.internal;

import in.spcct.spacedoc.config.ConfigNamespace;
import in.spcct.spacedoc.config.ConfigProperty;
import in.spcct.spacedoc.config.loader.Config;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@ConfigNamespace(
        prefix = "markdown.render-cache"
)
public class RenderCacheConfig implements Config {

    @ConfigProperty(name = "cache-directory", defaultValue = ".spacedoc/cache/")
    private String cacheDirectory;

}

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
        prefix = "polyglot"
)
public class PolyglotConfig implements Config {

    /**
     * Directory to be used for require():d NPM modules
     */
    @ConfigProperty(required = false, value = "js.require.directory")
    private String requireCwd = "js/require";

}
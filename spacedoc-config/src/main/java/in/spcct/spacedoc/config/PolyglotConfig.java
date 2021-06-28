package in.spcct.spacedoc.config;

import in.spcct.spacedoc.configloader.ConfigFile;
import in.spcct.spacedoc.configloader.Property;
import in.spcct.spacedoc.configloader.propconfig.AbstractPropertiesFileConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ConfigFile(
        value = "/config/spacedoc.properties",
        prefix = "polyglot"
)
public class PolyglotConfig extends AbstractPropertiesFileConfig {

    /**
     * Directory to be used for require():d NPM modules
     */
    @Property(required = false, value = "js.require.directory")
    private String requireCwd = "js/require";


    private static PolyglotConfig INSTANCE = new PolyglotConfig();

    public static PolyglotConfig getInstance() {
        return INSTANCE;
    }

}
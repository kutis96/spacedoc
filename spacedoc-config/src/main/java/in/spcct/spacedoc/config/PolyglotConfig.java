package in.spcct.spacedoc.config;

import in.spcct.spacedoc.propconfig.AbstractPropertyConfig;
import in.spcct.spacedoc.propconfig.Property;
import in.spcct.spacedoc.propconfig.PropertyFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@PropertyFile(
        value = "/config/spacedoc.properties",
        prefix = "polyglot"
)
public class PolyglotConfig extends AbstractPropertyConfig {

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
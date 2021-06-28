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
        prefix = "general"
)
public class GeneralConfig extends AbstractPropertiesFileConfig {


    @Property(required = false)
    private String tempFilePrefix = "spacedoc-temp";

    private static GeneralConfig INSTANCE = new GeneralConfig();
    public static GeneralConfig getInstance() {
        return INSTANCE;
    }
}

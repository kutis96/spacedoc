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
        prefix = "ffc"
)
public class FFCConfig extends AbstractPropertiesFileConfig {

    @Property
    private String npxExecutable;

    @Property(required = false)
    private Boolean forceJS = false;


    private static FFCConfig INSTANCE = new FFCConfig();

    public static FFCConfig getInstance() {
        return INSTANCE;
    }

}

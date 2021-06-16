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
        prefix = "ffc"
)
public class FFCConfig extends AbstractPropertyConfig {

    @Property
    private String npxExecutable;

    @Property(required = false)
    private Boolean forceJS = false;


    private static FFCConfig INSTANCE = new FFCConfig();

    public static FFCConfig getInstance() {
        return INSTANCE;
    }

}

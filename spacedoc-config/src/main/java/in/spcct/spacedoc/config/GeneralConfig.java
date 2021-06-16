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
        prefix = "general"
)
public class GeneralConfig extends AbstractPropertyConfig {


    @Property(required = false)
    private String tempFilePrefix = "spacedoc-temp";

    private static GeneralConfig INSTANCE = new GeneralConfig();
    public static GeneralConfig getInstance() {
        return INSTANCE;
    }
}

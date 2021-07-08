package in.spcct.spacedoc.config;


import in.spcct.spacedoc.config.commons.EnableDisableAuto;
import in.spcct.spacedoc.configloader.ConfigFile;
import in.spcct.spacedoc.configloader.Converter;
import in.spcct.spacedoc.configloader.Property;
import in.spcct.spacedoc.configloader.fieldmap.converter.EnumConverter;
import in.spcct.spacedoc.configloader.propconfig.AbstractPropertiesFileConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Configures "Foreign Function Call" properties, typically for calling external JavaScript libraries.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigFile(
        value = "/config/spacedoc.properties",
        prefix = "ffc"
)
public class FFCConfig extends AbstractPropertiesFileConfig {

    private FFCConfig() {
        loadConfig();
    }

    /**
     * Path to Node.JS' "npx" executable
     * <p>
     * Typically useful for running code from external JavaScript libraries.
     */
    @Property
    private String npxExecutable;

    /**
     *
     */
    @Property(required = false)
    @Converter(EnumConverter.class)
    private EnableDisableAuto polyglotJs = EnableDisableAuto.AUTO;


    private static FFCConfig INSTANCE = new FFCConfig();

    public static FFCConfig getInstance() {
        return INSTANCE;
    }

}

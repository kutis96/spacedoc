package in.spcct.spacedoc.config;


import in.spcct.spacedoc.configloader.ConfigFile;
import in.spcct.spacedoc.configloader.Property;
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

    /**
     * Path to Node.JS' "npx" executable
     * <p>
     * Typically useful for running code from external JavaScript libraries.
     */
    @Property
    private String npxExecutable;

    /**
     * Set to true if JavaScript implementations of foreign functions are preferred over any others.
     * <p>
     * Typically useful when installing Node.JS is easier than installing other dependencies.
     */
    @Property(required = false)
    private Boolean forceJS = false;


    private static FFCConfig INSTANCE = new FFCConfig();

    public static FFCConfig getInstance() {
        return INSTANCE;
    }

}

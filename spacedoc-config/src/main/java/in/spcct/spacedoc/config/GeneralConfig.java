package in.spcct.spacedoc.config;

import in.spcct.spacedoc.configloader.ConfigFile;
import in.spcct.spacedoc.configloader.Property;
import in.spcct.spacedoc.configloader.propconfig.AbstractPropertiesFileConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * General configuration of SpaceDoc.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigFile(
        value = "/config/spacedoc.properties",
        prefix = "general"
)
public class GeneralConfig extends AbstractPropertiesFileConfig {

    /**
     * Prefix of SpaceDoc temporary files.
     * <p>
     * All SpaceDoc temporary files will then have a filename of the following pattern:
     * <code>[tempFilePrefix]-[some random name].[some suffix]</code>
     * <p>
     * The remaining portion of the name is typically random, the suffix depends on the content/usage of the temporary file.
     */
    @Property(required = false)
    private String tempFilePrefix = "spacedoc-temp";

    /**
     * Temporary file location.
     * <p>
     * When null is provided, the default path is used; see {@link java.io.File#createTempFile(String, String, java.io.File)} documentation.
     */
    @Property(required = false)
    private String tempDirectoryPath = null;

    private static GeneralConfig INSTANCE = new GeneralConfig();

    public static GeneralConfig getInstance() {
        return INSTANCE;
    }
}

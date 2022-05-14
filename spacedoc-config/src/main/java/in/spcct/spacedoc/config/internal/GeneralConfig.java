package in.spcct.spacedoc.config.internal;

import in.spcct.spacedoc.config.ConfigNamespace;
import in.spcct.spacedoc.config.ConfigProperty;
import in.spcct.spacedoc.config.loader.newconf.Config;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * General configuration of SpaceDoc.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@ConfigNamespace(
        prefix = "general"
)
public class GeneralConfig implements Config {

    /**
     * Prefix of SpaceDoc temporary files.
     * <p>
     * All SpaceDoc temporary files will then have a filename of the following pattern:
     * <code>[tempFilePrefix]-[some random name].[some suffix]</code>
     * <p>
     * The remaining portion of the name is typically random, the suffix depends on the content/usage of the temporary file.
     */
    @ConfigProperty(required = false)
    private String tempFilePrefix = "spacedoc-temp";

    /**
     * Temporary file location.
     * <p>
     * When null is provided, the default path is used; see {@link java.io.File#createTempFile(String, String, java.io.File)} documentation.
     */
    @ConfigProperty(required = false)
    private String tempDirectoryPath = null;
}

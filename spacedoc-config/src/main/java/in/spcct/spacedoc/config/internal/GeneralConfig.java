package in.spcct.spacedoc.config.internal;

import in.spcct.spacedoc.config.ConfigNamespace;
import in.spcct.spacedoc.config.ConfigProperty;
import in.spcct.spacedoc.config.loader.Config;
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
    @ConfigProperty(name = "temp-file-prefix", defaultValue = "spacedoc-temp")
    private String tempFilePrefix;

    /**
     * Temporary file location.
     * <p>
     * When null is provided, the Java default path for temp files is used;
     * see {@link java.io.File#createTempFile(String, String, java.io.File)} documentation.
     * <p>
     * This default behavior should probably change at some point.
     */
    @ConfigProperty(name = "temp-directory-path")
    private String tempDirectoryPath;
}

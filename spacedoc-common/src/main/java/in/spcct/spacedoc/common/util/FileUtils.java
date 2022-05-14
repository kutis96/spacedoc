package in.spcct.spacedoc.common.util;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.config.internal.GeneralConfig;

import java.io.File;
import java.io.IOException;

/**
 * A set of utilities for handling files.
 */
public class FileUtils {

    private static final GeneralConfig envConfig = Registry.lookup(GeneralConfig.class);

    /**
     * Creates a new temporary file.
     * <p>
     * The prefix is configured by {@link GeneralConfig#getTempFilePrefix},
     * the path by {@link GeneralConfig#getTempDirectoryPath}.
     * <p>
     * See {@link #getTempDirectory()} and {@link File#createTempFile(String, String, File)} for more.
     *
     * @param suffix suffix, should typically start with a dot. (Example: ".json")
     * @return new temporary file
     * @throws IOException when temporary file creation fails for some reason.
     */
    public static File createTempFile(String suffix) throws IOException {
        return File.createTempFile(envConfig.getTempFilePrefix(), suffix, getTempDirectory());
    }

    /**
     * Returns a path to be fed into {@link File#createTempFile(String, String, File)}.
     * <p>
     * If the configured value at {@link GeneralConfig#getTempDirectoryPath} is empty or null, a default path is used.
     *
     * @return null when the configured path is empty or null; a new {@link File} instance with the configured path otherwise.
     */
    private static File getTempDirectory() {
        String tempDirectoryPath = envConfig.getTempDirectoryPath();

        if (tempDirectoryPath == null || tempDirectoryPath.isBlank())
            return null;

        return new File(tempDirectoryPath);
    }

}

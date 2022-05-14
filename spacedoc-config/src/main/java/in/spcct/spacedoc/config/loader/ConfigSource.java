package in.spcct.spacedoc.config.loader;

import lombok.NonNull;

/**
 * Generic source of configuration objects.
 */
public interface ConfigSource {

    class NullSource implements ConfigSource {
        @Override
        public boolean containsItem(@NonNull String path) {
            return false;
        }

        @Override
        public Object getItem(@NonNull String path) {
            return null;
        }
    }

    /**
     * Determines whether this configuration source contains a value on the specified path.
     *
     * @param path path to the configuration value
     * @return true when this configuration source contains a value on this path.
     * This may also be true when the returned value on this pathSegments would be null.
     */
    boolean containsItem(@NonNull String path);

    /**
     * Fetches a configuration value on the specified path.
     *
     * @param path path to the configuration value
     * @return configuration value on this path, or null if no value was found.
     */
    Object getItem(@NonNull String path);

}

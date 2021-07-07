package in.spcct.spacedoc.configloader;

import lombok.NonNull;

/**
 * Generic source of configuration objects.
 *
 * @param <V> Type of values provided by this configuration source.
 */
public interface ConfigSource<V> {

    /**
     * Determines whether this configuration source contains a value on the specified path.
     *
     * @param pathSegments path segments to the configuration value
     * @return true when this configuration source contains a value on this path.
     * This may also be true when the returned value on this pathSegments would be null.
     */
    boolean containsItem(@NonNull String... pathSegments);

    /**
     * Fetches a configuration value on the specified path.
     *
     * @param pathSegments path to the configuration value
     * @return configuration value on this path, or null if no value was found.
     */
    V getItem(@NonNull String... pathSegments);

}

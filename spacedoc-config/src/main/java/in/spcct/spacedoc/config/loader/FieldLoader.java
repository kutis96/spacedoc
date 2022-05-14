package in.spcct.spacedoc.config.loader;

import in.spcct.spacedoc.config.ConfigProperty;
import in.spcct.spacedoc.config.ConfigSourceWithOverrides;

import java.util.Map;

/**
 * Field loader loads the fields of some target object based on properties from a specified config source.
 */
public interface FieldLoader {

    /**
     * Loads fields of the target object with values from the config source, offset by the specified base path.
     *
     * @param configSource contains values to be loaded into the target object
     * @param targetObject instance of the target object to load values into
     * @param basePath     path offset. The paths specified in {@link ConfigProperty#value()} are prepended with this offset for the purposes of loading.
     * @param overrides    Prefer these values to those from a config source. Use sparingly.
     */
    default void loadFields(ConfigSource configSource, Object targetObject, String basePath, Map<String, Object> overrides) {
        loadFields(
                new ConfigSourceWithOverrides(configSource, overrides),
                targetObject,
                basePath
        );
    }

    /**
     * Loads fields of the target object with values from the config source, offset by the specified base path.
     *
     * @param configSource contains values to be loaded into the target object
     * @param targetObject instance of the target object to load values into
     * @param basePath     path offset. The paths specified in {@link ConfigProperty#value()} are prepended with this offset for the purposes of loading.
     */
    void loadFields(ConfigSource configSource, Object targetObject, String basePath);

}

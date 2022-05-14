package in.spcct.spacedoc.config.loader;

import in.spcct.spacedoc.config.ConfigProperty;

/**
 * Field loader loads the fields of some target object based on properties from a specified config source.
 *
 * @param <CS> config source type to be used with this field loader
 */
public interface FieldLoader<CS extends ConfigSource<?>> {

    /**
     * Loads fields of the target object with values from the config source, offset by the specified base path.
     *
     * @param configSource     contains values to be loaded into the target object
     * @param targetObject     instance of the target object to load values into
     * @param basePathSegments path offset. The paths specified in {@link ConfigProperty#value()} are prepended with this offset for the purposes of loading.
     */
    void loadFields(CS configSource, Object targetObject, String... basePathSegments);

}

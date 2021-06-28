package in.spcct.spacedoc.configloader;

public interface FieldLoader<CS extends ConfigSource<?>> {

    void loadFields(CS configSource, String basePath, Object targetObject);

}

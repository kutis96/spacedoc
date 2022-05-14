package in.spcct.spacedoc.config.loader.newconf;

import in.spcct.spacedoc.config.ConfigProperty;
import in.spcct.spacedoc.config.loader.fieldmap.FieldMapping;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class ConfigValueImpl implements ConfigValue {

    private final FieldMapping fieldMapping;
    private final Config configObject;

    public ConfigValueImpl(FieldMapping fieldMapping, Config configObject) {
        this.fieldMapping = fieldMapping;
        this.configObject = configObject;
    }

    @Override
    public String getName() {
        return fieldMapping.getPath();
    }

    @Override
    public String getDefaultValue() {
        Field field = fieldMapping.getField();

        ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);

        return (configProperty.defaultValue().equals(""))
                ? ""
                : configProperty.defaultValue();
    }

    @SneakyThrows
    @Override
    public String getValue() {
        return String.valueOf(
                fieldMapping.getField().get(configObject)
        );
    }

    @Override
    public void setValue(String value) {
        //TODO: Standardized conversion

        //fieldMapping.getField().set(configObject, value);
    }
}

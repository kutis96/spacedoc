package in.spcct.spacedoc.propconfig;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public abstract class AbstractPropertyConfig {

    private final PropertyFile propertyFileConfig;

    public AbstractPropertyConfig() {
        this.propertyFileConfig = this.getClass().getAnnotation(PropertyFile.class);
        loadFields();
    }

    private void loadFields() {
        if (propertyFileConfig == null)
            throw new UnsupportedOperationException("No @PropertyFile annotation has been specified on this class.");

        loadFields(
                propertyFileConfig.value(),
                propertyFileConfig.prefix()
        );
    }

    @SneakyThrows
    private void loadFields(String path, String prefix) {
        Properties properties = new Properties();

        try (InputStream inputStream = this.getClass().getResourceAsStream(path)) {
            if (inputStream == null)
                throw new FileNotFoundException("Property file '" + path + "' has not been found.");

            properties.load(inputStream);
        }

        loadFields(properties, prefix);
    }

    private void loadFields(Properties properties, String originalPrefix) {
        final String actualPrefix = getPrefix(originalPrefix);

        List<FieldMapping> mappings =
                FieldUtils.getAllFieldsList(this.getClass())
                .stream()
                .filter(f -> f.getAnnotationsByType(Property.class).length > 0)
                .flatMap(f -> mapField(actualPrefix, f).stream())
                .collect(Collectors.toList());

        mapAll(mappings, properties);
    }

    private void mapAll(List<FieldMapping> mappings, Properties properties) {
        mappings.forEach(mapping -> {
            Field field = mapping.getField();
            String propertyName = mapping.getPropertyName();

            String value = properties.getProperty(propertyName);

            if(value == null) {
                if (mapping.isRequired()) {
                    throw new IllegalStateException("Failed to map property: Field '" + propertyName + "' was required and not found.");
                } else {
                    if (properties.containsKey(propertyName)) {
                        //Property value must be set to null, continue as intended.
                    } else {
                        //No property has been found in config, don't modify whatever default values there may be.
                        return; //this iteration, not this method
                    }
                }
            }

            try {
                Class<?> fieldType = field.getType();
                Object convertedValue;

                if (value == null) {
                    convertedValue = null;
                } else if (fieldType == String.class) {
                    convertedValue = value;
                } else if (fieldType == Integer.class) {
                    convertedValue = Integer.parseInt(value);
                } else if (fieldType == Boolean.class) {
                    convertedValue = Boolean.parseBoolean(value);
                } else {
                    throw new IllegalStateException("Failed to map property: Field '" + propertyName + "' is of an unsupported type " + fieldType.getCanonicalName());
                }

                BeanUtils.setProperty(this, field.getName(), convertedValue);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Failed to map property: Field access exception", e);
            }
        });
    }

    private List<FieldMapping> mapField(String prefix, Field field) {
        Property[] properties = field.getAnnotationsByType(Property.class);
        List<FieldMapping> fieldMappings = new ArrayList<>();

        for(Property p : properties) {
            String name = prefix
                    + ((("".equals(p.value())))
                    ? deriveConfigNameFromFieldName(field.getName())
                    : p.value());
            fieldMappings.add(
                    new FieldMapping(field, name, p.required())
            );
        }

        return fieldMappings;
    }

    /**
     * Converts javaCase field names to kebab-case.
     *
     * @param fieldName original field name
     * @return kebab-case field names
     */
    private String deriveConfigNameFromFieldName(String fieldName) {
        return fieldName
                .replaceAll("(\\p{javaUpperCase}+)", "-$1") //prefix groups of uppercase letters with a dash
                .replaceAll("^-", "") //remove leading dash
                .toLowerCase(); //convert all to lowercase
    }

    private String getPrefix(String prefix) {
        return ("".equals(prefix))
                ? ""
                : prefix + '.';
    }

    @Data
    private static final class FieldMapping {
        private final Field field;
        private final String propertyName;
        private final boolean required;

        public FieldMapping(Field field, String propertyName, boolean required) {
            this.field = field;
            this.propertyName = propertyName;
            this.required = required;
        }
    }
}

package in.spcct.spacedoc.configloader.fieldmap;

import in.spcct.spacedoc.configloader.ConfigSource;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @param <V> base type for value conversions
 */
public abstract class FieldMapper<V> {

    public void mapAll(List<FieldMapping> mappings, ConfigSource<V> configSource, Object targetObject) {
        mappings.forEach(mapping -> {
            Field field = mapping.getField();
            String propertyName = mapping.getPath();

            V value = configSource.getItem(propertyName);

            if (value == null) {
                if (mapping.isRequired()) {
                    throw new IllegalStateException("Failed to map property: Field '" + propertyName + "' was required and not found.");
                } else {
                    if (configSource.containsItem(propertyName)) {
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

                convertedValue = convertItem(propertyName, value, fieldType);

                BeanUtils.setProperty(targetObject, field.getName(), convertedValue);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Failed to map property: Field access exception", e);
            }
        });
    }

    /**
     * Converts the supplied value into an object of the specified fieldType.
     *
     * @param propertyName only used for printing more helpful exceptions
     * @param value        value to be converted into the specified type
     * @param fieldType    type to convert the value to
     * @return object of the specified fieldType or null
     */
    protected abstract Object convertItem(String propertyName, V value, Class<?> fieldType);

}

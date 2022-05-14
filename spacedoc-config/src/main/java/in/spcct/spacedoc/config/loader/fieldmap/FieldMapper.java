package in.spcct.spacedoc.config.loader.fieldmap;

import in.spcct.spacedoc.config.Converter;
import in.spcct.spacedoc.config.loader.ConfigSource;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
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

                Converter converter = field.getAnnotation(Converter.class);
                if (converter != null) {
                    FieldConverter fieldConverter = converter.value()
                            .getDeclaredConstructor().newInstance();

                    //hacky hack hack
                    convertedValue = fieldConverter.convert(String.valueOf(value), mapping);

                } else {
                    convertedValue = convertItem(propertyName, value, fieldType);
                }

                BeanUtils.setProperty(targetObject, field.getName(), convertedValue);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Failed to map property: Field access exception for field " + field.getName() + " of " + field.getDeclaringClass().getCanonicalName(), e);
            } catch (NoSuchMethodException | InstantiationException e) {
                throw new RuntimeException("Failed to instantiate converter for field " + field.getName() + " of " + field.getDeclaringClass().getCanonicalName(), e);
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

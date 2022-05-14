package in.spcct.spacedoc.config.loader.objectmapconfig;

import in.spcct.spacedoc.config.loader.fieldmap.FieldMapper;

public class ObjectFieldMapper extends FieldMapper<Object> {

    /**
     * Attempts to map Objects to the correct field type.
     * <p>
     * Currently only supports String, Integer, and Boolean values.
     *
     * @param propertyName only used for printing more helpful exceptions
     * @param value        value to be converted into the specified type
     * @param fieldType    type to convert the value to
     * @return converted object.
     */
    @Override
    protected Object convertItem(String propertyName, Object value, Class<?> fieldType) {

        if (value == null) {
            return null;
        } else if (fieldType == String.class) {
            return String.valueOf(value);
        } else if (fieldType == Integer.class || fieldType == int.class) {
            if (value instanceof Integer) {
                return value;
            } else {
                return Integer.parseInt(String.valueOf(value));
            }
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            if (value instanceof Boolean) {
                return value;
            } else {
                return Boolean.valueOf(String.valueOf(value));
            }
        } else {
            throw new IllegalStateException("Failed to map property: Field '" + propertyName + "' is of an unsupported type " + fieldType.getCanonicalName());
        }

    }
}

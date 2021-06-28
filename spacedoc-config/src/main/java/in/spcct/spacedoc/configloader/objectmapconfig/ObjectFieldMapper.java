package in.spcct.spacedoc.configloader.objectmapconfig;

import in.spcct.spacedoc.configloader.fieldmap.FieldMapper;

public class ObjectFieldMapper extends FieldMapper<Object> {
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

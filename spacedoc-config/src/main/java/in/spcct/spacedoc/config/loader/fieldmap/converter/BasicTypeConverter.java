package in.spcct.spacedoc.config.loader.fieldmap.converter;

import in.spcct.spacedoc.config.loader.fieldmap.FieldConverter;
import in.spcct.spacedoc.config.loader.fieldmap.FieldMapping;

public class BasicTypeConverter implements FieldConverter {

    @Override
    public Object convert(String source, FieldMapping fieldMapping) {

        //TODO: Un-fail.

        assert source != null;
        assert !source.isBlank();
        assert source.trim().equals(source);

        Class<?> fieldType = fieldMapping.getField().getType();

        if (fieldType == String.class) {
            return source; //too easy
        }
        if (fieldType == boolean.class || fieldType == Boolean.class) {
            if (source.equalsIgnoreCase("true")) {
                return true;
            } else if (source.equalsIgnoreCase("false")) {
                return false;
            } else {
                throw new IllegalArgumentException("Unsupported value " + source);
            }
        }
        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(source);
        }
        if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(source);
        }
        if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(source);
        }
        if (fieldType == float.class || fieldType == Float.class) {
            return Float.parseFloat(source);
        }
        if (fieldType == char.class || fieldType == Character.class) {
            return source.charAt(0);
        }

        throw new UnsupportedOperationException("Cannot convert fields of type " + fieldType.getCanonicalName() + " using this converter.");

    }
}

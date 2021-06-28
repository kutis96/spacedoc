package in.spcct.spacedoc.configloader.fieldmap;

public class StringFieldMapper extends FieldMapper<String> {
    @Override
    protected Object convertItem(String propertyName, String value, Class<?> fieldType) {
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

        return convertedValue;
    }
}

package in.spcct.spacedoc.config.loader.fieldmap;

public class StringFieldMapper extends FieldMapper {
    /**
     * Attempts to map Strings to the correct field type.
     * <p>
     * Currently only supports String, Integer, and Boolean values.
     *
     * @param propertyName only used for printing more helpful exceptions
     * @param value        value to be converted into the specified type
     * @param fieldType    type to convert the value to
     * @return converted object.
     */
    @Override
    public Object convertItem(String propertyName, Object value, Class<?> fieldType) {
        String stringValue = (String) value;

        Object convertedValue;

        if (value == null) {
            convertedValue = null;
        } else if (fieldType == String.class) {
            convertedValue = value;
        } else if (fieldType == Integer.class || fieldType == int.class) {
            convertedValue = Integer.parseInt(stringValue);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            convertedValue = Boolean.parseBoolean(stringValue);
        } else {
            throw new IllegalStateException("Failed to map property: Field '" + propertyName + "' is of an unsupported type " + fieldType.getCanonicalName());
        }

        return convertedValue;
    }
}

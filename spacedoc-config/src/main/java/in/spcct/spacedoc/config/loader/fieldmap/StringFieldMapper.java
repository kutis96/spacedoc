package in.spcct.spacedoc.config.loader.fieldmap;

public class StringFieldMapper extends FieldMapper<String> {
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

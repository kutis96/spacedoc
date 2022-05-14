package in.spcct.spacedoc.config.loader.fieldmap;

public interface FieldConverter {

    /**
     * @param source       Non-empty string with no leading or trailing whitespace
     * @param fieldMapping
     * @return converted value. Type defined by the fieldMapping's type.
     */
    Object convert(String source, FieldMapping fieldMapping) throws ConversionException;

}

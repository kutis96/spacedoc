package in.spcct.spacedoc.configloader.fieldmap.converter;

import in.spcct.spacedoc.configloader.fieldmap.FieldConverter;
import in.spcct.spacedoc.configloader.fieldmap.FieldMapping;

import java.util.StringJoiner;

public class EnumConverter implements FieldConverter {

    @Override
    public Object convert(String source, FieldMapping fieldMapping) {

        Class<Enum<?>> clazz = (Class<Enum<?>>) fieldMapping.getField().getType();

        if (!clazz.isEnum())
            throw new UnsupportedOperationException(clazz.getCanonicalName() + " is not an Enum");

        Enum<?>[] enumConstants = clazz.getEnumConstants();

        for (Enum<?> value : enumConstants) {
            if (source.equalsIgnoreCase(value.name()))
                return value;
        }

        throw new IllegalArgumentException("Cannot map '" + source + "' to an Enum. Valid values are: " + validValues(enumConstants));
    }

    private String validValues(Enum<?>[] enumConstants) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (Enum<?> value : enumConstants) {
            stringJoiner.add(value.name());
        }
        return stringJoiner.toString();
    }
}

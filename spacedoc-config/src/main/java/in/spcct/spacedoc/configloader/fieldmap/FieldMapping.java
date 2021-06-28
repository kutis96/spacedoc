package in.spcct.spacedoc.configloader.fieldmap;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class FieldMapping {
    private final Field field;
    private final String path;
    private final boolean required;

    public FieldMapping(Field field, String propertyName, boolean required) {
        this.field = field;
        this.path = propertyName;
        this.required = required;
    }
}
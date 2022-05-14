package in.spcct.spacedoc.config.loader.fieldmap;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * Maps a field of a target object to the path its value might be found on.
 */
@Data
public class FieldMapping {
    private final Field field;
    private final String path;
    private final boolean required;

    public FieldMapping(Field field, String propertyPath, boolean required) {
        this.field = field;
        this.path = propertyPath;
        this.required = required;
    }
}
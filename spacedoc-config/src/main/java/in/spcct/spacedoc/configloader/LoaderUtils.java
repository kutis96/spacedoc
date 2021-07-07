package in.spcct.spacedoc.configloader;

import in.spcct.spacedoc.configloader.fieldmap.FieldMapping;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO: Clean up
public class LoaderUtils {

    public static List<FieldMapping> deriveFieldMappings(String basePrefix, Class<?> clazz, String pathSeparator) {
        final String actualPrefix = actualPrefix(basePrefix, pathSeparator);

        return FieldUtils.getAllFieldsList(clazz)
                .stream()
                .filter(f -> f.getAnnotationsByType(Property.class).length > 0)
                .flatMap(f -> mapField(actualPrefix, f).stream())
                .collect(Collectors.toList());
    }

    /**
     * Converts prefix property to the actual prefix.
     * <p>
     * That is, "" when no prefix is set, or prefix + "." when a prefix is set.
     *
     * @param prefix prefix property
     * @return prefix to be used with further processing
     */
    public static String actualPrefix(String prefix, String pathSeparator) {
        return (prefix == null || "".equals(prefix))
                ? ""
                : prefix + pathSeparator;
    }


    public static List<FieldMapping> mapField(String prefix, Field field) {
        Property[] properties = field.getAnnotationsByType(Property.class);
        List<FieldMapping> fieldMappings = new ArrayList<>();

        for (Property p : properties) {
            String propertyPath = prefix
                    + ((("".equals(p.value())))
                    ? deriveConfigNameFromFieldName(field.getName())
                    : p.value());
            fieldMappings.add(
                    new FieldMapping(field, propertyPath, p.required())
            );
        }

        return fieldMappings;
    }

    /**
     * Converts javaCase field names to kebab-case.
     *
     * @param fieldName original field name
     * @return kebab-case field names
     */
    public static String deriveConfigNameFromFieldName(String fieldName) {
        return fieldName
                .replaceAll("(\\p{javaUpperCase}+)", "-$1") //prefix groups of uppercase letters with a dash
                .replaceAll("^-", "") //remove leading dash
                .toLowerCase(); //convert all to lowercase
    }

}

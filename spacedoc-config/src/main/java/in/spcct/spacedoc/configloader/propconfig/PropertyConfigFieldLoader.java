package in.spcct.spacedoc.configloader.propconfig;

import in.spcct.spacedoc.configloader.FieldLoader;
import in.spcct.spacedoc.configloader.Property;
import in.spcct.spacedoc.configloader.fieldmap.FieldMapper;
import in.spcct.spacedoc.configloader.fieldmap.FieldMapping;
import in.spcct.spacedoc.configloader.fieldmap.StringFieldMapper;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertyConfigFieldLoader implements FieldLoader<PropertyConfigSource> {

    private final FieldMapper<String> fieldMapper = new StringFieldMapper();

    public void loadFields(Properties properties, String basePrefix, Object o) {
        loadFields(new PropertyConfigSource(properties), basePrefix, o);
    }

    @Override
    public void loadFields(PropertyConfigSource configSource, String basePrefix, Object targetObject) {
        List<FieldMapping> mappings = deriveFieldMappings(basePrefix, targetObject.getClass());
        fieldMapper.mapAll(mappings, configSource, targetObject);
    }

    private List<FieldMapping> deriveFieldMappings(String basePrefix, Class<?> clazz) {
        final String actualPrefix = actualPrefix(basePrefix);

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
    private String actualPrefix(String prefix) {
        return (prefix == null || "".equals(prefix))
                ? ""
                : prefix + '.';
    }

    private List<FieldMapping> mapField(String prefix, Field field) {
        Property[] properties = field.getAnnotationsByType(Property.class);
        List<FieldMapping> fieldMappings = new ArrayList<>();

        for (Property p : properties) {
            String name = prefix
                    + ((("".equals(p.value())))
                    ? deriveConfigNameFromFieldName(field.getName())
                    : p.value());
            fieldMappings.add(
                    new FieldMapping(field, name, p.required())
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
    private String deriveConfigNameFromFieldName(String fieldName) {
        return fieldName
                .replaceAll("(\\p{javaUpperCase}+)", "-$1") //prefix groups of uppercase letters with a dash
                .replaceAll("^-", "") //remove leading dash
                .toLowerCase(); //convert all to lowercase
    }

}

package in.spcct.spacedoc.configloader.propconfig;

import in.spcct.spacedoc.configloader.FieldLoader;
import in.spcct.spacedoc.configloader.LoaderUtils;
import in.spcct.spacedoc.configloader.fieldmap.FieldMapper;
import in.spcct.spacedoc.configloader.fieldmap.FieldMapping;
import in.spcct.spacedoc.configloader.fieldmap.StringFieldMapper;

import java.util.List;
import java.util.Properties;

public class PropertyConfigFieldLoader implements FieldLoader<PropertyConfigSource> {

    private final FieldMapper<String> fieldMapper = new StringFieldMapper();
    private final String pathSeparator;

    public PropertyConfigFieldLoader() {
        this(".");
    }

    public PropertyConfigFieldLoader(String pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    public void loadFields(Properties properties, String basePrefix, Object o) {
        loadFields(new PropertyConfigSource(properties), o, basePrefix);
    }

    @Override
    public void loadFields(PropertyConfigSource configSource, Object targetObject, String... basePathSegments) {

        String pathPrefix = (basePathSegments == null || basePathSegments.length == 0) ? null :
                String.join(pathSeparator, basePathSegments);

        List<FieldMapping> mappings = LoaderUtils.deriveFieldMappings(pathPrefix, targetObject.getClass(), pathSeparator);
        fieldMapper.mapAll(mappings, configSource, targetObject);
    }

}

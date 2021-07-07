package in.spcct.spacedoc.configloader.objectmapconfig;

import in.spcct.spacedoc.configloader.FieldLoader;
import in.spcct.spacedoc.configloader.LoaderUtils;
import in.spcct.spacedoc.configloader.fieldmap.FieldMapper;
import in.spcct.spacedoc.configloader.fieldmap.FieldMapping;

import java.util.List;

public class ObjectMapFieldLoader implements FieldLoader<ObjectMapConfigSource> {
    private final String pathSeparator;
    FieldMapper<Object> fieldMapper = new ObjectFieldMapper();

    public ObjectMapFieldLoader() {
        this(".");
    }

    public ObjectMapFieldLoader(String pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    @Override
    public void loadFields(ObjectMapConfigSource configSource, Object targetObject, String... basePathSegments) {
        String pathPrefix = (basePathSegments == null || basePathSegments.length == 0) ? null :
                String.join(pathSeparator, basePathSegments);

        List<FieldMapping> fieldMappings = LoaderUtils.deriveFieldMappings(pathPrefix, targetObject.getClass(), pathSeparator);
        fieldMapper.mapAll(fieldMappings, configSource, targetObject);
    }

}

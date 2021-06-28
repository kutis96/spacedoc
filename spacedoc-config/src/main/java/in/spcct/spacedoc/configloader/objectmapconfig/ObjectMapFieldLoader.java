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
    public void loadFields(ObjectMapConfigSource configSource, String basePath, Object targetObject) {
        List<FieldMapping> fieldMappings = LoaderUtils.deriveFieldMappings(basePath, targetObject.getClass(), pathSeparator);
        fieldMapper.mapAll(fieldMappings, configSource, targetObject);
    }

}

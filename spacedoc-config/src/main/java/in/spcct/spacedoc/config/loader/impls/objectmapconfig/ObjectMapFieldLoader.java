package in.spcct.spacedoc.config.loader.impls.objectmapconfig;

import in.spcct.spacedoc.config.loader.ConfigSource;
import in.spcct.spacedoc.config.loader.FieldLoader;
import in.spcct.spacedoc.config.loader.LoaderUtils;
import in.spcct.spacedoc.config.loader.fieldmap.FieldMapper;
import in.spcct.spacedoc.config.loader.fieldmap.FieldMapping;

import java.util.List;

public class ObjectMapFieldLoader implements FieldLoader {
    FieldMapper fieldMapper = new ObjectFieldMapper();

    public ObjectMapFieldLoader() {
    }

    @Override
    public void loadFields(ConfigSource configSource, Object targetObject, String basePath) {
        List<FieldMapping> fieldMappings = LoaderUtils.deriveFieldMappings(basePath, targetObject.getClass());
        fieldMapper.mapAll(fieldMappings, configSource, targetObject);
    }

}

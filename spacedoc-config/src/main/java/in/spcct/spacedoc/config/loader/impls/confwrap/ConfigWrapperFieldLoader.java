package in.spcct.spacedoc.config.loader.impls.confwrap;

import in.spcct.spacedoc.config.loader.ConfigSource;
import in.spcct.spacedoc.config.loader.FieldLoader;
import in.spcct.spacedoc.config.loader.LoaderUtils;
import in.spcct.spacedoc.config.loader.fieldmap.FieldMapper;
import in.spcct.spacedoc.config.loader.fieldmap.FieldMapping;
import in.spcct.spacedoc.config.loader.fieldmap.StringFieldMapper;

import java.util.List;

public class ConfigWrapperFieldLoader implements FieldLoader {

    private final FieldMapper fieldMapper = new StringFieldMapper();

    @Override
    public void loadFields(ConfigSource configSource, Object targetObject, String basePath) {
        List<FieldMapping> mappings = LoaderUtils.deriveFieldMappings(basePath, targetObject.getClass());
        fieldMapper.mapAll(mappings, configSource, targetObject);
    }

}

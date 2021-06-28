package in.spcct.spacedoc.configloader.propconfig;

import in.spcct.spacedoc.configloader.ConfigFile;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractPropertiesFileConfig {

    private final PropertyConfigFieldLoader fieldLoader = new PropertyConfigFieldLoader();

    private final ConfigFile propertyFileConfig;

    public AbstractPropertiesFileConfig() {
        this.propertyFileConfig = this.getClass().getAnnotation(ConfigFile.class);
        loadFields();
    }

    private void loadFields() {
        if (propertyFileConfig == null)
            throw new UnsupportedOperationException("No @PropertyFile annotation has been specified on this class.");

        loadFields(
                propertyFileConfig.value(),
                propertyFileConfig.prefix()
        );
    }

    @SneakyThrows
    private void loadFields(String path, String prefix) {
        Properties properties = new Properties();

        try (InputStream inputStream = this.getClass().getResourceAsStream(path)) {
            if (inputStream == null)
                throw new FileNotFoundException("Property file '" + path + "' has not been found.");

            properties.load(inputStream);
        }

        loadFields(properties, prefix);
    }

    private void loadFields(Properties properties, String originalPrefix) {
        fieldLoader.loadFields(
                properties,
                originalPrefix,
                this.getClass()
        );
    }

}

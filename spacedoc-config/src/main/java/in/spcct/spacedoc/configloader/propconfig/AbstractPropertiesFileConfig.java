package in.spcct.spacedoc.configloader.propconfig;

import in.spcct.spacedoc.configloader.ConfigFile;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Abstract class to be used by configuration classes, that are to be loaded from .properties files.
 * <p>
 * Every such class must also be annotated by {@link ConfigFile} annotation, specifying the config file location and other specifics.
 * <p>
 * Whenever an implementing class is instantiated, the properties file is loaded,
 * and the annotated fields of the class are populated from the loaded properties file.
 */
public abstract class AbstractPropertiesFileConfig {

    /**
     * Field loader supporting this config class.
     * <p>
     * This is responsible for actually handling String to the correct type translation, and actual field setting.
     */
    private final PropertyConfigFieldLoader fieldLoader = new PropertyConfigFieldLoader();

    /**
     * Configuring annotation
     */
    private final ConfigFile propertyFileConfig;

    /**
     * Loads the configuring {@link ConfigFile} annotation annotating the implementing class,
     * and initiates field loading according to the configuration.
     */
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

    /**
     * Attempts to load fields based on the specified propertiesFilePath to the .properties file, and the assumed prefix for the configuring values.
     *
     * @param propertiesFilePath path to the .properties file to be loaded.
     * @param prefix             prefix of all values to be loaded. Null when no prefix is to be prepended.
     * @throws FileNotFoundException sneakily throws this exception whenever the property file specified cannot be found.
     */
    @SneakyThrows
    private void loadFields(String propertiesFilePath, String prefix) {
        Properties properties = new Properties();

        try (InputStream inputStream = this.getClass().getResourceAsStream(propertiesFilePath)) {
            if (inputStream == null)
                throw new FileNotFoundException("Property file '" + propertiesFilePath + "' has not been found.");

            properties.load(inputStream);
        }

        loadFields(properties, prefix);
    }

    /**
     * Attempts to load fields based on the specified .properties object, and the assumed prefix for the configuring values.
     *
     * @param properties Properties file of to load the values from
     * @param prefix     prefix of all values to be loaded. Null when no prefix is to be prepended.
     */
    private void loadFields(Properties properties, String prefix) {
        fieldLoader.loadFields(
                properties,
                prefix,
                this
        );
    }

}

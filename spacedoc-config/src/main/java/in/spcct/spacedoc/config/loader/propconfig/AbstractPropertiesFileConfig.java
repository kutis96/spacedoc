package in.spcct.spacedoc.config.loader.propconfig;

import in.spcct.spacedoc.config.ConfigFile;
import in.spcct.spacedoc.config.ConfigNamespace;
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
 *
 * @deprecated for a new awesome method of loading all the things
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
    private final ConfigNamespace configNamespace;

    /**
     * Loads the configuring {@link ConfigFile} annotation annotating the implementing class.
     * <p>
     * Please do call {@link #loadConfig()} in the constructor of the implementing class to correctly initialize all values.
     */
    protected AbstractPropertiesFileConfig() {
        this.propertyFileConfig = this.getClass().getAnnotation(ConfigFile.class);
        this.configNamespace = this.getClass().getAnnotation(ConfigNamespace.class);
    }

    @SneakyThrows
    protected void loadConfig() {
        String fileName;
        String namespace;

        fileName = (propertyFileConfig != null)
                ? propertyFileConfig.fileName()
                : null; //TODO: Use some default file

        namespace = (configNamespace != null)
                ? configNamespace.prefix()
                : null;

        loadConfig(
                fileName,
                namespace
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
    private void loadConfig(String propertiesFilePath, String prefix) throws FileNotFoundException {
        Properties properties = new Properties();

        try (InputStream inputStream = this.getClass().getResourceAsStream(propertiesFilePath)) {
            if (inputStream == null)
                throw new FileNotFoundException("Property file '" + propertiesFilePath + "' has not been found.");

            properties.load(inputStream);
        }

        loadConfig(properties, prefix);
    }

    /**
     * Attempts to load fields based on the specified .properties object, and the assumed prefix for the configuring values.
     *
     * @param properties Properties file of to load the values from
     * @param prefix     prefix of all values to be loaded. Null when no prefix is to be prepended.
     */
    private void loadConfig(Properties properties, String prefix) {
        fieldLoader.loadFields(
                properties,
                prefix,
                this
        );
    }

}

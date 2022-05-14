package in.spcct.spacedoc.config;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.config.internal.FFCConfig;
import in.spcct.spacedoc.config.internal.GeneralConfig;
import in.spcct.spacedoc.config.internal.PolyglotConfig;
import in.spcct.spacedoc.config.loader.Config;
import in.spcct.spacedoc.config.loader.impls.confwrap.ConfigContextConfigSource;
import in.spcct.spacedoc.config.loader.impls.confwrap.ConfigWrapperFieldLoader;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

public class Setup {
    private static final Class<Config> configClass = Config.class;
    private static final ConfigContext configContext = ConfigContext.getInstance();
    public static final ConfigContextConfigSource configSource = new ConfigContextConfigSource(configContext);
    public static final ConfigWrapperFieldLoader fieldLoader = new ConfigWrapperFieldLoader();

    public static void registerAll() {

        loadConfigs();

        loadAndRegister(new FFCConfig());
        loadAndRegister(new GeneralConfig());
        loadAndRegister(new PolyglotConfig());
    }

    private static void loadAndRegister(Config config) {
        String basePath = null;

        ConfigNamespace namespace = config.getClass().getAnnotation(ConfigNamespace.class);
        if (namespace != null)
            basePath = namespace.prefix();

        fieldLoader.loadFields(
                configSource,
                config,
                basePath
        );

        Registry.registerSingleton((Class<Config>) config.getClass(), 0, () -> config);
    }

    @SneakyThrows
    public static void loadConfigs() {
        Properties properties = new Properties();
        properties.load(Setup.class.getResourceAsStream("/config/spacedoc.properties"));
        configContext.putAll(
                ConfigContext.EntrySource.CONFIG_FILE,
                properties.entrySet().stream().collect(
                        Collectors.toMap(
                                e -> String.valueOf(e.getKey()),
                                e -> String.valueOf(e.getValue()),
                                (prev, next) -> next, HashMap::new
                        ))
        );
    }

}

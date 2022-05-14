package in.spcct.spacedoc.config;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.config.internal.FFCConfig;
import in.spcct.spacedoc.config.internal.GeneralConfig;
import in.spcct.spacedoc.config.internal.PolyglotConfig;
import in.spcct.spacedoc.config.loader.newconf.Config;

public class Setup {

    public static void registerAll() {

        Class<Config> configClass = Config.class;
        Registry.registerSingleton(configClass, 0, FFCConfig::new);
        Registry.registerSingleton(configClass, 0, GeneralConfig::new);
        Registry.registerSingleton(configClass, 0, PolyglotConfig::new);

    }

}

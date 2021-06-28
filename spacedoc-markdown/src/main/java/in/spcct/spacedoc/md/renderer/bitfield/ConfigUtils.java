package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.configloader.propconfig.PropertyConfigFieldLoader;

import java.util.Map;
import java.util.Properties;

public class ConfigUtils {

    private static final PropertyConfigFieldLoader configFieldLoader = new PropertyConfigFieldLoader();

    public static BitFieldRenderer.Config createRendererConfig(Map<String, String> configEntries) {
        Properties properties = new Properties();
        properties.putAll(configEntries);

        BitFieldRenderer.Config config = new BitFieldRenderer.Config();

        configFieldLoader.loadFields(
                properties,
                null,
                config
        );

        return config;
    }

}

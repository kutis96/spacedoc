package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.configloader.propconfig.PropertyConfigFieldLoader;

import java.util.Map;
import java.util.Properties;

public class ConfigUtils {

    private static final PropertyConfigFieldLoader configFieldLoader = new PropertyConfigFieldLoader();

    /**
     * Creates a new {@link in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer.Config} from the provided Map of config entries.
     * <p>
     * The {@link in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer.Config} fields are annotated with {@link in.spcct.spacedoc.configloader.Property},
     * enabling the use of a a {@link PropertyConfigFieldLoader} for populating the class's values.
     *
     * @param configEntries map of config entries
     * @return new instance of the configuration file
     */
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

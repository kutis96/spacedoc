package in.spcct.spacedoc.md.renderer.bitfield;

import in.spcct.spacedoc.config.ConfigProperty;
import in.spcct.spacedoc.config.helper.YesIKnow;
import in.spcct.spacedoc.config.loader.ConfigSource;
import in.spcct.spacedoc.config.loader.FieldLoader;
import in.spcct.spacedoc.config.loader.impls.confwrap.ConfigWrapperFieldLoader;

import java.util.Map;

public class ConfigUtils {

    private static final FieldLoader fieldLoader = new ConfigWrapperFieldLoader();

    /**
     * Creates a new {@link in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer.Config} from the provided Map of config entries.
     * <p>
     * The {@link in.spcct.spacedoc.md.renderer.bitfield.BitFieldRenderer.Config} fields are annotated with {@link ConfigProperty},
     * enabling the use of a {@link FieldLoader} for populating the class's values.
     *
     * @param configEntries map of config entries
     * @return new instance of the configuration file
     */
    public static BitFieldRenderer.Config createRendererConfig(Map<String, String> configEntries) {

        BitFieldRenderer.Config config = new BitFieldRenderer.Config();

        fieldLoader.loadFields(
                new ConfigSource.NullSource(),
                config,
                null,
                YesIKnow.thatStringsAreActuallyObjects(configEntries)
        );

        return config;
    }

}

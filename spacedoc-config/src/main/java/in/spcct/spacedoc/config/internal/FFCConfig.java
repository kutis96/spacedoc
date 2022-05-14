package in.spcct.spacedoc.config.internal;


import in.spcct.spacedoc.config.ConfigNamespace;
import in.spcct.spacedoc.config.ConfigProperty;
import in.spcct.spacedoc.config.Converter;
import in.spcct.spacedoc.config.commons.EnableDisableAuto;
import in.spcct.spacedoc.config.loader.Config;
import in.spcct.spacedoc.config.loader.fieldmap.converter.EnumConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Configures "Foreign Function Call" properties, typically for calling external JavaScript libraries.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigNamespace(
        prefix = "ffc"
)
public class FFCConfig implements Config {

    /**
     * Path to Node.JS' "npx" executable
     * <p>
     * Typically useful for running code from external JavaScript libraries.
     */
    @ConfigProperty
    private String npxExecutable;

    /**
     *
     */
    @ConfigProperty(required = false)
    @Converter(EnumConverter.class)
    private EnableDisableAuto polyglotJs = EnableDisableAuto.AUTO;
}

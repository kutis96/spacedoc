package in.spcct.spacedoc.config.helper;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.config.commons.EnableDisableAuto;
import in.spcct.spacedoc.config.internal.FFCConfig;
import org.graalvm.home.Version;
public class VariantDecider {

    public boolean supportsPolyglotJS() {
        FFCConfig ffcConfig = Registry.lookup(FFCConfig.class);

        EnableDisableAuto polyglotJs = ffcConfig.getPolyglotJs();

        if (polyglotJs == EnableDisableAuto.ENABLED)
            return true;

        if (polyglotJs == EnableDisableAuto.DISABLED)
            return false;

        //GraalVM can totally run JS variants
        //Fixme: Detect GraalVM better, or indeed at all
        return Version.getCurrent().isRelease() || Version.getCurrent().isSnapshot();
    }



}

package in.spcct.spacedoc.config.helper;

import in.spcct.spacedoc.config.FFCConfig;
import in.spcct.spacedoc.config.commons.EnableDisableAuto;
import org.graalvm.home.Version;

public class VariantDecider {

    public boolean supportsPolyglotJS() {
        EnableDisableAuto polyglotJs = FFCConfig.getInstance().getPolyglotJs();

        if (polyglotJs == EnableDisableAuto.ENABLED)
            return true;

        if (polyglotJs == EnableDisableAuto.DISABLED)
            return false;

        //GraalVM can totally run JS variants
        //Fixme: Detect GraalVM better, or indeed at all
        return Version.getCurrent().isRelease() || Version.getCurrent().isSnapshot();
    }



}

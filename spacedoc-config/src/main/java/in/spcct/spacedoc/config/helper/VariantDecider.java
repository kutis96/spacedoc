package in.spcct.spacedoc.config.helper;

import in.spcct.spacedoc.config.FFCConfig;
import lombok.Singular;
import org.graalvm.home.Version;

public class VariantDecider {

    public boolean canRunJS() {
        if(FFCConfig.getInstance().getForceJS())
            return true;

        //GraalVM can totally run JS variants
        return Version.getCurrent().isRelease() || Version.getCurrent().isSnapshot();
    }



}

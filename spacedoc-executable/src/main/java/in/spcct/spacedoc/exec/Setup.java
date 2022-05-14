package in.spcct.spacedoc.exec;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.common.module.Module;
import in.spcct.spacedoc.exec.module.LanguageRendererModule;
import in.spcct.spacedoc.exec.module.MarkdownModule;

public class Setup {

    /**
     * Registers everything that needs to be registered pre-startup.
     */
    public static void registerAll() {
        Registry.registerSingleton(Module.class, 0, MarkdownModule::new);
        Registry.registerSingleton(Module.class, 0, LanguageRendererModule::new);

        in.spcct.spacedoc.config.Setup.registerAll();
        in.spcct.spacedoc.md.Setup.registerAll();
        in.spcct.spacedoc.ffc.Setup.registerAll();


    }
}

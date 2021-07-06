package in.spcct.spacedoc.exec;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.exec.module.LanguageRendererModule;
import in.spcct.spacedoc.exec.module.MarkdownModule;
import in.spcct.spacedoc.module.Module;

public class Setup {

    public static void registerAll() {
        SillyCDI.registerCaching(Module.class, 0, MarkdownModule::new);
        SillyCDI.registerCaching(Module.class, 0, LanguageRendererModule::new);

        in.spcct.spacedoc.md.Setup.registerAll();
        in.spcct.spacedoc.ffc.Setup.registerAll();
    }
}

package in.spcct.spacedoc.md;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.md.extension.externalformat.ExternalCodeRendererStore;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.InstructionSetRenderer;
import in.spcct.spacedoc.md.renderer.impl.GraphvizSvgRenderer;
import in.spcct.spacedoc.md.renderer.impl.WavedromSvgRenderer;

public class Setup {

    public static void registerAll() {

        SillyCDI.register(
                ExternalCodeRenderer.class, 1, WavedromSvgRenderer::new
        );
        SillyCDI.register(
                ExternalCodeRenderer.class, 1, GraphvizSvgRenderer::new
        );
        SillyCDI.register(
                ExternalCodeRenderer.class, 1, InstructionSetRenderer::new
        );


        //TODO: Make better after CDI improvement.
        // With nicer CDI, one could actually look stuff up nicer instead of using a special store for everything.
        ExternalCodeRendererStore store = ExternalCodeRendererStore.getInstance();

        for(ExternalCodeRenderer codeRenderer : SillyCDI.lookupAll(ExternalCodeRenderer.class, 0)) {
            store.register(codeRenderer.languageName(), codeRenderer);
        }
    }

}

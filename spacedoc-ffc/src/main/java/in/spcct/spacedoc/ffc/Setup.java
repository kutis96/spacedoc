package in.spcct.spacedoc.ffc;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.config.helper.VariantDecider;
import in.spcct.spacedoc.ffc.cli.GraphvizCLI;
import in.spcct.spacedoc.ffc.cli.WavedromCLI;
import in.spcct.spacedoc.ffc.js.VizJS;
import in.spcct.spacedoc.ffc.js.WavedromJS;

public class Setup {
    static final VariantDecider variantDecider = new VariantDecider();

    /**
     * Registers all known renderer implementations, along with their variant decision logic.
     */
    public static void registerAll() {
        SillyCDI.registerCaching(Wavedrom.class, 100, variantDecider::supportsPolyglotJS, WavedromJS::new);
        SillyCDI.registerCaching(Wavedrom.class, 0, WavedromCLI::new);

        SillyCDI.registerCaching(Graphviz.class, 100, variantDecider::supportsPolyglotJS, VizJS::new);
        SillyCDI.registerCaching(Graphviz.class, 0, GraphvizCLI::new);
    }
}

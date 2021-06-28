package in.spcct.spacedoc.ffc;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.config.helper.VariantDecider;
import in.spcct.spacedoc.ffc.cli.GraphvizCLI;
import in.spcct.spacedoc.ffc.cli.WavedromCLI;
import in.spcct.spacedoc.ffc.js.VizJS;
import in.spcct.spacedoc.ffc.js.WavedromJS;

public class Setup {
    static final VariantDecider variantDecider = new VariantDecider();

    public static void registerAll() {
        SillyCDI.register(Wavedrom.class, 100, variantDecider::canRunJS, WavedromJS::new);
        SillyCDI.register(Wavedrom.class, 0, WavedromCLI::new);

        SillyCDI.register(Graphviz.class, 100, variantDecider::canRunJS, VizJS::new);
        SillyCDI.register(Graphviz.class, 0, GraphvizCLI::new);
    }
}

package in.spcct.spacedoc.md.renderer.impl;

import in.spcct.spacedoc.cdi.SillyCDI;
import in.spcct.spacedoc.ffc.Graphviz;
import in.spcct.spacedoc.md.renderer.ExternalCodeRenderer;
import in.spcct.spacedoc.md.renderer.RenderingException;

import java.io.IOException;

public class GraphvizSvgRenderer implements ExternalCodeRenderer {

    @Override
    public String renderSvg(String source) throws RenderingException {
        try {
            return SillyCDI
                    .lookup(Graphviz.class)
                    .renderToSVG(source);

        } catch (IOException e) {
            throw new RenderingException("IO Exception during Graphviz render", e);
        } catch (InterruptedException e) {
            throw new RenderingException("Rendering interrupted, likely by timeout. Make sure the Graphviz tools are correctly installed.", e);
        }
    }

    @Override
    public String languageName() {
        return "graphviz";
    }

}
